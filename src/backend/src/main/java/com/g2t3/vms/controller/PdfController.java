package com.g2t3.vms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.g2t3.vms.model.Pdf;
import com.g2t3.vms.response.ResponseHandler;
import com.g2t3.vms.service.PdfService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Operation(summary = "Retrieve PDF by file name, Download PDF in Downloads folder", description="file name convention: <entityUEN>_<formName>",
    responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pdf.class))),
    })
    @GetMapping("/{fileName}")
    public ResponseEntity<?> retrievePDFByFileName(@PathVariable String fileName) {

        try {
            pdfService.retrievePDFByFileName(fileName);
            return ResponseHandler.generateResponse("Successful", HttpStatus.OK, null);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Operation(summary = "Stream PDF and look at it directly in Postman", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pdf.class))),
    })
    @GetMapping("/stream/{fileId}")
    public void streamPdf(@PathVariable String fileId, HttpServletResponse response) throws IllegalStateException, IOException {
        Pdf pdf = pdfService.streamPDF(fileId);
        FileCopyUtils.copy(pdf.getStream(), response.getOutputStream());
    }


    @Operation(summary = "Save PDF (binary data) in database", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pdf.class))),
    })
    @PostMapping("/save")
    public ResponseEntity<?> savePDF(@RequestParam("title") String title, @RequestParam("file") MultipartFile file) {

        try {
            pdfService.savePDF(title, file);
            return new ResponseEntity<String>("PDF saved", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
