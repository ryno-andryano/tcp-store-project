package com.prosigmaka.service;

import com.prosigmaka.entity.User;
import com.prosigmaka.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional
    public User create(User reqUser) {
        User user = new User();
        user.setUsername(reqUser.getUsername());
        user.setPassword(passwordEncoder.encode(reqUser.getPassword()));
        user.setRole(reqUser.getRole());
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User get(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
    }

    @Override
    @Transactional
    public User update(String username, User reqUser) {
        List<? extends GrantedAuthority> authorities = (List<? extends GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String authority = authorities.get(0).getAuthority();
        User user = get(username);

        if (reqUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(reqUser.getPassword()));
        }
        if (reqUser.getRole() != null) {
            if (authority.equals("ROLE_ADMIN")) {
                user.setRole(reqUser.getRole());
            }
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }
}
