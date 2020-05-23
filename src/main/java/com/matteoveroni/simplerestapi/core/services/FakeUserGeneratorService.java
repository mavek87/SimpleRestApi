package com.matteoveroni.simplerestapi.core.services;

import com.matteoveroni.simplerestapi.core.dto.User;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FakeUserGeneratorService {

    private final SecureRandom random;

    @Inject
    public FakeUserGeneratorService(SecureRandom random) {
        this.random = random;
    }

    public User generateUser() {
        UUID id = UUID.randomUUID();
        String fakeName = createFakeName();
        int fakeAge = createFakeAge();
        return new User(id, fakeName, fakeAge);
    }

    private String createFakeName() {
        // Ascii codes of lowercase letters =>  97 - 122
        final int firstLowercaseLetterAscii = 97;
        final int lowercaseLetterRange = 25;

        // Maximum length random name = 20
        final int minNameLength = 4;
        int randomNameLength = (random.nextInt(17)) + minNameLength;

        // Algorithm to generate the random name
        StringBuilder randomName = new StringBuilder();
        for (int i = 0; i < randomNameLength; i++) {
            char randChar = (char) (random.nextInt(lowercaseLetterRange + 1) + firstLowercaseLetterAscii);
            randomName.append(randChar);
        }
        return randomName.toString();
    }

    private int createFakeAge() {
        final int maxAge = 100;
        return random.nextInt(maxAge);
    }
}
