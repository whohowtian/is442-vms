package com.g2t3.vms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.g2t3.vms.enums.FormStatus;
import com.g2t3.vms.exception.ResourceNotFoundException;
import com.g2t3.vms.model.Form;
import com.g2t3.vms.model.FormSection;
import com.g2t3.vms.model.FormTemplate;
import com.g2t3.vms.model.Question;
import com.g2t3.vms.repository.FormRepo;
import com.g2t3.vms.repository.FormTemplateRepo;


@Service
public class FormService {

    @Autowired
    private FormRepo formRepo;

    @Autowired
    private FormTemplateRepo formTemplateRepo;

    @Autowired
    private UserService userService;


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
        
        String assigned_vendor_email = newFormInfo.get("assigned_vendor_email");

        System.out.println(newFormInfo.toString());

        FormTemplate getFormTempt = formTemplateRepo.getFormTemplateByNo(formNo);
        if (getFormTempt == null) {
            throw new ResourceNotFoundException("Form Template " + formNo + "does not exist.");
        }

        boolean startFromAdmin = (assigned_vendor_email == null) ? true : false;

        Form newForm;

        if (startFromAdmin) {
            newForm = new Form(getFormTempt, startFromAdmin);
        } else {
            newForm = new Form(assigned_vendor_email, getFormTempt);
        }

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
            // if (forAdminOnly) {
            //     continue;
            // }

            HashMap<String, Question> currSectObj = sectionEntry.getValue().getQuestions();

            for (Map.Entry<String, Question> qnEntry : currSectObj.entrySet()) {
                String currQnID = qnEntry.getKey();
                Question currQnObj = qnEntry.getValue();

                currFormObjDB.getFormContent().getFormSections().get(currSectID).getQuestions().get(currQnID).setAnswer(currQnObj.getAnswer());
            }
        }

        formRepo.save(currFormObjDB);
    }

    public void changeStatus(Form form, String action) throws ResourceNotFoundException, Exception {
        String formID = form.getId();
        Form currFormObjDB = formRepo.getFormByID(formID);
        currFormObjDB.updateStatusChangeDateTime();

        if (currFormObjDB == null) {
            throw new ResourceNotFoundException("Form " + formID + "does not exist.");
        }

        switch(action) {
            case "approve":
                currFormObjDB.setApprover(form.getApprover());
                currFormObjDB.changeStatusApproved();
                break;
            case "submit":
                currFormObjDB.changeStatusSubmitted();
                break;
            case "adminreviewed":
                currFormObjDB.setReviewedBy(form.getReviewedBy());
                currFormObjDB.changeStatusAdminReviewed();
                break;
            case "adminreject":
                currFormObjDB.changeStatusAdminRejected();
                break;
            case "approverreject":
                currFormObjDB.changeStatusApproverRejected();
                break;
        }

        formRepo.save(currFormObjDB);

    } 

    public void archiveForm(Map<String, String> postQuery) throws ResourceNotFoundException, Exception {

        // check
        userService.getUserById(postQuery.get("archivedBy"));

        String formID = postQuery.get("formID");
        Form currFormObjDB = formRepo.getFormByID(formID);
        currFormObjDB.updateStatusChangeDateTime();
        currFormObjDB.archiveForm(postQuery.get("archivedBy"));

        formRepo.save(currFormObjDB);

    } 

    public ArrayList<Form> getFormByStatus(String statusID) throws ResourceNotFoundException, Exception {
        ArrayList<Form> forms = new ArrayList<>();

        for (Form form : formRepo.getFormByStatus(statusID)) {
            forms.add(form);
        }

        if (forms.isEmpty()) {
            throw new ResourceNotFoundException("No forms/workflows have been created.");
        }

        return forms;
    }

    public ArrayList<Form> getFormByVendor(String vendorEmail) throws ResourceNotFoundException, Exception {
        ArrayList<Form> forms = formRepo.getFormByVendor(vendorEmail);

        if (forms.isEmpty()) {
            throw new ResourceNotFoundException("No forms/workflows have been created.");
        }

        return forms;
    }

}
