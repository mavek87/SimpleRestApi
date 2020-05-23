package com.matteoveroni.simplerestapi.core.repositories;

import com.matteoveroni.simplerestapi.core.dto.User;
import java.util.*;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

@ApplicationScoped
@Default
public class MemoryVolatileUserRepository implements UserRepository {

    private final Map<UUID, User> dbInMemory = new HashMap<>();

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(dbInMemory.values());
    }

    @Override
    public Optional<User> getUserById(UUID userId) {
        return Optional.ofNullable(dbInMemory.get(userId));
    }

    @Override
    public User createUser(User user) {
        User newUser = new User(UUID.randomUUID(), user.getName(), user.getAge());
        return dbInMemory.put(newUser.getId(), newUser);
    }

    @Override
    public Optional<User> deleteUser(UUID userId) {
        return Optional.of(dbInMemory.remove(userId));
    }

    @Override
    public Optional<User> updateUser(User user) {
        UUID id = user.getId();
        if (id == null) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(dbInMemory.put(user.getId(), user));
        }
    }
}
