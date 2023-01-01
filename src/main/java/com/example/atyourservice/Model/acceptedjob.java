package com.example.atyourservice.Model;

public class acceptedjob {
    String WorkerWhoAcceptJobUid;
    Double WorkerWhoAcceptJobLat,WorkerWhoAcceptJobLng;

    public acceptedjob(){}

    public acceptedjob(String workerWhoAcceptJobUid, Double workerWhoAcceptJobLat, Double workerWhoAcceptJobLng) {
        WorkerWhoAcceptJobUid = workerWhoAcceptJobUid;
        WorkerWhoAcceptJobLat = workerWhoAcceptJobLat;
        WorkerWhoAcceptJobLng = workerWhoAcceptJobLng;
    }

    public String getWorkerWhoAcceptJobUid() {
        return WorkerWhoAcceptJobUid;
    }

    public void setWorkerWhoAcceptJobUid(String workerWhoAcceptJobUid) {
        WorkerWhoAcceptJobUid = workerWhoAcceptJobUid;
    }

    public Double getWorkerWhoAcceptJobLat() {
        return WorkerWhoAcceptJobLat;
    }

    public void setWorkerWhoAcceptJobLat(Double workerWhoAcceptJobLat) {
        WorkerWhoAcceptJobLat = workerWhoAcceptJobLat;
    }

    public Double getWorkerWhoAcceptJobLng() {
        return WorkerWhoAcceptJobLng;
    }

    public void setWorkerWhoAcceptJobLng(Double workerWhoAcceptJobLng) {
        WorkerWhoAcceptJobLng = workerWhoAcceptJobLng;
    }
}
