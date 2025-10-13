package com.wbg.filesummary.file_summary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
    private Long id;
    private String fileName;
    private String fileFormat;
    private String status;
}
