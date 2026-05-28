package com.sendhrresume.controller;

import com.sendhrresume.service.SpringJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job")
public class SpringJobController {

    private final SpringJobService springJobService;

    @GetMapping("/view/details/{jobType}")
    public void printJonDetails(@PathVariable String jobType) {
        springJobService.getJobInFormation(jobType);
    }

}
