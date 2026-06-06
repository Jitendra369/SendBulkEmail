package com.sendhrresume.batch.email;

import com.sendhrresume.entity.User;
import com.sendhrresume.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BatchUserReader implements ItemReader<User> {

    private final UserService userService;
    private List<User> users;
    private int nextUserIndex;


    @PostConstruct
    public void readData(){
        users = userService.viewAllUsers();
        nextUserIndex = 0;
    }

    @Override
    public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // todo : need to change using table value [ isSendEmail = false ]
        if (nextUserIndex < users.size()) {
            User nextUser = users.get(nextUserIndex);
            nextUserIndex++;
            return nextUser;
        } else {
            return null; // No more data to read
        }
    }
}
