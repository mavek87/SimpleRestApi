package com.matteoveroni.simplerestapi.rest.responses;

import com.google.gson.annotations.SerializedName;
import com.matteoveroni.simplerestapi.core.dto.User;
import java.util.List;

public class UsersResponse {

    @SerializedName("users")
    private final List<User> users;

    @SerializedName("number_of_results")
    private final int numberOfResults;

    public UsersResponse(List<User> users) {
        this.users = users;
        this.numberOfResults = users.size();
    }

    public List<User> getUsers() {
        return users;
    }

    public int getNumberOfResults() {
        return numberOfResults;
    }
}
