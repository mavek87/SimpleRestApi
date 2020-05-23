package com.matteoveroni.simplerestapi.server;

import com.google.gson.Gson;
import com.matteoveroni.simplerestapi.events.EventStartServer;
import com.matteoveroni.simplerestapi.router.Router;
import io.javalin.Javalin;
import io.javalin.http.util.RedirectToLowercasePathPlugin;
import io.javalin.plugin.json.JavalinJson;
import io.javalin.plugin.openapi.OpenApiPlugin;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private static final int DEFAULT_SERVER_PORT = 8899;

    private final Javalin javalin;

    @Inject
    public Server(Router router, OpenApiPlugin openApiPlugin, Gson gson) {
        this.javalin = setupServer(router, openApiPlugin, gson);
    }

    public void onEvent(@Observes EventStartServer event) {
        LOG.debug("Server start event received");
        if (event.getServerPort().isPresent()) {
            start(event.getServerPort().get());
        } else {
            start();
        }
    }

    public void start() {
        start(DEFAULT_SERVER_PORT);
    }

    public void start(int port) {
        LOG.debug("Starting the server");
        javalin.start(port);
    }

    private Javalin setupServer(Router router, OpenApiPlugin openApiPlugin, Gson gson) {
        JavalinJson.setFromJsonMapper(gson::fromJson);
        JavalinJson.setToJsonMapper(gson::toJson);

        return Javalin
                .create(config -> {
                    config.registerPlugin(new RedirectToLowercasePathPlugin());
                    config.registerPlugin(openApiPlugin);
                    config.defaultContentType = "application/json";
                })
                .routes(router);
    }
}
