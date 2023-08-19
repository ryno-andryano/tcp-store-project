package com.prosigmaka.service;

import com.prosigmaka.entity.User;
import com.prosigmaka.model.UserDto;

import java.util.List;

public interface UserService {
    boolean isExist(String username);

    User create(UserDto userDto);

    List<User> getAll();

    User get(String username);

    User update(String username, UserDto userDto);

    void delete(String username);

}
