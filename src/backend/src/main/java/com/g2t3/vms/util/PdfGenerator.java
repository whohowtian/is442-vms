package com.g2t3.vms.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class PdfGenerator {
    
    private static final String PDF_OUTPUT =  System.getProperty("user.home") + "/Downloads/vendor-form.pdf";

    public static void main(String[] args) {
        try {
            PdfGenerator htmlToPdf = new PdfGenerator();
            htmlToPdf.generateHtmlToPdf(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateHtmlToPdf(String inputHTML) throws IOException {
        // String currentpath = System.getProperty("user.dir");
        // System.out.println("current path is:" + currentpath);

        Document doc = createWellFormedHtml(inputHTML);
        xhtmlToPdf(doc, PDF_OUTPUT);
    }

    private Document createWellFormedHtml(String inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings()
            .syntax(Document.OutputSettings.Syntax.xml);
        return document;
    }

    private void xhtmlToPdf(Document doc, String outputPdf) throws IOException {
        try (OutputStream os = new FileOutputStream(outputPdf)) {
            String baseUri = FileSystems.getDefault()
                .getPath("src/main/resources/")
                .toUri()
                .toString();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(outputPdf);
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(doc), baseUri);
            builder.run();
        }
    }
    
}
