package com.prosigmaka.controller;

import com.prosigmaka.entity.User;
import com.prosigmaka.model.ResponseEnvelope;
import com.prosigmaka.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    final private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    private ResponseEntity<ResponseEnvelope> register(@RequestBody User reqUser) {
        if (userService.isExist(reqUser.getUsername())) {
            HttpStatus status = HttpStatus.CONFLICT;
            return new ResponseEntity<>(new ResponseEnvelope(status, "Username already used"), status);
        }

        reqUser.setRole("ROLE_CUSTOMER");
        User result = userService.create(reqUser);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(new ResponseEnvelope(status, result), status);
    }
}
