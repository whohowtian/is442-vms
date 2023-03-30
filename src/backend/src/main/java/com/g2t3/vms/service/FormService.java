package com.g2t3.vms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;


import com.g2t3.vms.exception.ResourceNotFoundException;
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
    public ArrayList<Form> getAllForms() throws ResourceNotFoundException, Exception {
        ArrayList<Form> forms = new ArrayList<>();

        for (Form form : formRepo.findAll()) {
            forms.add(form);
        }

        if (forms.isEmpty()) {
            throw new ResourceNotFoundException("No forms/workflows have been created.");
        }

        return forms;
    }

    public Form getFormByFID(String formID) throws ResourceNotFoundException, Exception {
        
        Form getForm = formRepo.getFormByID(formID);
        if (getForm == null) {
            throw new ResourceNotFoundException("Form " + formID +  " does not exist.");
        }
        return getForm;
    }

    // create new form/workflow
    public void createForm(Map<String, String> newFormInfo) throws ResourceNotFoundException, DataIntegrityViolationException, Exception {

        String formNo = newFormInfo.get("formNo");  
        String assigned_vendor_uid = newFormInfo.get("assigned_vendor_email");

        System.out.println(newFormInfo.toString());

        FormTemplate getFormTempt = formTemplateRepo.getFormTemplateByNo(formNo);

        System.out.println(getFormTempt.toString());

        Form newForm = new Form(assigned_vendor_uid, getFormTempt);

        if (getFormTempt == null) {
            throw new ResourceNotFoundException("Form Template " + formNo + "does not exist.");
        }

        // TODO: Check vendor UID exists?

        formRepo.save(newForm); 
    }

    public void editForm(Form form) throws ResourceNotFoundException, DataIntegrityViolationException, Exception {
        String formID = form.getId();
        Form currFormObjDB = formRepo.getFormByID(formID);

        if (currFormObjDB == null) {
            throw new ResourceNotFoundException("Form " + formID + "does not exist.");
        }

        Map<String, FormSection> newInput = form.getFormContent().getFormSections();

        for (Map.Entry<String, FormSection> sectionEntry : newInput.entrySet()) {
            String currSectID = sectionEntry.getKey();
            FormSection currFormSectDB = currFormObjDB.getFormContent().getFormSections().get(currSectID);
            boolean forAdminOnly = currFormSectDB.isAdminUseOnly();

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

    public void changeStatus(Map<String, String> postQuery, String action) throws ResourceNotFoundException, Exception {
        String formID = postQuery.get("formID");
        Form currFormObjDB = formRepo.getFormByID(formID);

        if (currFormObjDB == null) {
            throw new ResourceNotFoundException("Form " + formID + "does not exist.");
        }

        // TODO: restrict certain status change to admin/approvers
        switch(action) {
            case "approve":
                currFormObjDB.setStatus(FormStatus.APPROVED);
                // TODO: add approver name and datatime into Form
                break;
            case "submit":
                currFormObjDB.setStatus(FormStatus.PENDING_REVIEW);
                break;
            case "adminreviewed":
                currFormObjDB.setStatus(FormStatus.PENDING_APPROVAL);
                break;
            case "archive":
                // currFormObjDB.setStatus(FormStatus.ARCHIVED);
                currFormObjDB.isArchived(true);
                // currFormObjDB.setArchivedBy();
                break;
            case "adminreject":
                currFormObjDB.setStatus(FormStatus.ADMIN_REJECTED);
                break;
            case "approverreject":
                currFormObjDB.setStatus(FormStatus.APPROVER_REJECTED);
                break;
            case "created":
                currFormObjDB.setStatus(FormStatus.PENDING_VENDOR);
                break;
        }

        formRepo.save(currFormObjDB);

    } 

}
