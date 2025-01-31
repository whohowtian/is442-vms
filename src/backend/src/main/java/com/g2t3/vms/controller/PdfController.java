package com.g2t3.vms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.request.PdfRequest;
import com.g2t3.vms.response.ResponseHandler;
import com.g2t3.vms.service.PdfService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin
@RestController
@RequestMapping("/api/pdf")
@Validated
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Operation(summary = "Retrieve PDF by file name, Download PDF in Downloads folder", description="file name convention: <entityUEN>_<formTemplateName>",
    responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PdfRequest.class))),
    })
    @GetMapping("/{fileName}")
    public ResponseEntity<?> retrievePDFByFileName(@PathVariable String fileName) {

        try {
            pdfService.retrievePDFByFileName(fileName);
            return ResponseHandler.generateResponse("Successful", HttpStatus.OK, null);
        } catch (IllegalStateException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    @Operation(summary = "Retrieve PDF by vendor UEN, Download all PDFs in a ZIP file under Downloads folder",
    responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PdfRequest.class))),
    })
    @GetMapping("/vendor/{vendorUEN}")
    public ResponseEntity<?> retrievePDFByVendor(@PathVariable String vendorUEN) {

        try {
            pdfService.retrievePDFByVendor(vendorUEN);
            return ResponseHandler.generateResponse("Successful", HttpStatus.OK, null);
        } catch (IllegalStateException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    @Operation(summary = "Save PDF (binary data) in database", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PdfRequest.class))),
    })
    @PostMapping("/save")
    public ResponseEntity<?> savePDF(@RequestParam("title") String title, @RequestParam("file") MultipartFile file) {

        try {
            pdfService.savePDF(title, file);
            return new ResponseEntity<String>("PDF saved", HttpStatus.OK);
        } catch (ResourceAlreadyExistException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (IOException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }

    }

    @Operation(summary = "Healthcheck", description="file name convention: <entityUEN>_<formTemplateName>", responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PdfRequest.class))),
    })
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> healthCheck() {
        return ResponseHandler.generateResponse("PdfController connected.", HttpStatus.OK, null);
    }

}
