package com.sendhrresume.service;

import com.sendhrresume.entity.User;
import com.sendhrresume.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public int markEmailAsSent(int id ){
        try {
            userRepository.markEmailAsSent(id);
        } catch (Exception e) {
           log.error("Failed to mark email as sent for user with id {}. Error: {}", id, e.getMessage());
        }
        return -1;
    }

    public Page<User> getUser(int page, int size, String sortedBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortedBy).descending());
        return userRepository.findAll(pageRequest);
    }

    public List<User> viewAllUsers() {
        return userRepository.findAll();
    }
}
