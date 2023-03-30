package com.g2t3.vms.enums;

public enum FormStatus {
    PENDING_VENDOR, //vendor todo
    PENDING_REVIEW, //admin todo
    PENDING_APPROVAL, //approver todo
    APPROVED, //completed
    ADMIN_REJECTED, //sent back to vendor todo
    APPROVER_REJECTED, //sent back to vendor todo
}
