package com.sendhrresume.controller;


import com.sendhrresume.batch.BatchPdfFileReadingService;
import com.sendhrresume.dto.FileUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/file")
public class FileReadController {

    private final BatchPdfFileReadingService batchFileService;

    @PostMapping("/readFile")
    public void readFile(@RequestBody FileUploadDto fileUploadDto){
        batchFileService.startJob(fileUploadDto);
    }
}
