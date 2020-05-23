package com.matteoveroni.simplerestapi.producers;

import java.security.SecureRandom;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class RandomProducer {

    private final SecureRandom random = new SecureRandom();

    @Produces
    public SecureRandom produceRandom(){
        return random;
    }
}
