package com.wbg.filesummary.file_summary.controller;

import com.wbg.filesummary.file_summary.service.FileMetadataService;
import com.wbg.filesummary.file_summary.service.FileProcessingService;
import com.wbg.filesummary.file_summary.util.FileScanner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import java.io.File;
import java.util.List;


@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {
    private final FileScanner fileScanner;
    private final FileProcessingService fileProcessingService;
    private final FileMetadataService metadataService;


    public FileController(FileScanner fileScanner, FileProcessingService fileProcessingService, FileMetadataService metadataService) {
        this.fileScanner = fileScanner;
        this.fileProcessingService = fileProcessingService;
        this.metadataService = metadataService;
    }

    @PostMapping("/refresh") //process all files in folder.
    public ResponseEntity<String> refresh() {
        List<File> files = fileScanner.scanFolder();

        files.forEach(fileProcessingService::processFile);
        return ResponseEntity.accepted().body("File processing started asynchronously for " + files.size() + " files.");
    }

}
