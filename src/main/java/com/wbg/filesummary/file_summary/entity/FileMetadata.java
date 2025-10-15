package com.wbg.filesummary.file_summary.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "file_metadata")
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String filePath;
    private String fileFormat;
    private Long fileSize;
    private String checksum;

    @Column(columnDefinition = "TEXT")  // or @Lob for long text
    private String textContent;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(length = 2000)
    private String status;

    @Column(length = 2000)
    private String errorMessage;

    private LocalDateTime lastProcessed;

}
