package com.sendhrresume.batch;

import com.sendhrresume.dto.FileUploadDto;
import com.sendhrresume.entity.FileSource;
import com.sendhrresume.repo.FileRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchPdfFileReadingService {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final FileRepo fileRepo;

    public String startJob(FileUploadDto fileUploadDto) {

        Optional<FileSource> fileSourceOptional = fileRepo.findByFileName(fileUploadDto.getFileName());

        if (fileSourceOptional.isEmpty()){
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("fileName", fileUploadDto.getFileName())
                    .addString("filePath", fileUploadDto.getFileLocation())
                    .addString("timestamp", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();

            try {
                jobLauncher.run(job, jobParameters);
                fileRepo.save(toEntity(fileUploadDto));
                return "job-started";
            } catch (JobExecutionAlreadyRunningException e) {
                throw new RuntimeException(e);
            } catch (JobRestartException e) {
                throw new RuntimeException(e);
            } catch (JobInstanceAlreadyCompleteException e) {
                throw new RuntimeException(e);
            } catch (JobParametersInvalidException e) {
                throw new RuntimeException(e);
            }
        }else{
            log.info("File with name {} already exists in the database. Skipping job execution.", fileUploadDto.getFileName());
            return "file-already-exists";
        }
    }

    private FileSource toEntity(FileUploadDto fileUploadDto){
        if (fileUploadDto == null){{
            log.warn("Received null FileUploadDto, cannot convert to FileSource entity.");
        }}
        FileSource file = new FileSource();
        file.setFileName(fileUploadDto.getFileName());
        file.setFileType(fileUploadDto.getFileType());
        file.setFileLocation(fileUploadDto.getFileLocation());
        return fileRepo.save(file);
    }
}
