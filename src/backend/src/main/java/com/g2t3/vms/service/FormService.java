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
import com.g2t3.vms.model.Question;
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

    public Form getFormByFID(String formID) throws FormNotFoundException, Exception {
        
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

        FormTemplate getFormTempt = formTemplateRepo.getFormTemplateByNo(formNo);

        System.out.println(getFormTempt.toString());

        Form newForm = new Form(formNo, getFormTempt);

        if (getFormTempt == null) {
            throw new NullPointerException("Form Template " + formNo + "does not exist.");
        }

        // TODO: Check vendor UID exists?

        formRepo.save(newForm); 
    }

    public void editForm(Form form) throws NullPointerException, DataIntegrityViolationException, Exception {
        String formID = form.getId();
        Form currFormObjDB = formRepo.getFormByID(formID);

        if (currFormObjDB == null) {
            throw new NullPointerException("Form " + formNo + "does not exist.");
        }

        Map<String, FormSection> newInput = form.getFormContent().getFormSections();

        for (Map.Entry<String, FormSection> sectionEntry : newInput.entrySet()) {
            String currSectID = sectionEntry.getKey();
            FormSection currFormSectDB = currFormObjDB.getFormContent().getFormSections().get(currSectID);
            boolean forAdminOnly = currFormSectDB.getAdminUseOnly();

            // TODO: to allow admin to edit forAdminUseOnly sections; currently only allow editing for vendor questions
            if (forAdminOnly) {
                continue;
            }

            HashMap<String, Question> currSectObj = sectionEntry.getValue().getQuestions();

            for (Map.Entry<String, Question> qnEntry : currSectObj.entrySet()) {
                String currQnID = qnEntry.getKey();
                Question currQnObj = qnEntry.getValue();

                currFormObjDB.getFormContent().getFormSections().get(currSectID).getQuestions().get(currQnID).setAnswer(currQnObj.getAnswer());
            }
        }

        formRepo.save(currFormObjDB);
    }


}
