package com.g2t3.vms.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.g2t3.vms.model.Form;
import com.g2t3.vms.repository.FormRepo;
import com.g2t3.vms.model.FormTemplate;
import com.g2t3.vms.repository.FormTemplateRepo;


@RestController
@RequestMapping(path="/v1/api/form", produces="application/json")
public class FormContoller {
    @Autowired
    private FormRepo formRepo;
    @Autowired
    private FormTemplateRepo formTemplateRepo;

    // Returns all Forms
    @GetMapping("/view")
    @ResponseBody
    public ArrayList<Form> get_all_ft() {
        ArrayList<Form> result = new ArrayList<>();

        for (Form form : formRepo.findAll()) {
            result.add(form);
        }

        return result;
    } 

    // Creates new Form
    // returns 400 bad request if validation fails
    @PostMapping("/create")
    public Form new_form_template(@RequestBody Map<String,String> newFormJSON) {
        try {
            String assigned_vendor_uid = newFormJSON.get("assigned_vendor_uid");
            String formNo = newFormJSON.get("form_no");

            FormTemplate formTemplate = formTemplateRepo.getFormTemplateByNo(formNo);
            
            Form newForm = new Form(assigned_vendor_uid, formTemplate);

            Form writeResp = formRepo.save(newForm);
            return writeResp;
            // return writeResp.toString();
        }
        catch (org.springframework.dao.DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request", e);
        }
        // catch (Exception e) {
        //     throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Service Error", e);
        // }
        
    }
}
