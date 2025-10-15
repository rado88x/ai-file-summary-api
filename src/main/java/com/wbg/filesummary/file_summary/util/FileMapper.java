package com.wbg.filesummary.file_summary.util;

import com.wbg.filesummary.file_summary.dto.ContentResponse;
import com.wbg.filesummary.file_summary.dto.FileResponse;
import com.wbg.filesummary.file_summary.dto.SummaryResponse;
import com.wbg.filesummary.file_summary.entity.FileMetadata;
import com.wbg.filesummary.file_summary.service.FileMetadataService;

public class FileMapper {


    private static FileMetadataService service;

    private FileMapper(FileMetadataService service) {
        FileMapper.service = service;
    }

    public static FileResponse mapFile(FileMetadata fileMetadata) {
        FileResponse fileResponse = new FileResponse(fileMetadata.getId(), fileMetadata.getFileName(), fileMetadata.getFileFormat(), fileMetadata.getStatus());
        return fileResponse;
    }

    public static SummaryResponse mapSummary(Long id) {
        FileMetadata fileMetadata = service.findById(id);
        SummaryResponse summaryResponse = new SummaryResponse(fileMetadata.getFileName(), fileMetadata.getSummary(), fileMetadata.getStatus());
        return summaryResponse;
    }

}
