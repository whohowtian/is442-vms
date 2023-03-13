package com.g2t3.vms.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.g2t3.vms.util.PdfGenerator;

@RestController
@RequestMapping("/api")
public class PdfController {
    @Autowired
    private PdfGenerator pdfGenerator;

    @PostMapping("/generatepdf")
    public ResponseEntity<?> generatePDF(@RequestBody String test) throws FileNotFoundException, IOException {
        pdfGenerator.generateHtmlToPdf(test);
        
        return new ResponseEntity<String>("PDF generated", HttpStatus.OK);
    } 
}
