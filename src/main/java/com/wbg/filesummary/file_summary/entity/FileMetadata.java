package com.wbg.filesummary.file_summary.entity;

import com.wbg.filesummary.file_summary.util.FileMetadataChecksum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "file_metadata",
        indexes = {
                @Index(name = "idx_content_sha256", columnList = "contentSha256"),
                @Index(name = "idx_metadata_sha256", columnList = "metadataSha256")
        })
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

    @Column(length = 64)
    private String contentSha256;

    @Column(length = 64)
    private String metadataSha256;

//    @PrePersist
//    @PreUpdate
//    private void recomputeChecksums() {
//        this.metadataSha256 = FileMetadataChecksum.computeMetadataSha256(this);
//    }


}
