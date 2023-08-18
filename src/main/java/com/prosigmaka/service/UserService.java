package com.prosigmaka.service;

import com.prosigmaka.entity.User;
import com.prosigmaka.model.UserAuthDto;

public interface UserService {
    boolean isExist(String username);

    User create(UserAuthDto userAuthDto, String role);
}
