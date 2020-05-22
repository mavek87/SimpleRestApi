package com.matteoveroni.simplerestapi.resources;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ResourceHello implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.json("Hello world");
    }
}
