package com.wbg.filesummary.file_summary.repository;

import com.wbg.filesummary.file_summary.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    Optional<FileMetadata> findByFilePath(String filePath);
    Optional<FileMetadata> findByChecksum(String checksum);
}
