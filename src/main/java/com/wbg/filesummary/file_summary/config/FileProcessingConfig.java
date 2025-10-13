package com.wbg.filesummary.file_summary.config;

import com.wbg.filesummary.file_summary.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileProcessingConfig {
    @Bean("fileContentExtractor")
    public FileContentExtractor fileContentExtractor() {
        return new TikaFileContentExtractor();
    }
}