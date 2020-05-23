package com.matteoveroni.simplerestapi.resources;

import com.matteoveroni.simplerestapi.router.Router;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.OpenApi;

public class ResourceRoot implements Handler {

    @Override
    @OpenApi(ignore = true)
    public void handle(Context ctx) throws Exception {
        ctx.redirect(Router.SWAGGER);
    }
}
