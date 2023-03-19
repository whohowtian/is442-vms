package com.g2t3.vms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Email {

    private String toEmail;
    private String body;
    private String subject;
    private String attachment;

    public Email(String toEmail, String body, String subject, String attachment) {
        this.toEmail = toEmail;
        this.body = body;
        this.subject = subject;
        this.attachment = attachment;
    }
}
