package com.matteoveroni.simplerestapi;

import io.javalin.Javalin;
import io.javalin.http.util.RedirectToLowercasePathPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private final Logger LOG = LoggerFactory.getLogger(App.class);

    public static final void main(String... args) {
        new App().startupServer();
    }

    public void startupServer() {
        LOG.debug("Server startup");
        Javalin
                .create(config -> {
                    config.registerPlugin(new RedirectToLowercasePathPlugin());
                })
                .get("/hello", ctx -> ctx.json("Hello world"))
                .start(8899);
    }
}
