package com.g2t3.vms.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.model.InputPdf;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class PdfService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    public void savePDF(String title, MultipartFile file) throws IOException, Exception {

        // errors
        GridFSFile dbFile = operations.findOne(new Query(Criteria.where("filename").is(title)));
        if (dbFile != null) {
            throw new ResourceAlreadyExistException("File name already exist in database.");
        }

        // Define metadata
        DBObject metaData = new BasicDBObject();

        // Store PDF in database
        metaData.put("type", "pdf");
        metaData.put("title", title);
        gridFsTemplate.store(
            file.getInputStream(), title, file.getContentType(), metaData
        );

    }

    public void retrievePDFByFileName(String fileName) throws IllegalStateException, IOException{

        // Query
        GridFSFile dbFile = operations.findOne(new Query(Criteria.where("filename").is(fileName)));
        String fileTitle = dbFile.getMetadata().get("title").toString();

        InputPdf pdf = new InputPdf();
        pdf.setTitle(fileTitle); 
        pdf.setStream(operations.getResource(dbFile).getInputStream());

        // Convert binary data to pdf
        OutputStream outputStream = new FileOutputStream(System.getProperty("user.home") + "/Downloads/" + fileTitle + ".pdf");
        operations.getResource(dbFile).getInputStream().transferTo(outputStream); 

        // Close output stream
        outputStream.close();
        
    }

    public InputPdf streamPDF(String id) throws IllegalStateException, IOException {

        // Query
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

        InputPdf pdf = new InputPdf();
        pdf.setTitle(file.getMetadata().get("title").toString()); 
        pdf.setStream(operations.getResource(file).getInputStream());
        
        return pdf; 

    }

}
