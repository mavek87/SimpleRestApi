package com.matteoveroni.simplerestapi.router;

import com.matteoveroni.simplerestapi.resources.ResourceHello;
import io.javalin.apibuilder.EndpointGroup;
import javax.enterprise.context.ApplicationScoped;
import static io.javalin.apibuilder.ApiBuilder.get;

@ApplicationScoped
public class Router implements EndpointGroup {

    public static final String ROOT = "/";
    public static final String API = ROOT + "api";

    public static final String RESOURCE_HELLO = API + "/hello";

    @Override
    public void addEndpoints() {
        get(RESOURCE_HELLO, new ResourceHello());
    }
}
