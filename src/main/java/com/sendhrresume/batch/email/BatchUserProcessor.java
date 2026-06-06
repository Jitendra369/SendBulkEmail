package com.sendhrresume.batch.email;


import com.sendhrresume.entity.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BatchUserProcessor implements ItemProcessor<User,User> {

    @Override
    public User process(User item) throws Exception {
        if (item.getEmail() == null) {
            return null;
        }
        item.setName(item.getName().toUpperCase());
        return item;
    }
}
