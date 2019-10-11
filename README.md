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
//collection of server events to handle
Collection<String> events = new ArraySet<>();
        events.add("login");
        events.add("new message");
        events.add("logout");

//socket initialization
RxSocketIo socket = RxSocketIo.create("http://localhost", events);

//subscribing socket state
disposable.add(socket.observeState()
                .subscribeOn(Schedulers.computation())
                .subscribe(this::onState));

//subscribing socket incoming messages from server               
disposable.add(socket.observeMessages()
                .subscribeOn(Schedulers.computation())
                .subscribe(this::onIncomingMessage));
                
socket.connect();

```
You can set a socket with additional options. It is the original `Socket.IO-client` liblary object:

```java

IO.Options options = new IO.Options();
options.timeout = 50000;

RxSocketIo socket = RxSocketIo.create("http://localhost", events, options);
```
Full options description is available [here](https://github.com/socketio/socket.io-client-java)


Sending message:

```java
socket.emit("new message", message);
```

Handling incoming messages:

```java
 
private void onIncomingMessage(SocketEvent socketEvent){
    String eventName = socketEvent.name();

    switch (eventName){

        case "new message":
            for(Object obj: socketEvent.data()) {
                //do something
            }
            break;
        
        case "logout":
            socket.disconnect();
            break;
    }
}
```

### Sample App

TBD
