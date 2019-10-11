# RxSocketIO
Simple RxJava2 socket.io library wrapper over [socket.io-client-java](https://github.com/socketio/socket.io-client-java) library.

## Install

### Gradle

```gradle
repositories {
    jcenter()
}

dependencies {
  implementation 'com.gorgexec.rxsocketio:rxsocketio:1.0.0'
}
```

## Usage

```java

 Collection<String> events = new ArraySet<>();
        events.add("login");
        events.add("new message");
        
RxSocketIo socket = RxSocketIo.create("http://localhost", events);

disposable.add(socket.observeState()
                .subscribeOn(Schedulers.computation())
                .subscribe(this::onState));
                
disposable.add(socket.observeMessages()
                .subscribeOn(Schedulers.computation())
                .subscribe(this::onIncomingMessage));
                
socket.connect();

```

### Example

TBD
