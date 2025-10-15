package com.wbg.filesummary.file_summary.controller;

import com.wbg.filesummary.file_summary.dto.ContentResponse;
import com.wbg.filesummary.file_summary.dto.FileResponse;
import com.wbg.filesummary.file_summary.dto.SummaryResponse;
import com.wbg.filesummary.file_summary.service.FileMetadataService;
import com.wbg.filesummary.file_summary.service.FileProcessingService;
import com.wbg.filesummary.file_summary.util.FileScannerHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.io.File;
import java.util.List;

@Tag(name = "Files", description = "File upload & summary APIs")
@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {

    private final FileScannerHelper fileScannerHelper;
    private final FileProcessingService fileProcessingService;
    private final FileMetadataService metadataService;


    public FileController(FileScannerHelper fileScannerHelper, FileProcessingService fileProcessingService, FileMetadataService metadataService) {
        this.fileScannerHelper = fileScannerHelper;
        this.fileProcessingService = fileProcessingService;
        this.metadataService = metadataService;
    }

    @Operation(
            summary = "Re-scan folder and (re)process files",
            description = "Scans the configured folder and starts asynchronous processing for all discovered files."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "202",
                    description = "Processing started",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(responseCode = "500", description = "Unexpected server error")
    })

    @PostMapping("/refresh") //process all files in folder.
    public ResponseEntity<String> refresh() {
        List<File> files = fileScannerHelper.scanFolder();

        files.forEach(fileProcessingService::processFile);
        return ResponseEntity.accepted().body("File processing started asynchronously for " + files.size() + " files.");
    }


    @Operation(
            summary = "List indexed files",
            description = "Returns metadata for all files known to the system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Files listed",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FileResponse.class)))
            )
    })
    @GetMapping("/files")
    public ResponseEntity<List<FileResponse>> listFiles() {
        List<FileResponse> result = metadataService.findAll();
        return ResponseEntity.ok(result);
    }


    @Operation(
            summary = "Get file summary by ID",
            description = "Returns the generated summary for the file with the given ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Summary returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SummaryResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @GetMapping("/files/{id}/summary")
    public ResponseEntity<SummaryResponse> fileSummary(@Parameter(description = "Internal file identifier", example = "123")
                                                       @PathVariable Long id) {
        SummaryResponse response = metadataService.getSummaryById(id);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Get file content by ID",
            description = "Returns extracted/parsed content for the file with the given ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Content returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContentResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @GetMapping("/files/{id}/content")
    public ResponseEntity<ContentResponse> fileContent(@Parameter(description = "Internal file identifier", example = "123")
                                                       @PathVariable Long id) {
        ContentResponse response = metadataService.getContentById(id);
        return ResponseEntity.ok(response);
    }
}
