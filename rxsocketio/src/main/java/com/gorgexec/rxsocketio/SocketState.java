package com.gorgexec.rxsocketio;

import io.socket.client.Socket;

public enum SocketState {

    READY("ready"),
    INIT_ERROR("initError"),
    CONNECTED(Socket.EVENT_CONNECT),
    CONNECTING(Socket.EVENT_CONNECTING),
    DISCONNECTED(Socket.EVENT_DISCONNECT),
    ERROR(Socket.EVENT_ERROR),
    CONNECT_ERROR(Socket.EVENT_CONNECT_ERROR),
    CONNECT_TIMEOUT(Socket.EVENT_CONNECT_TIMEOUT),
    RECONNECTED(Socket.EVENT_RECONNECT),
    RECONNECT_ERROR(Socket.EVENT_RECONNECT_ERROR),
    RECONNECT_FAILED(Socket.EVENT_RECONNECT_FAILED),
    RECONNECT_ATTEMPT(Socket.EVENT_RECONNECT_ATTEMPT),
    RECONNECTING(Socket.EVENT_RECONNECTING);

    public final String label;

    SocketState(String label) {
        this.label = label;
    }
}



