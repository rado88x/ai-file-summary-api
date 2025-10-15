package com.wbg.filesummary.file_summary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ContentResponse", description = "Extracted text content for a file")
public record ContentResponse(
        @Schema(description = "Original file name", example = "report_q3.pdf")
        String fileName,

        @Schema(description = "Extracted (possibly normalized) text content",
                example = "Lorem ipsum dolor...")
        String content,

        @Schema(description = "Processing status", example = "DONE",
                allowableValues = {"PENDING", "PROCESSING", "DONE", "FAILED"})
        String status
) {}
