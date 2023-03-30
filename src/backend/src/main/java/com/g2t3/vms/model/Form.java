package com.g2t3.vms.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.PersistenceConstructor;


import org.bson.types.ObjectId;

import com.g2t3.vms.enums.FormStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Document("Form")
@Getter
@Setter
@NoArgsConstructor
public class Form {
    @Id
    private String id;
    private String assigned_vendor_email;

    private FormStatus status;
    private FormTemplate formContent;

    // should this be in a seperate class?
    private String approver;
    private LocalDateTime approvalDateTime;

    private String archivedBy;
    private boolean isArchived;

    public Form (String assigned_vendor_email, FormTemplate formContent) {
        this(formContent);
        this.assigned_vendor_email = assigned_vendor_email;

    }

    public Form (FormTemplate formContent) {
        this.status = FormStatus.PENDING_VENDOR;
        this.formContent = formContent;
        this.approver = "";
        this.approvalDateTime = null;
        this.archivedBy = null;

    }

    // @PersistenceConstructor
    // public Form (String assigned_vendor_email, FormTemplate formContent, String approver, LocalDateTime approvalDateTime, String archivedBy, boolean isArchived, FormStatus status) {
    //     this.status = status;
    //     this.formContent = formContent;
    //     this.approver = approver;
    //     this.approvalDateTime = approvalDateTime;
    //     this.archivedBy = archivedBy;
    //     this.isArchived = isArchived;
    //     this.assigned_vendor_email = assigned_vendor_email;
    // }

    // @Override
    // public String toString() {
    //     return String.format("{ \"assigned_vendor_uid\": '%s', \"status\": '%s', \"approver\": '%s', \"approvalDateTime\": '%s', \"formContent\": %s }", assigned_vendor_uid, status, approver, approvalDateTime, formContent.toString());
    // }

    // public String getID() {
    //     return id;
    // } 
}