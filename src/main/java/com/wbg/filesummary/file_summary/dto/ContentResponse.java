package com.wbg.filesummary.file_summary.dto;

public record ContentResponse(
        String fileName,
        String content,
        String status
) {}
