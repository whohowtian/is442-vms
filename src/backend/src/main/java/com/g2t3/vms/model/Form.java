package com.g2t3.vms.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.g2t3.vms.enums.FormStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document("Form")
@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Form {
    @Id
    private String id;
    private String assigned_vendor_email;

    private FormStatus status;
    private LocalDateTime lastStatusChangeDate;
    private FormTemplate formContent;

    private String approver;
    private String reviewedBy;

    // MAYBE USER OBJECT?
    private String archivedBy;
    private boolean isArchived;

    private LocalDateTime formEffDate;
    private LocalDateTime deadline;

    public Form (String assigned_vendor_email, FormTemplate formContent, boolean startFromAdmin) {
        this(formContent);
        doStartFromAdmin(startFromAdmin);
        this.assigned_vendor_email = assigned_vendor_email;

    }

    public Form (FormTemplate formContent, boolean startFromAdmin) {
        this(formContent);
        doStartFromAdmin(startFromAdmin);
    }

    public Form (FormTemplate formContent) {
        this.formContent = formContent;
        this.approver = "";
        this.reviewedBy = "";
        this.archivedBy = null;
        LocalDateTime dateTimeNow = LocalDateTime.now();
        this.formEffDate = dateTimeNow;
        this.deadline = dateTimeNow.plusDays(formContent.getDeadlineDays());
        this.lastStatusChangeDate = dateTimeNow;
    }

    public void updateStatusChangeDateTime() {
        LocalDateTime dateTimeNow = LocalDateTime.now();

        this.lastStatusChangeDate = dateTimeNow;
    }

    public void archiveForm(String user) {
        this.isArchived = true;
        this.archivedBy = user;
    }

    public void changeStatusApproved() {
        this.status = FormStatus.APPROVED;
    }

    public void changeStatusSubmitted() {
        this.status = FormStatus.PENDING_ADMIN;
    }

    public void changeStatusAdminReviewed() {
        this.status = FormStatus.PENDING_APPROVAL;
    }

    public void changeStatusAdminRejected() {
        this.status = FormStatus.PENDING_ADMIN;
    }

    public void changeStatusApproverRejected() {
        this.status = FormStatus.APPROVER_REJECTED;
    }

    private void doStartFromAdmin(boolean startFromAdmin) {
        if (startFromAdmin) {
            this.status = FormStatus.PENDING_ADMIN;

        } else {
            this.status = FormStatus.PENDING_VENDOR;
        }
    }
}