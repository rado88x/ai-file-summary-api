package com.wbg.filesummary.file_summary.util;

import com.wbg.filesummary.file_summary.repository.FileMetadataRepository;
import com.wbg.filesummary.file_summary.service.FileMetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileScannerHelper {
    private static final Logger logger = LoggerFactory.getLogger(FileScannerHelper.class);

    private static final String DEFAULT_SCAN_PATH = "Task Documents";

    private final FileMetadataService metadataService;

    public FileScannerHelper(FileMetadataService metadataService) {
        this.metadataService = metadataService;
    }

    public List<File> scanFolder() {
        cleanUpHelper(); // cleaning db records during dev phase

        Path folderPath = Path.of(DEFAULT_SCAN_PATH);
        System.out.println("Folder Path = " + folderPath);
        System.out.println("Absolute Path = " + folderPath.toAbsolutePath());
        if (Files.exists(folderPath)) {
            logger.warn("Scanning path not found: {}", folderPath.toAbsolutePath());

            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                logger.error("Failed to create scanning directory: {}", folderPath.toAbsolutePath(), e);
                return Collections.emptyList();
            }
        }

        try (Stream<Path> paths = Files.walk(folderPath)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Error scanning folder: {}", folderPath.toAbsolutePath(), e);
            return Collections.emptyList();
        }
    }

    public void cleanUpHelper() {
        metadataService.cleanUp();
    }

}
