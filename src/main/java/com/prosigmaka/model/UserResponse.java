package com.prosigmaka.model;

import com.prosigmaka.entity.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final String username;
    private final String role;

    public UserResponse(User u) {
        this.username = u.getUsername();
        this.role = u.getRole();
    }
}
