package com.gorgexec.rxsocketio;

import java.net.URISyntaxException;
import java.util.Collection;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.socket.client.IO;
import io.socket.client.Socket;

public class RxSocketIo {

    private final BehaviorSubject<SocketStateEvent> state = BehaviorSubject.create();

    private synchronized void onSocketState(SocketStateEvent state) {
        this.state.onNext(state);
    }

    public synchronized Flowable<SocketStateEvent> observeState() {
        return state.toFlowable(BackpressureStrategy.BUFFER);
    }

    private final PublishSubject<SocketEvent> messages = PublishSubject.create();

    private synchronized void onExternalEvent(SocketEvent event) {
        this.messages.onNext(event);
    }

    public synchronized Flowable<SocketEvent> observeMessages() {
        return messages.toFlowable(BackpressureStrategy.BUFFER);
    }

    private Socket socket;

    private RxSocketIo() {
    }

    public void connect() {
        socket.connect();
    }

    public void disconnect() {
        socket.disconnect();
    }

    public Single<Boolean> emit(String event, String message) {
        if (!socket.connected()) connect();
        return Single.just(socket.emit(event, message)).map(i -> true).subscribeOn(Schedulers.io());
    }

    public static RxSocketIo create(String serverAddress, Collection<String> externalEvents) {
        RxSocketIo res = new RxSocketIo();
        try {
            res.socket = IO.socket(serverAddress);

            //internal events
            for (SocketState state : SocketState.values()) {
                res.socket.on(state.label, objects -> res.onSocketState(SocketStateEvent.create(state, objects)));
            }

            //external events
            for (String e : externalEvents) {
                res.socket.on(e, objects -> res.onExternalEvent(SocketEvent.create(e, objects)));
            }

            res.onSocketState(SocketStateEvent.create(SocketState.READY, new Object[]{}));

        } catch (URISyntaxException e) {
            res.onSocketState(SocketStateEvent.create(SocketState.INIT_ERROR, new Object[]{e}));
        }

        return res;
    }
}
