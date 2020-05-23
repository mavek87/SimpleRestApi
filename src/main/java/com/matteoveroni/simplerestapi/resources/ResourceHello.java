package com.matteoveroni.simplerestapi.resources;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;

public class ResourceHello implements Handler {

    @OpenApi(
            method = HttpMethod.GET,
            operationId = "hello",
            summary = "Hello Resource",
            description = "Simple test resource that returns Hello World",
            tags = {"helloworld"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "OK",
                            content = @OpenApiContent(type = "application/json", from = String.class)
                    )
            }
    )
    @Override
    public void handle(Context ctx) throws Exception {
        ctx.json("Hello world");
    }
}
