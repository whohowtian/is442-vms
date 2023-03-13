package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.time.*;


@Document("Form")
@Getter
@Setter
public class Form {
    @Id
    private String id;
    private String assigned_vendor_uid;
    private String status;
    private FormTemplate formContent;

    // should this be in a seperate class?
    private String approver;
    private LocalDateTime approvalDateTime;

    public Form (String assigned_vendor_uid, FormTemplate formContent) {
        this.assigned_vendor_uid = assigned_vendor_uid;
        this.status = "new";
        this.formContent = formContent;
        this.approver = "";
        this.approvalDateTime = null;

    }

    @Override
    public String toString() {
        return String.format("{ \"assigned_vendor_uid\": '%s', \"status\": '%s', \"approver\": '%s', \"approvalDateTime\": '%s', \"formContent\": %s }", assigned_vendor_uid, status, approver, approvalDateTime, formContent.toString());
    }
}