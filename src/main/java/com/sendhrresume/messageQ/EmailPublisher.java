package com.sendhrresume.messageQ;

import com.commondto.model.EmailRequest;
import com.sendhrresume.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailPublisher {

    private final KafkaTemplate<String, EmailRequest> kafkaTemplate;
    private final UserService userService;

    public void sendHrResumeMessage(){

//        userService.getUser()

//        EmailRequest.builder()
//                .to("")
    }
}
