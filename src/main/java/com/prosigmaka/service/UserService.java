package com.prosigmaka.service;

import com.prosigmaka.entity.User;

import java.util.List;

public interface UserService {
    boolean isExist(String username);

    User create(User reqUser);

    List<User> getAll();

    User get(String username);

    User update(String username, User reqUser);

    void delete(String username);

}
