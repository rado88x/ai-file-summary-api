package com.wbg.filesummary.file_summary.service;

import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public interface FileContentExtractor {
    String extract(Path file) throws Exception;
}
