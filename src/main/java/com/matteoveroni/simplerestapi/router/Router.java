package com.matteoveroni.simplerestapi.router;

import com.matteoveroni.simplerestapi.openapi.OpenApiJwtResource;
import com.matteoveroni.simplerestapi.resources.ResourceRoot;
import com.matteoveroni.simplerestapi.resources.ResourceHello;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.jackson.JacksonModelConverterFactory;
import io.javalin.plugin.openapi.jackson.JacksonToJsonMapper;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import static io.javalin.apibuilder.ApiBuilder.after;
import static io.javalin.apibuilder.ApiBuilder.get;

@ApplicationScoped
public class Router implements EndpointGroup {

    public static final String ROOT = "/";

    public static final String OPENAPI = ROOT + "openapi";
    public static final String SWAGGER = ROOT + "docs";
    public static final String API = ROOT + "api";

    public static final String API_RESOURCE_HELLO = API + "/hello";

    private final OpenApiPlugin openApiPlugin;

    @Inject
    public Router(OpenApiPlugin openApiPlugin) {
        this.openApiPlugin = openApiPlugin;
    }

    @Override
    public void addEndpoints() {
        after(OPENAPI, new OpenApiJwtResource(openApiPlugin));

        get(ROOT, new ResourceRoot());

        get(API_RESOURCE_HELLO, new ResourceHello());
    }
}
