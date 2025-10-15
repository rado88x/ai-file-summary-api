package com.wbg.filesummary.file_summary.service;

import com.wbg.filesummary.file_summary.entity.FileMetadata;
import com.wbg.filesummary.file_summary.exception.RecordAlreadyExistsException;
import com.wbg.filesummary.file_summary.util.FileMetadataChecksum;
import org.springframework.ai.chat.client.ChatClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;


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

    @Async("fileProcessorExecutor")
    public void processFile(File file) {
        logger.info("Starting asynchronous processing for file: {}", file.getName());

        FileMetadata metadata = null;

        try {
            // generate metadata and status tracking (PENDING for initial state)
            metadata = metadataService.createInitialMetadata(file);

            // calculate checksum
            String checksum = FileMetadataChecksum.computeMetadataSha256(metadata);
            metadata.setChecksum(checksum);

            if (metadataService.findByChecksum(checksum).isPresent()) {
                throw new RecordAlreadyExistsException("Record with that checksum already exist.");
            }

            // extracting content
            String content = contentExtractor.extractText(file.getAbsolutePath(), metadata.getFileFormat());
            metadata.setTextContent(content);

            //gen AI resume from ChatGPT
            String summary = openAIService.summarize(content);
            metadata.setSummary(summary);
            metadata.setStatus("SUMMARIZED");

            //persisting updated information to db
            metadataService.save(metadata);
            logger.info("Successfully summarized file: {}", file.getName());

        } catch (Exception e) {
            logger.error("Failed to process file: {}", file.getName(), e);
            if (metadata != null) {
                metadata.setStatus("FAILED: " + e.getMessage());
                metadataService.save(metadata);
            }
        }
    }

}
