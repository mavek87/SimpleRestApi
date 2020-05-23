package com.matteoveroni.simplerestapi.core.repositories;

import com.matteoveroni.simplerestapi.core.dto.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    List<User> getUsers();

    Optional<User> getUserById(UUID userId);

    User createUser(User user);

    Optional<User> deleteUser(UUID userId);

    Optional<User> updateUser(User user);
}
