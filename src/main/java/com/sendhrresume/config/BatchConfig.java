package com.sendhrresume.config;

import com.sendhrresume.batch.PdfTableReader;
import com.sendhrresume.batch.UserCustomWriter;
import com.sendhrresume.batch.UserProcessor;
import com.sendhrresume.entity.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    // custom reader, processor, writer
    @Bean
    public PdfTableReader pdfUserReader(){
        return new PdfTableReader();
    }
//
//    @Bean
//    public JpaItemWriter<User> writer(EntityManagerFactory emf){
//        return new JpaItemWriterBuilder<User>()
//                .entityManagerFactory(emf)
//                .build();
//    }

   /**
    *  Read 5 users
    Process 5 users
    Write 5 users
    Commit transaction*/
    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      PdfTableReader pdfTableReader,
                      UserProcessor userProcessor,
//                      JpaItemWriter<User> writer
                      UserCustomWriter writer
                      ){

        return new StepBuilder("pdf-step", jobRepository)
                .<User, User>chunk(5, transactionManager)
                .reader(pdfTableReader)
                .processor(userProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job pdfJob(JobRepository jobRepository, Step step1){
        return new JobBuilder("pdf-job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }
}
