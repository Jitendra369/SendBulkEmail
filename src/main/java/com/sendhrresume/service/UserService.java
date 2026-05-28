package com.sendhrresume.service;

import com.sendhrresume.entity.User;
import com.sendhrresume.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User addUser(User user) {
        try {
            user.setDataAddedToDb(true);
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("user is not added in to Database for email: {}. Error: {}", user.getEmail(), e.getMessage());
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
