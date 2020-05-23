package com.matteoveroni.simplerestapi;

import com.matteoveroni.simplerestapi.events.EventStartServer;
import javax.enterprise.event.Event;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    public static final String APP_NAME = "SimpleRestApi";
    public static final String APP_DESCRIPTION = "This is a simple REST API demo using Javalin";

    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static SeContainer CONTAINER;
    public static Event<Object> BUS;

    public static final void main(String... args) {
        new App().start();
    }

    public void start() {
        LOG.debug("Starting the app ...");

        LOG.debug("Creating the CDI Container ...");
        CONTAINER = SeContainerInitializer.newInstance()
                .disableDiscovery()
                .addPackages(true, getClass())
                .initialize();
        LOG.debug("CDI container created!");

        LOG.debug("Creating the EventBus ...");
        BUS = CONTAINER.getBeanManager().getEvent();
        LOG.debug("Eventbus created!");

        LOG.debug("Emitting start server event...");
        BUS.fire(new EventStartServer());
    }
}
