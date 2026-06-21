package com.sendhrresume.batch.pdf;

import com.commondto.constant.JobNames;
import com.sendhrresume.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchFileReadConfigSteps {

    @Bean
    @StepScope
    public PdfTableReader pdfUserReader(
            @Value("#{jobParameters['filePath']}")
            String filePath) {

        return new PdfTableReader(filePath);
    }

    /**
     * Read 5 users
     * Process 5 users
     * Write 5 users
     * Commit transaction
     */
    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      PdfTableReader pdfTableReader,
                      UserProcessor userProcessor,
//                      JpaItemWriter<User> writer
                      UserCustomWriter writer
    ) {

        return new StepBuilder("pdf-step", jobRepository)
                .<User, User>chunk(5, transactionManager)
                .reader(pdfTableReader)
                .processor(userProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job pdfJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder(JobNames.PDF_READ_FILE, jobRepository)
//                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }
}
