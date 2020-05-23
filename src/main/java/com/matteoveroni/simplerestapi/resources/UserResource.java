package com.matteoveroni.simplerestapi.resources;

import com.matteoveroni.simplerestapi.core.dto.User;
import com.matteoveroni.simplerestapi.core.repositories.UserRepository;
import com.matteoveroni.simplerestapi.core.services.FakeUserGeneratorService;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import java.util.List;
import javax.inject.Inject;

public class UserResource implements CrudHandler {

    private final UserRepository userRepository;
    private final FakeUserGeneratorService userGeneratorService;

    @Inject
    public UserResource(UserRepository userRepository, FakeUserGeneratorService userGeneratorService) {
        this.userRepository = userRepository;
        this.userGeneratorService = userGeneratorService;
    }

    @Override
    @OpenApi(
            method = HttpMethod.POST,
            operationId = "users",
            summary = "Resource user",
            description = "Create a new user",
            tags = {"user"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "OK",
                            content = @OpenApiContent(type = "application/json", from = String.class)
                    )
            }
    )
    public void create(Context context) {
        User newUser = userGeneratorService.generateUser();
        userRepository.createUser(newUser);
    }

    @Override
    public void delete(Context context, String userId) {
    }

    @Override
    @OpenApi(
            method = HttpMethod.GET,
            operationId = "users",
            summary = "Resource user",
            description = "Get all the users",
            tags = {"user"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "OK",
                            content = @OpenApiContent(type = "application/json", from = String.class)
                    )
            }
    )
    public void getAll(Context context) {
        List<User> users = userRepository.getUsers();
        context.json(users);
    }

    @Override
    public void getOne(Context context, String userId) {

    }

    @Override
    public void update(Context context, String userId) {

    }
}
