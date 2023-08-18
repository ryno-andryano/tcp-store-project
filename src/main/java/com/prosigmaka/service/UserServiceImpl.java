package com.prosigmaka.service;

import com.prosigmaka.entity.User;
import com.prosigmaka.model.UserAuthDto;
import com.prosigmaka.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User create(UserAuthDto userAuthDto, String role) {
        User user = new User();
        user.setUsername(userAuthDto.getUsername());
        user.setPassword(passwordEncoder.encode(userAuthDto.getPassword()));
        user.setRole(role);
        return userRepository.save(user);
    }
}
