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

    private String archivedBy;
    private boolean isArchived;

    private String adminApproverComments;

    private LocalDateTime formEffDate;
    private LocalDateTime deadline;

    public Form (String assigned_vendor_email, FormTemplate formContent) {
        this(formContent);
        this.status = FormStatus.PENDING_VENDOR;
        this.assigned_vendor_email = assigned_vendor_email;

    }

    public Form (FormTemplate formContent, boolean startFromAdmin) {
        this(formContent);
        if (startFromAdmin) {
            this.status = FormStatus.PENDING_ADMIN;
        } else {
            this.status = FormStatus.PENDING_VENDOR;
        }
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
        this.adminApproverComments = "";

    }

    public void updateStatusChangeDateTime() {
        LocalDateTime dateTimeNow = LocalDateTime.now();

        this.lastStatusChangeDate = dateTimeNow;
    }

    public void archiveForm(String user) {
        this.isArchived = true;
        this.archivedBy = user;
    }

    public void changeStatusApproved(String approver, String comments) {
        this.approver = approver;
        this.adminApproverComments = comments;
        this.status = FormStatus.APPROVED;
        this.adminApproverComments = "";
    }

    public void changeStatusSubmitted() {
        this.status = FormStatus.PENDING_ADMIN;
    }

    public void changeStatusAdminReviewed(String reviewer, String comments) {
        this.reviewedBy = reviewer;
        this.adminApproverComments = comments;
        this.status = FormStatus.PENDING_APPROVAL;
        this.adminApproverComments = "";
    }

    public void changeStatusAdminRejected(String reviewer, String comments) {
        this.reviewedBy = reviewer;
        this.adminApproverComments = comments;
        this.status = FormStatus.ADMIN_REJECTED;
    }

    public void changeStatusApproverRejected(String approver, String comments) {
        this.approver = approver;
        this.adminApproverComments = comments;
        this.status = FormStatus.APPROVER_REJECTED;
    }

    // private void doStartFromAdmin(boolean startFromAdmin) {
    //     if (startFromAdmin) {
    //         this.status = FormStatus.PENDING_ADMIN;

    //     } else {
    //         this.status = FormStatus.PENDING_VENDOR;
    //     }
    // }
}