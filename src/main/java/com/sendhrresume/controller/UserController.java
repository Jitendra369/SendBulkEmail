package com.sendhrresume.controller;

import com.sendhrresume.entity.User;
import com.sendhrresume.service.KafkaEmailPublisherService;
import com.sendhrresume.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    public final UserService userService;
    private final KafkaEmailPublisherService kafkaEmailPublisherService;

    @GetMapping("/page/all")
    public Page<User> getAllUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortedBy
    ) {
        return userService.getUser(page, size, sortedBy);
    }

    @GetMapping("/publish")
    public void getAllUser() throws Exception {
        List<User> users = userService.viewAllUsers().stream().limit(10).toList();
        if (!users.isEmpty()) {
            for (User user : users) {
                kafkaEmailPublisherService.publishNotification(user);
            }
        }
    }
}
