package com.wbg.filesummary.file_summary.dto;

public record FileResponse(
        Long id,
        String fileName,
        String fileFormat,
        String status) {
}
