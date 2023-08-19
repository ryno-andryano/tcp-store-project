package com.prosigmaka.controller;

import com.prosigmaka.entity.User;
import com.prosigmaka.model.ResponseEnvelope;
import com.prosigmaka.model.UserDto;
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
    private ResponseEntity<ResponseEnvelope> register(@RequestBody UserDto userDto) {
        if (userService.isExist(userDto.getUsername())) {
            HttpStatus status = HttpStatus.CONFLICT;
            return new ResponseEntity<>(new ResponseEnvelope(status, "Username already used"), status);
        }

        userDto.setRole("ROLE_CUSTOMER");
        User user = userService.create(userDto);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(new ResponseEnvelope(status, user), status);
    }
}
