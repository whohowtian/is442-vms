package com.g2t3.vms.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.g2t3.vms.model.FormTemplate;
import com.g2t3.vms.repository.FormRepo;

@RestController
@RequestMapping(path="/v1/api", produces="application/json")
public class FormTemplateController {
    @Autowired
    private FormRepo formRepo;

    // Returns all Forms
    @GetMapping("/form/view")
    @ResponseBody
    public ArrayList<FormTemplate> get_all_ft() {
        ArrayList<FormTemplate> result = new ArrayList<>();

        for (FormTemplate form : formRepo.findAll()) {
            result.add(form);
        }

        return result;
    } 

    // Returns form by FormNo
    // returns 404 not found if invalid formNo
    @GetMapping("/form/view/{no}")
    @ResponseBody
    public FormTemplate get_ft_by_id(@PathVariable String no) {
        FormTemplate getForm = formRepo.getFormTemplateByNo(no);
        if (getForm == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Form Not Found");
        }
        return getForm;
    }

    // Edit existing form by FormNo
    // returns form json obj if success, otherwise 400 or 500
    @PostMapping("/form/edit")
    public FormTemplate edit_form_template(@RequestBody FormTemplate updatedFT) {
        try {
            FormTemplate existingForm = formRepo.getFormTemplateByNo(updatedFT.getFormNo());
            existingForm.setFormEffDate(updatedFT.getFormEffDate());
            existingForm.setFormName(updatedFT.getFormName());
            existingForm.setArchived(updatedFT.isArchived());
            existingForm.setRevNo(existingForm.getRevNo() + 1);
            existingForm.setFormSections(updatedFT.getFormSections());

            FormTemplate doUpdate = formRepo.save(existingForm); 
            return doUpdate;
        }
        catch (org.springframework.dao.DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request", e);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Service Error", e);
        }
        
    }

    // Creates new Form
    // returns 400 bad request if validation fails
    @PostMapping("/form/create")
    public FormTemplate new_form_template(@RequestBody FormTemplate newFT) {
        try {
            FormTemplate writeResp = formRepo.save(newFT);
            return writeResp;
        }
        catch (org.springframework.dao.DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request", e);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Service Error", e);
        }
        
    }
}
