package com.example.atyourservice.SignUp;

public class Customer {
    private String username,email,contactNo,password,type;

    public Customer(String username, String email, String cn, String password,String type){

    }
    public Customer(String uname,String UserEmail,String UserContact,String type){
        this.username=uname;
        this.email=UserEmail;
        this.contactNo=UserContact;
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
