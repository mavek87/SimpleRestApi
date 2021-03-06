package com.matteoveroni.simplerestapi.rest.resources;

import com.matteoveroni.simplerestapi.router.Router;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.plugin.openapi.annotations.OpenApi;

public class RootResource implements Handler {

    @Override
    @OpenApi(ignore = true)
    public void handle(Context ctx) throws Exception {
        ctx.redirect(Router.SWAGGER);
    }
}
