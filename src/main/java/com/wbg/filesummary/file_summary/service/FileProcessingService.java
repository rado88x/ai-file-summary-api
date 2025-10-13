package com.wbg.filesummary.file_summary.service;

import com.wbg.filesummary.file_summary.entity.FileMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileProcessingService {
    private static final Logger logger = LoggerFactory.getLogger(FileProcessingService.class);

    private final FileMetadataService metadataService;
    private final FileContentExtractor contentExtractor;
    private final OpenAIService openAIService;

    public FileProcessingService(FileMetadataService metadataService, FileContentExtractor contentExtractor, OpenAIService openAIService) {
        this.metadataService = metadataService;
        this.contentExtractor = contentExtractor;
        this.openAIService = openAIService;
    }

    @Async ("fileProcessorExecutor")
    public void processFile(File file) {
        logger.info("Starting asynchronous processing for file: {}", file.getName());
        FileMetadata metadata = null;

        metadata = metadataService.createInitialMetadata(file);
    }
}
