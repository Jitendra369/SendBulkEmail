package com.sendhrresume.batch.email;

import com.commondto.model.EmailRequest;
import com.sendhrresume.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchUserPublishWriter implements ItemWriter<User> {

    private final KafkaTemplate<String, EmailRequest> kafkaTemplate;

    @Override
    public void write(Chunk<? extends User> chunk) throws Exception {

        for (User user : chunk.getItems()) {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(user.getEmail());
            emailRequest.setSubject("Your Resume has been processed");
            emailRequest.setBody("Dear " + user.getName() + ",\n\nYour resume has been successfully processed. We will get back to you shortly.\n\nBest regards,\nSendHRResume Team");
            kafkaTemplate.send("email-topic", emailRequest);
        }
    }
}
