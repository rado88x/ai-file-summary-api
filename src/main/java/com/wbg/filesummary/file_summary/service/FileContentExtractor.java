package com.wbg.filesummary.file_summary.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileContentExtractor {
    private static final Logger logger = LoggerFactory.getLogger(FileContentExtractor.class);

    public String extractText(String filePath, String format) {
        File file = new File(filePath);
        logger.info("Step 2 -> Extracting content for file: {}.", file.getName());
        try {
            return switch (format.toLowerCase()) {
                case "pdf" -> extractPdf(file);
                case "docx" -> extractDocx(file);
                case "txt" -> extractTxt(file);
                default -> {
                    logger.warn("Unsupported file format : {}", format);
                    yield "Unsupported format: " + format;
                }
            };
        } catch (IOException e) {
            logger.error("Failed to extract content from file: {}", filePath, e);
            return "ERROR: Failed to read file content.";
        }
    }

    private String extractTxt(File file) throws IOException {
        return Files.readString(file.toPath());
    }

    private String extractPdf(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String extractDocx(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            XWPFDocument document = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            return extractor.getText();
        }

    }
}
