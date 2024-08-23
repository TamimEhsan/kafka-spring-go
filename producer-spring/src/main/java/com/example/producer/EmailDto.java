package com.example.producer;

public class EmailDto {
    private String to;
    private String subject;

    public EmailDto() {
    }

    public EmailDto(String to, String subject) {
        this.to = to;
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "EmailDto [to=" + to + ", subject=" + subject + "]";
    }
}
