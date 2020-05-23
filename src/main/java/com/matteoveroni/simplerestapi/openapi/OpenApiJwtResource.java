package com.matteoveroni.simplerestapi.openapi;

import io.javalin.core.util.Header;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.json.ToJsonMapper;
import io.javalin.plugin.openapi.JavalinOpenApi;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenApiJwtResource implements Handler {

    private static final Logger LOG = LoggerFactory.getLogger(OpenApiJwtResource.class);

    private final OpenApiPlugin openApiPlugin;

    @Inject
    public OpenApiJwtResource(OpenApiPlugin openApiPlugin) {
        this.openApiPlugin = openApiPlugin;
    }

    @OpenApi(
            method = HttpMethod.GET,
            operationId = "getOpenApiJwt",
            summary = "OpenAPI con token JWT - Inserisci nel pannello Swagger in alto /openapijwt e premi il pulsante 'Explore' per usarle",
            description = "Chiamata che fornisce il json OpenAPI configurato con il token jwt. Inserisci nel pannello Swagger in alto /openapijwt e premi il pulsante 'Explore' per usarle",
            tags = {"openapi_jwt"},
            responses = {
                @OpenApiResponse(status = "200", description = "OK -  Ritorna il json OpenAPI configurato con il token jwt", content = @OpenApiContent(type = "application/json", from = String.class))
            }
    )
    @Override
    public void handle(Context ctx) {
        ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        ctx.header(Header.ACCESS_CONTROL_ALLOW_METHODS, "GET");

        ToJsonMapper toJsonMapper = openApiPlugin.getOpenApiHandler().getOptions().getToJsonMapper();
        OpenAPI openAPISchema = openApiPlugin.getOpenApiHandler().createOpenAPISchema();

        // Esempio security schema openapi per bearer JWT
        //  "security": [
        //      {
        //          "bearerAuth": []
        //      }
        //  ]
        List<SecurityRequirement> securityRequirements = new ArrayList<>();
        SecurityRequirement securityRequirement = new SecurityRequirement();
        securityRequirement.addList("bearerAuth");
        securityRequirements.add(securityRequirement);

        openAPISchema.setSecurity(securityRequirements);

        // Esempio struttura del json da aggiungere ai components openapi
        //        "components": {
        //            "securitySchemes": {
        //                "bearerAuth": {
        //                    "type": "http",
        //                            "scheme":"bearer",
        //                            "bearerFormat":"JWT"
        //                }
        //            }
        //        }
        Map<String, SecurityScheme> bearerAuth = new HashMap<>();
        SecurityScheme jwtSecurityScheme = new SecurityScheme();
        jwtSecurityScheme.setType(SecurityScheme.Type.HTTP);
        jwtSecurityScheme.setScheme("bearer");
        jwtSecurityScheme.setBearerFormat("JWT");
        bearerAuth.put("bearerAuth", jwtSecurityScheme);

        openAPISchema.getComponents().setSecuritySchemes(bearerAuth);

        LOG.debug("OPENAPIJSON modificata: " + toJsonMapper.map(openAPISchema));

        ctx.result(toJsonMapper.map(openAPISchema));
    }
}
