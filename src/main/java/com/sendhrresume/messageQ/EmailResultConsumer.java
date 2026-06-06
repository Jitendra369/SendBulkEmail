package com.sendhrresume.messageQ;


import com.commondto.model.EmailResult;
import com.sendhrresume.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailResultConsumer {

    private final UserService userService;

    @KafkaListener(
            topics = "email.result.sendHr",
            groupId = "email-result-consumer-group"
    )
    public void onEmailResult(@Payload EmailResult emailResult){
        if (emailResult.isSuccess()){
            userService.markEmailAsSent(Math.toIntExact(emailResult.getRecordId()));
        }
    }
}
