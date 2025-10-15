package com.wbg.filesummary.file_summary.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wbg.filesummary.file_summary.entity.FileMetadata;

import java.nio.file.Path;

public final class FileMetadataChecksum {
    private FileMetadataChecksum() {
    }

    public static String normalizePath(Path path) {
        String abs = path.toAbsolutePath().normalize().toString();
        return abs.toLowerCase();
    }

    public static String computeMetadataSha256(FileMetadata fm) {

        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("normalizedPath", FileMetadataChecksum.normalizePath(Path.of(fm.getFilePath())));
        node.put("fileName", fm.getFileName());
        node.put("format", fm.getFileFormat());
        node.put("sizeBytes", fm.getFileSize());
        return ChecksumHelper.sha256HexCanonicalJson(node);
    }


}
