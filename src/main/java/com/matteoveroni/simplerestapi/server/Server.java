package com.matteoveroni.simplerestapi.server;

import com.google.gson.Gson;
import com.matteoveroni.simplerestapi.configurations.ConfigFile;
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

    private final Javalin javalin;
    private final ConfigFile configFile;

    @Inject
    public Server(Router router, OpenApiPlugin openApiPlugin, Gson gson, ConfigFile configFile) {
        this.configFile = configFile;

        JavalinJson.setFromJsonMapper(gson::fromJson);
        JavalinJson.setToJsonMapper(gson::toJson);

        this.javalin = Javalin
                .create(config -> {
                    config.registerPlugin(new RedirectToLowercasePathPlugin());
                    config.registerPlugin(openApiPlugin);
                    config.defaultContentType = "application/json";
                })
                .routes(router);
    }

    public void onEvent(@Observes EventStartServer event) {
        LOG.debug("Event start server received");
        startServer(configFile.getServerPort());
    }

    public void startServer(int port) {
        LOG.debug("Starting the server on port " + port);
        javalin.start(port);
    }
}
