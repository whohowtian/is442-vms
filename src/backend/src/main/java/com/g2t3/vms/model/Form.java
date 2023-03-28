package com.g2t3.vms.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import com.g2t3.vms.enums.FormStatus;

import lombok.Getter;
import lombok.Setter;


@Document("Form")
@Getter
@Setter
public class Form {
    @Id
    private String id;
    private String assigned_vendor_email;
    private FormStatus status;
    private FormTemplate formContent;

    // should this be in a seperate class?
    private String approver;
    private LocalDateTime approvalDateTime;

    public Form (String assigned_vendor_email, FormTemplate formContent) {
        this.assigned_vendor_email = assigned_vendor_email;
        this.status = FormStatus.PENDING_VENDOR;
        this.formContent = formContent;
        this.approver = "";
        this.approvalDateTime = null;

    }

    // @Override
    // public String toString() {
    //     return String.format("{ \"assigned_vendor_uid\": '%s', \"status\": '%s', \"approver\": '%s', \"approvalDateTime\": '%s', \"formContent\": %s }", assigned_vendor_uid, status, approver, approvalDateTime, formContent.toString());
    // }

    // public String getID() {
    //     return id;
    // } 
}