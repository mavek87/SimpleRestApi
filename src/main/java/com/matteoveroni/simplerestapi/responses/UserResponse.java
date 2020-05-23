package com.matteoveroni.simplerestapi.responses;

import com.google.gson.annotations.SerializedName;
import com.matteoveroni.simplerestapi.core.dto.User;

public class UserResponse {

    @SerializedName("user")
    private final User user;

    public UserResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
