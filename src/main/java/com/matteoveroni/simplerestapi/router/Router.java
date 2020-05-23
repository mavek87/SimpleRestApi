package com.matteoveroni.simplerestapi.router;

import com.matteoveroni.simplerestapi.openapi.OpenApiJwtResource;
import com.matteoveroni.simplerestapi.resources.RootResource;
import com.matteoveroni.simplerestapi.resources.HelloResource;
import com.matteoveroni.simplerestapi.resources.UserResource;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.plugin.openapi.OpenApiPlugin;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import static io.javalin.apibuilder.ApiBuilder.*;

@ApplicationScoped
public class Router implements EndpointGroup {

    public static final String ROOT = "/";

    public static final String OPENAPI = ROOT + "openapi";
    public static final String SWAGGER = ROOT + "docs";
    public static final String API = ROOT + "api";

    public static final String API_RESOURCE_HELLO = API + "/hello";
    public static final String API_RESOURCE_USER = API + "/user";

    private final OpenApiPlugin openApiPlugin;

    private final HelloResource helloHandler;
    private final UserResource userHandler;

    @Inject
    public Router(OpenApiPlugin openApiPlugin, HelloResource helloHandler, UserResource userHandler) {
        this.openApiPlugin = openApiPlugin;
        this.helloHandler = helloHandler;
        this.userHandler = userHandler;
    }

    @Override
    public void addEndpoints() {
        after(OPENAPI, new OpenApiJwtResource(openApiPlugin));

        get(ROOT, new RootResource());

        get(API_RESOURCE_HELLO, helloHandler);

        crud(API_RESOURCE_USER + "/:user-id", userHandler);
    }
}
