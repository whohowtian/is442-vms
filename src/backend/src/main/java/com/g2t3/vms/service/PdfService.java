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
        // InputStream inputStream = new FileInputStream(System.getProperty("user.home") + "/Downloads/" + title + ".pdf");
        ObjectId id = gridFsTemplate.store(
            file.getInputStream(), file.getName(), file.getContentType(), metaData
        );
        System.out.println("file id stored: " + id.toString() + " ,  " + file.getName());
        System.out.println("input stream: " + file.getInputStream().toString());

        return "redirect:/pdfs/" + id.toString();

    }

    public void retrievePDF(String fileId) throws IllegalStateException, IOException{

        // GridFSFile file =  pdfRepo.findbyId(fileId);

        GridFSFile dbFile = operations.findOne(new Query(Criteria.where("_id").is(fileId)));
        OutputStream outputStream = new FileOutputStream(System.getProperty("user.home") + "/Downloads/" + "testing2" + ".pdf");
        operations.getResource(dbFile).getInputStream().transferTo(outputStream);;
        
        // InputStream inputStream = fsResource.getInputStream();
        // GridFsResource fsResource = new GridFsResource(dbFile, getGridFs().open)

        Pdf pdf = new Pdf();
        pdf.setTitle(dbFile.getMetadata().get("title").toString()); 
        pdf.setStream(operations.getResource(dbFile).getInputStream());

        // return fsResource;


    }

    public Pdf streamPDF(String id) throws IllegalStateException, IOException {

        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        Pdf pdf = new Pdf();
        pdf.setTitle(file.getMetadata().get("title").toString()); 
        pdf.setStream(operations.getResource(file).getInputStream());
        
        return pdf; 

    }

}
