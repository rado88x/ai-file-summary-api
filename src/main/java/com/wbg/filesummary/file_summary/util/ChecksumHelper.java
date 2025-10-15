package com.wbg.filesummary.file_summary.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChecksumHelper {

    private static final ObjectMapper CANONICAL_MAPPER = JsonMapper.builder()
            .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
            .build();

    private ChecksumHelper() {
    }

    public static String sha256Hex(byte[] bytes) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(bytes);
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 unavailable", e);
        }
    }

    public static String sha256HexCanonicalJson(ObjectNode node) {

        try {
            byte[] json = CANONICAL_MAPPER.writeValueAsBytes(node);
            return sha256Hex(json);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to canonicalize  JSON", e);
        }
    }

}
