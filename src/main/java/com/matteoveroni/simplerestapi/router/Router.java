package com.matteoveroni.simplerestapi.router;

import com.matteoveroni.simplerestapi.rest.resources.HelloResource;
import com.matteoveroni.simplerestapi.rest.resources.RootResource;
import com.matteoveroni.simplerestapi.rest.resources.UsersResource;
import com.matteoveroni.simplerestapi.rest.resources.openapi.OpenApiJwtResource;
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
    public static final String API_RESOURCE_USERS = API + "/users";

    private final OpenApiJwtResource openApiJwtResource;
    private final RootResource rootResource;
    private final HelloResource helloResource;
    private final UsersResource userResource;

    @Inject
    public Router(OpenApiJwtResource openApiJwtResource, RootResource rootResource, HelloResource helloResource, UsersResource userResource) {
        this.openApiJwtResource = openApiJwtResource;
        this.rootResource = rootResource;
        this.helloResource = helloResource;
        this.userResource = userResource;
    }

    @Override
    public void addEndpoints() {
        after(OPENAPI, openApiJwtResource);

        get(ROOT, rootResource);

        get(API_RESOURCE_HELLO, helloResource);

        crud(API_RESOURCE_USERS + "/:user-id", userResource);
    }
}
