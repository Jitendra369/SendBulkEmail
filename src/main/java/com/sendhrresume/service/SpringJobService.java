package com.sendhrresume.service;

import com.commondto.constant.JobNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.JobName;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpringJobService {

    private final JobExplorer jobExplorer;

    public void getJobInFormation(String jobType) {
        List<JobInstance> jobInstances = jobExplorer.getJobInstances(JobNames.PDF_READ_FILE, 0, 10);
        log.info("Printing Job Details for Job Type: {}", jobType);
        for (JobInstance jobInstance : jobInstances) {
            log.info("Job Instance ID: {}, Job Name: {}", jobInstance.getId(), jobInstance.getJobName());
        }
    }
}
