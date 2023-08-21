package com.prosigmaka.controller;

import com.prosigmaka.entity.User;
import com.prosigmaka.model.ResponseEnvelope;
import com.prosigmaka.model.UserResponse;
import com.prosigmaka.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    final private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseEnvelope> createUser(@RequestBody User reqUser) {
        if (userService.isExist(reqUser.getUsername())) {
            HttpStatus status = HttpStatus.CONFLICT;
            return new ResponseEntity<>(new ResponseEnvelope(status, "Username already used"), status);
        }

        User result = userService.create(reqUser);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(new ResponseEnvelope(status, result), status);
    }

    @GetMapping("/api/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseEnvelope> getAllUsers() {
        List<User> users = userService.getAll();
        List<UserResponse> result = users.stream().map(UserResponse::new).toList();

        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, result), status);
    }

    @GetMapping("/api/user/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == principal.username")
    public ResponseEntity<ResponseEnvelope> getUser(@PathVariable String username) {
        if (!userService.isExist(username)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "User not found"), status);
        }

        User result = userService.get(username);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, result), status);
    }

    @PutMapping("/api/user/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == principal.username")
    public ResponseEntity<ResponseEnvelope> updateUser(@PathVariable String username, @RequestBody User reqUser) {
        if (!userService.isExist(username)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "User not found"), status);
        }

        User result = userService.update(username, reqUser);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, result), status);
    }

    @DeleteMapping("/api/user/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == principal.username")
    public ResponseEntity<ResponseEnvelope> deleteUser(@PathVariable String username) {
        if (!userService.isExist(username)) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(new ResponseEnvelope(status, "User not found"), status);
        }

        userService.delete(username);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(new ResponseEnvelope(status, "User deleted"), status);
    }
}
