package com.matteoveroni.simplerestapi.rest.resources;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.matteoveroni.simplerestapi.core.dto.User;
import com.matteoveroni.simplerestapi.core.repositories.UserRepository;
import com.matteoveroni.simplerestapi.core.services.FakeUserGeneratorService;
import com.matteoveroni.simplerestapi.rest.responses.UserResponse;
import com.matteoveroni.simplerestapi.rest.responses.UsersResponse;
import com.matteoveroni.simplerestapi.utils.JsonUtils;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.plugin.openapi.annotations.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class UsersResource implements CrudHandler {

    private final UserRepository userRepository;
    private final FakeUserGeneratorService userGeneratorService;
    private Gson gson;
    private JsonUtils jsonUtils;

    @Inject
    public UsersResource(UserRepository userRepository, FakeUserGeneratorService userGeneratorService, Gson gson, JsonUtils jsonUtils) {
        this.userRepository = userRepository;
        this.userGeneratorService = userGeneratorService;
        this.gson = gson;
        this.jsonUtils = jsonUtils;
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
    public void getAll(Context ctx) {
        List<User> users = userRepository.getUsers();
        ctx.json(new UsersResponse(users));
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
    public void getOne(Context ctx, String userId) {
        try {
            UUID id = UUID.fromString(userId);
            Optional<User> user = userRepository.getUserById(id);
            if (user.isPresent()) {
                ctx.json(new UserResponse(user.get()));
            } else {
                ctx.json(new UserResponse(null));
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
            requestBody = @OpenApiRequestBody(
                    content = {
                            @OpenApiContent(type = "application/json", from = User.class)
                    }
            ),
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "OK",
                            content = @OpenApiContent(type = "application/json", from = UserResponse.class)
                    )
            }
    )
    public void create(Context ctx) {
        try {
            String jsonBodyData = ctx.body();
            if (!jsonUtils.isValid(jsonBodyData)) {
                throw new IllegalArgumentException("Invalid json");
            }

            User user = gson.fromJson(jsonBodyData, User.class);

            User createdUser = userRepository.createUser(user);

            ctx.json(new UserResponse(createdUser));
        } catch (JsonSyntaxException | IllegalArgumentException ex) {
            throw new BadRequestResponse("Bad request. " + ex.getLocalizedMessage());
        }
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
    public void delete(Context ctx, String userId) {
        try {
            UUID id = UUID.fromString(userId);
            Optional<User> removedUser = userRepository.deleteUser(id);
            if (removedUser.isPresent()) {
                ctx.json(new UserResponse(removedUser.get()));
            } else {
                ctx.json(new UserResponse(null));
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
            requestBody = @OpenApiRequestBody(
                    content = {
                            @OpenApiContent(type = "application/json", from = User.class)
                    }
            ),
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "OK",
                            content = @OpenApiContent(type = "application/json", from = UserResponse.class)
                    )
            }
    )
    public void update(Context ctx, String userId) {
        try {
            UUID id = UUID.fromString(userId);

            String jsonBodyData = ctx.body();
            if (!jsonUtils.isValid(jsonBodyData)) {
                throw new IllegalArgumentException("Invalid json");
            }

            User user = gson.fromJson(jsonBodyData, User.class);
            user.setId(id);

            Optional<User> updatedUser = userRepository.updateUser(user);
            if (updatedUser.isPresent()) {
                ctx.json(new UserResponse(updatedUser.get()));
            } else {
                throw new NotFoundResponse("Id not found");
            }
        } catch (JsonSyntaxException | IllegalArgumentException ex) {
            throw new BadRequestResponse("Bad request. " + ex.getLocalizedMessage());
        }
    }
}