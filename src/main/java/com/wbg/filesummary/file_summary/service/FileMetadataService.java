package com.wbg.filesummary.file_summary.service;


import com.wbg.filesummary.file_summary.dto.SummaryResponse;
import com.wbg.filesummary.file_summary.entity.FileMetadata;
import com.wbg.filesummary.file_summary.exception.ResourceNotFoundException;
import com.wbg.filesummary.file_summary.repository.FileMetadataRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileMetadataService {

    private static final Logger logger = LoggerFactory.getLogger(FileMetadataService.class);

    private final FileMetadataRepository repository;

    public FileMetadataService(FileMetadataRepository repository) {
        this.repository = repository;
    }

    public List<FileMetadata> findAll() {
        return repository.findAll();
    }

    public FileMetadata findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File with ID " + id + " not found."));
    }

    public void save(FileMetadata metadata) {
        metadata.setLastProcessed(LocalDateTime.now());
        repository.save(metadata);
    }

    public SummaryResponse getSummaryById(Long id) {
        FileMetadata metadata = findById(id);
        return new SummaryResponse(metadata.getFileName(), metadata.getSummary(), metadata.getStatus());
    }

    public String getContentById(Long id) {
        FileMetadata metadata = findById(id);
        return metadata.getTextContent();
    }

    public FileMetadata createInitialMetadata(File file) {
        Optional<FileMetadata> existingMetadata = repository.findByFilePath(file.getAbsolutePath());
        FileMetadata metadata = existingMetadata.orElseGet(FileMetadata::new);

        metadata.setFileName(file.getName());
        metadata.setFilePath(file.getAbsolutePath());
        metadata.setFileSize(file.length());

        metadata.setFileFormat(getFileExtension(file.getName()));
        metadata.setStatus("PENDING");
        logger.info("Step 1 -> Persisting initial entity '{}' to db: ", file.getName());

        return repository.save(metadata);
    }

    private String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        return (lastDot > 0) ? fileName.substring(lastDot + 1) : "UNKNOWN";
    }

    public void cleanUp() {
        repository.deleteAll();
    }

}
