package com.g2t3.vms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;


import com.g2t3.vms.exception.FormNotFoundException;
import com.g2t3.vms.model.FormTemplate;
import com.g2t3.vms.model.FormSection;
import com.g2t3.vms.model.Form;
import com.g2t3.vms.repository.FormTemplateRepo;
import com.g2t3.vms.repository.FormRepo;
import com.g2t3.vms.service.FormTemplateService;
import com.g2t3.vms.enums.FormStatus;


@Service
public class FormService {
    @Autowired
    private FormRepo formRepo;
    @Autowired
    private FormTemplateRepo formTemplateRepo;


    // Get all created forms/workflows
    public ArrayList<Form> getAllForms() throws FormNotFoundException, Exception {
        ArrayList<Form> forms = new ArrayList<>();

        for (Form form : formRepo.findAll()) {
            forms.add(form);
        }

        if (forms.isEmpty()) {
            throw new FormNotFoundException("No forms/workflows have been created.");
        }

        return forms;
    }

    public Form getFormByFID(ObjectId formID) throws FormNotFoundException, Exception {
        
        Form getForm = formRepo.getFormByID(formID);
        if (getForm == null) {
            throw new FormNotFoundException("Form " + formID +  " does not exist.");
        }
        return getForm;
    }

    // create new form/workflow
    public void createForm(Map<String, String> newFormInfo) throws NullPointerException, DataIntegrityViolationException, Exception {

        String formNo = newFormInfo.get("formNo");
        String assigned_vendor_uid = newFormInfo.get("assigned_vendor_uid");

        System.out.println(newFormInfo.toString());

        FormTemplate getForm = formTemplateRepo.getFormTemplateByNo(formNo);

        System.out.println(getForm.toString());

        Form newForm = new Form(formNo, getForm);

        if (getForm == null) {
            throw new NullPointerException("Form Template " + formNo + "does not exist.");
        }

        // TODO: Check vendor UID exists?

        formRepo.save(newForm); 
    }

    // public void editForm(Form form) throws NullPointerException, DataIntegrityViolationException, Exception {
    //     // TODO: fix formid generation
    //     String formID = form.getID();
    //     System.out.println(formID);
    //     Form prevFT = formRepo.getFormByID(formID);
    //     System.out.println(prevFT);


    //     ArrayList<FormSection> newInput = form.getFormContent().getFormSections();

    //     // TODO: no admin check - assume vendor only for now
    //     Iterator<FormSection> iter = newInput.iterator();
    //     if (iter.hasNext()) {
    //         FormSection currSection = iter.next();

    //         if (currSection.getAdminUseOnly()) {
    //             iter.remove();
    //         }

    //     }
    //     prevFT.setFormSections(newInput);

    //     formRepo.save(prevFT); 
    // }
}
