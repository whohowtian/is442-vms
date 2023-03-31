package com.g2t3.vms.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
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
        String[] elements = title.split("_");
        metaData.put("type", "pdf");
        metaData.put("vendorUEN", elements[0]);
        metaData.put("formName", elements[1]);
        gridFsTemplate.store(
            file.getInputStream(), title, file.getContentType(), metaData
        );

    }

    public void retrievePDFByFileName(String fileName) throws IllegalStateException, IOException {

        // Query
        GridFSFile dbFile = operations.findOne(new Query(Criteria.where("filename").is(fileName)));

        // Convert binary data to pdf
        OutputStream outputStream = new FileOutputStream(System.getProperty("user.home") + "/Downloads/" + fileName + ".pdf");
        operations.getResource(dbFile).getInputStream().transferTo(outputStream); 

        // Close output stream
        outputStream.close();
        
    }

    public void retrievePDFByVendor(String vendorUEN) throws IllegalStateException, IOException {

        // Query
        GridFSFindIterable dbFile = operations.find(new Query(Criteria.where("metadata.vendorUEN").is(vendorUEN)));

        // Convert binary data to pdf
        ZipOutputStream outputStream = new ZipOutputStream(
            new FileOutputStream(System.getProperty("user.home") + "/Downloads/" + vendorUEN + ".zip")
        );
        for (GridFSFile gridFSFile : dbFile) {
            String formName = gridFSFile.getMetadata().get("formName").toString();
            ZipEntry entry = new ZipEntry(vendorUEN + "_" + formName + ".pdf");
            outputStream.putNextEntry(entry);
            operations.getResource(gridFSFile).getInputStream().transferTo(outputStream);
        }

        // Close output stream
        outputStream.close();
        
    }

}
