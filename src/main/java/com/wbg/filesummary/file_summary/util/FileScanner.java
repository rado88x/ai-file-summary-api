package com.wbg.filesummary.file_summary.util;

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
public class FileScanner {
    private static final Logger logger = LoggerFactory.getLogger(FileScanner.class);

    private static final String DEFAULT_SCAN_PATH = "Task Documents";

    public List<File> scanFolder() {

        Path folderPath = Path.of(DEFAULT_SCAN_PATH);
        System.out.println("Folder Path = " + folderPath);
        System.out.println("Absolute Path = " + folderPath.toAbsolutePath());
        if (Files.exists(folderPath)) {
            logger.warn("Scanning path not found: {}. Creating it.", folderPath.toAbsolutePath());

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

}
