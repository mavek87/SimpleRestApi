package com.matteoveroni.simplerestapi.resources;

import com.matteoveroni.simplerestapi.core.dto.User;
import com.matteoveroni.simplerestapi.core.repositories.UserRepository;
import com.matteoveroni.simplerestapi.core.services.FakeUserGeneratorService;
import com.matteoveroni.simplerestapi.responses.UserResponse;
import com.matteoveroni.simplerestapi.responses.UsersResponse;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class UsersResource implements CrudHandler {

    private final UserRepository userRepository;
    private final FakeUserGeneratorService userGeneratorService;

    @Inject
    public UsersResource(UserRepository userRepository, FakeUserGeneratorService userGeneratorService) {
        this.userRepository = userRepository;
        this.userGeneratorService = userGeneratorService;
    }

    @Override
    @OpenApi(
            method = HttpMethod.GET,
            operationId = "get_users",
            summary = "Get users",
            description = "Get all the users",
            tags = {"user"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "OK",
                            content = @OpenApiContent(type = "application/json", from = UsersResponse.class)
                    )
            }
    )
    public void getAll(Context context) {
        List<User> users = userRepository.getUsers();
        context.json(new UsersResponse(users));
    }

    @Override
    @OpenApi(
            method = HttpMethod.GET,
            operationId = "get_user",
            summary = "Get user",
            description = "Get a single user by id",
            tags = {"user"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "OK",
                            content = @OpenApiContent(type = "application/json", from = UserResponse.class)
                    )
            }
    )
    public void getOne(Context context, String userId) {
        try {
            UUID id = UUID.fromString(userId);
            Optional<User> user = userRepository.getUserById(id);
            if (user.isPresent()) {
                context.json(new UserResponse(user.get()));
            } else {
                context.json(new UserResponse(null));
            }
        } catch (IllegalArgumentException ex) {
            throw new BadRequestResponse("Bad request " + ex.getLocalizedMessage());
        }
    }

    @Override
    @OpenApi(
            method = HttpMethod.POST,
            operationId = "create_user",
            summary = "Create user",
            description = "Create a new user",
            tags = {"user"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "OK",
                            content = @OpenApiContent(type = "application/json", from = UserResponse.class)
                    )
            }
    )
    public void create(Context context) {
        User newUser = userGeneratorService.generateUser();
        userRepository.createUser(newUser);
        context.json(new UserResponse(newUser));
    }

    @Override
    @OpenApi(
            method = HttpMethod.DELETE,
            operationId = "delete_user",
            summary = "Delete user",
            description = "Delete a user by id",
            tags = {"user"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "OK",
                            content = @OpenApiContent(type = "application/json", from = UserResponse.class)
                    )
            }
    )
    public void delete(Context context, String userId) {
        try {
            UUID id = UUID.fromString(userId);
            Optional<User> removedUser = userRepository.deleteUser(id);
            if (removedUser.isPresent()) {
                context.json(new UserResponse(removedUser.get()));
            } else {
                context.json(new UserResponse(null));
            }
        } catch (IllegalArgumentException ex) {
            throw new BadRequestResponse("Bad request " + ex.getLocalizedMessage());
        }
    }

    @Override
    @OpenApi(
            method = HttpMethod.PATCH,
            operationId = "update_user",
            summary = "Update user",
            description = "Update a user",
            tags = {"user"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "OK",
                            content = @OpenApiContent(type = "application/json", from = UserResponse.class)
                    )
            }
    )
    public void update(Context context, String userId) {

    }
}
