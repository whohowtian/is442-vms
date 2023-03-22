package com.g2t3.vms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.g2t3.vms.exception.FormNotFoundException;
import com.g2t3.vms.model.FormTemplate;
import com.g2t3.vms.model.Form;
import com.g2t3.vms.repository.FormTemplateRepo;
import com.g2t3.vms.repository.FormRepo;

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
}
