package com.wbg.filesummary.file_summary.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SummaryResponse", description = "Generated summary for a file")
public record SummaryResponse(
        @Schema(description = "Original file name", example = "report_q3.pdf")
        String fileName,

        @Schema(description = "Concise, generated summary of the file content",
                example = "The report lorem ipsum dolor...")
        String summary,

        @Schema(description = "Processing status", example = "DONE",
                allowableValues = {"PENDING", "PROCESSING", "DONE", "FAILED"})
        String status
) {}