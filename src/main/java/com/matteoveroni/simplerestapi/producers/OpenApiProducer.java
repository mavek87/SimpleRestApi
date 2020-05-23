package com.matteoveroni.simplerestapi.producers;

import com.matteoveroni.simplerestapi.App;
import com.matteoveroni.simplerestapi.router.Router;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class OpenApiProducer {

    private final OpenApiPlugin openApiPlugin;

    public OpenApiProducer() {
        Info applicationInfo = new Info()
                .version("1.0")
                .title(App.APP_NAME)
                .description(App.APP_DESCRIPTION);

        OpenApiOptions options = new OpenApiOptions(applicationInfo)
                .path(Router.OPENAPI)
                .swagger(new SwaggerOptions(Router.SWAGGER).title("Docs SimpleRestApi"));
        openApiPlugin = new OpenApiPlugin(options);
    }

    @Produces
    public OpenApiPlugin producesOpenApi() {
        return this.openApiPlugin;
    }

}
