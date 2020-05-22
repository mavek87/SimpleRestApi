package com.matteoveroni.simplerestapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private final Logger LOG = LoggerFactory.getLogger(App.class);

    public static final void main(String... args) {
        new App().start();
    }

    public void start() {
        LOG.debug("Hello World");
    }
}
