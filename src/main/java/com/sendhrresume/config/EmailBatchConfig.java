package com.sendhrresume.config;

import com.sendhrresume.batch.email.BatchUserProcessor;
import com.sendhrresume.batch.email.BatchUserPublishWriter;
import com.sendhrresume.batch.email.BatchUserReader;
import com.sendhrresume.entity.User;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class EmailBatchConfig {

    // this is for huge data
    @Bean
    public JpaPagingItemReader<User> customReader(EntityManagerFactory emf) {
        JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(emf);
        reader.setQueryString("SELECT u FROM User u");
        reader.setPageSize(10);
        return reader;
    }

    @Bean
    public Step sendHrEmailStep(
            JobRepository jobRepository,
            PlatformTransactionManager txManager,
            JpaPagingItemReader<User> jpaPagingItemReader,
            BatchUserProcessor processor,
            BatchUserPublishWriter writer,
            BatchUserReader reader
    ) {

        return new StepBuilder(
                "sendHrEmail-step",
                jobRepository).<User, User>chunk(100, txManager)
                .processor(processor)
                .reader(reader)
                .writer(writer)
                .build();
    }
}
