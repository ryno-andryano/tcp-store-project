package com.prosigmaka.controller;

import com.prosigmaka.entity.User;
import com.prosigmaka.model.ResponseEnvelope;
import com.prosigmaka.model.UserAuthDto;
import com.prosigmaka.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    final private UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    private ResponseEntity<Object> register(@RequestBody UserAuthDto userAuthDto) {
        if (userService.isExist(userAuthDto.getUsername())) {
            HttpStatus status = HttpStatus.CONFLICT;
            return new ResponseEntity<>(new ResponseEnvelope(
                    status.value(),
                    "Username already exist",
                    null
            ), status);
        }

        User user = userService.create(userAuthDto, "ROLE_USER");
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(
                status.value(),
                status.getReasonPhrase(),
                user
        ), status);
    }
}
