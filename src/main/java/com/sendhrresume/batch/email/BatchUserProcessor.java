package com.sendhrresume.batch.email;


import com.sendhrresume.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BatchUserProcessor implements ItemProcessor<User,User> {

    @Override
    public User process(User item) throws Exception {
        log.info("Processing user with email: {}", item.getEmail());
        if (item.getEmail() == null) {
            return null;
        }
        item.setName(item.getName().toUpperCase());
        return item;
    }
}
