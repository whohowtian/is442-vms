package com.g2t3.vms.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.exception.ResourceNotFoundException;
import com.g2t3.vms.model.FormTemplate;
import com.g2t3.vms.repository.FormTemplateRepo;

@Service
public class FormTemplateService {

    @Autowired
    private FormTemplateRepo formTemplateRepo;

    public ArrayList<FormTemplate> getAllFormTemplates() throws ResourceNotFoundException, Exception {
        ArrayList<FormTemplate> formTemplates = new ArrayList<>();

        for (FormTemplate form : formTemplateRepo.findAll()) {
            formTemplates.add(form);
        }

        if (formTemplates.isEmpty()) {
            throw new ResourceNotFoundException("No form templates have been created.");
        }

        return formTemplates;
    }

    public FormTemplate getFormTemplateByFTNo(String formTemplateNo) throws ResourceNotFoundException, Exception {
        
        FormTemplate getForm = formTemplateRepo.getFormTemplateByNo(formTemplateNo);
        if (getForm == null) {
            throw new ResourceNotFoundException("Form " + formTemplateNo +  " does not exist.");
        }
        return getForm;
    }

    public void updateFormTemplate(FormTemplate formTemplate) throws ResourceNotFoundException, DataIntegrityViolationException, Exception {

            String formNo = formTemplate.getFormNo();
            FormTemplate prevFT = getFormTemplateByFTNo(formNo); 
            prevFT.setFormName(formTemplate.getFormName());
            prevFT.setRevNo(prevFT.getRevNo() + 1);
            prevFT.setFormSections(formTemplate.getFormSections());

            prevFT.setLastEdited(formTemplate.getLastEdited());
            prevFT.setEditedBy(formTemplate.getEditedBy());


            formTemplateRepo.save(prevFT); 

    }

    public void createFormTemplate(FormTemplate formTemplate) throws ResourceAlreadyExistException, DataIntegrityViolationException, Exception {
        String formNo = formTemplate.getFormNo();
        FormTemplate getForm = formTemplateRepo.getFormTemplateByNo(formNo);

        if (getForm != null) {
            throw new ResourceAlreadyExistException("Form Template " + formNo + " already exist.");
        }
        
        formTemplateRepo.save(formTemplate); 


    }

    public void deleteFormTemplate(String FTID) throws ResourceNotFoundException, DataIntegrityViolationException, Exception {

        getFormTemplateByFTNo(FTID);
        formTemplateRepo.deleteFormTemplateByNo(FTID);

    }
    
}
