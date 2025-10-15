package com.wbg.filesummary.file_summary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FileResponse", description = "Metadata about a processed file")
public record FileResponse(
        @Schema(description = "Internal identifier", example = "123", type = "integer", format = "int64")
        Long id,

        @Schema(description = "Original file name", example = "report_q3.pdf")
        String fileName,

        @Schema(description = "Detected file format/extension", example = "pdf")
        String fileFormat,

        @Schema(description = "Processing status", example = "DONE",
                allowableValues = {"PENDING", "PROCESSING", "DONE", "FAILED"})
        String status) {
}
