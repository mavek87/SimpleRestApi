package com.matteoveroni.simplerestapi.producers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class GsonProducer {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Produces
    public Gson createGson() {
        return gson;
    }
}
