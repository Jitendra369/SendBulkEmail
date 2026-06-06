package com.sendhrresume.batch.pdf;

import com.sendhrresume.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User item) throws Exception {
        log.info("processing user with email {} ", item.getEmail()) ;
        item.setEmail(item.getEmail().toLowerCase());
        return item;
    }
}
