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

    public static FileResponse mapSummary(FileMetadata fm) {
        FileResponse fr = new FileResponse();
        fr.setFileName(fm.getFileName());
        fr.setFileFormat(fm.getFileFormat());
        fr.setId(fm.getId());
        fr.setStatus(fm.getStatus());
        return fr;
    }

    public static SummaryResponse mapSummary(Long id) {
        SummaryResponse sr = new SummaryResponse();
        FileMetadata fm = service.findById(id);
        sr.setFileName(fm.getFileName());
        sr.setSummary(fm.getSummary());
        sr.setStatus(fm.getStatus());
        return sr;
    }

}
