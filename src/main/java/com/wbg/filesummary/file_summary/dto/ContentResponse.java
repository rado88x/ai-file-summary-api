package com.wbg.filesummary.file_summary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponse {
    private String fileName;
    private String content;
    private String status;

}
