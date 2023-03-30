package com.g2t3.vms.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.g2t3.vms.model.Pdf;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class PdfService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    public String savePDF(String title, MultipartFile file) throws IOException, Exception {

        // Define metadata
        DBObject metaData = new BasicDBObject();

        // Store PDF in database
        metaData.put("type", "pdf");
        metaData.put("title", title);
        ObjectId id = gridFsTemplate.store(
            file.getInputStream(), title, file.getContentType(), metaData
        );

        // TO BE DELETED
        System.out.println("file id stored: " + id.toString() + " ,  " + file.getName());
        System.out.println("input stream: " + file.getInputStream().toString());

        return "redirect:/pdfs/" + id.toString();

    }

    public void retrievePDFById(String fileId) throws IllegalStateException, IOException{

        // Query
        GridFSFile dbFile = operations.findOne(new Query(Criteria.where("_id").is(fileId)));
        String fileTitle = dbFile.getMetadata().get("title").toString();

        Pdf pdf = new Pdf();
        pdf.setTitle(fileTitle); 
        pdf.setStream(operations.getResource(dbFile).getInputStream());

        // Convert binary data to pdf
        OutputStream outputStream = new FileOutputStream(System.getProperty("user.home") + "/Downloads/" + fileTitle + ".pdf");
        operations.getResource(dbFile).getInputStream().transferTo(outputStream); 

        // Close output stream
        outputStream.close();
        
    }

    public void retrievePDFByFileName(String fileName) throws IllegalStateException, IOException{

        // Query
        GridFSFile dbFile = operations.findOne(new Query(Criteria.where("filename").is(fileName)));
        String fileTitle = dbFile.getMetadata().get("title").toString();

        Pdf pdf = new Pdf();
        pdf.setTitle(fileTitle); 
        pdf.setStream(operations.getResource(dbFile).getInputStream());

        // Convert binary data to pdf
        OutputStream outputStream = new FileOutputStream(System.getProperty("user.home") + "/Downloads/" + fileTitle + ".pdf");
        operations.getResource(dbFile).getInputStream().transferTo(outputStream); 

        // Close output stream
        outputStream.close();
        
    }

    public Pdf streamPDF(String id) throws IllegalStateException, IOException {

        // Query
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

        Pdf pdf = new Pdf();
        pdf.setTitle(file.getMetadata().get("title").toString()); 
        pdf.setStream(operations.getResource(file).getInputStream());
        
        return pdf; 

    }

}
