package com.example.atyourservice.Admin.Fragments;

public class SenderModel {

    String username,email,contactNo;

    public SenderModel(){}
    public SenderModel(String username, String email, String contactNo) {
        this.username = username;
        this.email = email;
        this.contactNo = contactNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
