package com.matteoveroni.simplerestapi.events;

import java.util.Optional;

public class EventStartServer {

    private Integer serverPort = null;

    public EventStartServer() {
    }

    public EventStartServer(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public Optional<Integer> getServerPort() {
        return Optional.ofNullable(serverPort);
    }
}
