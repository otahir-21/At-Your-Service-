package com.example.atyourservice.Model;

public class checkWorkerModel {
    String WorkerType,contactNo,email,type,username;

    public checkWorkerModel(){}

    public checkWorkerModel(String workerType, String contactNo, String email, String type, String username) {
        WorkerType = workerType;
        this.contactNo = contactNo;
        this.email = email;
        this.type = type;
        this.username = username;
    }

    public String getWorkerType() {
        return WorkerType;
    }

    public void setWorkerType(String workerType) {
        WorkerType = workerType;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
