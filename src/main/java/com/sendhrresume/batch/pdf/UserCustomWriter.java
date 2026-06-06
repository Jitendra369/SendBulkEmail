package com.sendhrresume.batch.pdf;

import com.sendhrresume.entity.User;
import com.sendhrresume.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCustomWriter implements ItemWriter<User> {

    private final UserService userService;

    @Override
    public void write(Chunk<? extends User> chunk) throws Exception {
        List<? extends User> users = chunk.getItems();

        for (User user : users) {
            log.info("Writing user to database: {}", user.getEmail());
            userService.addUser(user);
        }
    }
}
