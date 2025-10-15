package com.wbg.filesummary.file_summary.dto;


public record SummaryResponse(
        String fileName,
        String summary,
        String status
        ) {
}

