package com.sendhrresume.service;

import com.commondto.model.EmailRequest;
import com.commondto.topic.SendHrResumeTopic;
import com.sendhrresume.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEmailPublisherService {

    private final KafkaTemplate<String, EmailRequest> kafkaTemplate;

    @Value("${kafka.topic.send-email}")
    private String sendEmailTopic;

    public void publishNotification(User user) throws Exception {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(user.getEmail());
            emailRequest.setSubject("Your Resume has been processed");
            emailRequest.setBody("Dear " + user.getName() + ",\n\nYour resume has been successfully processed. We will get back to you shortly.\n\nBest regards,\nSendHRResume Team");
            kafkaTemplate.send(sendEmailTopic, emailRequest);
    }
}
