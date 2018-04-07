package com.example.abhay.messapp;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScanInstance {
//    private LocalTime scanTime = java.time.LocalTime.now();
    private int scanInstanceId;
    private Mess mess;
    private int scanTime = java.time.LocalTime.now().getHour();
    private LocalDate scanDate = LocalDate.now();
    private String userScanner;

    public ScanInstance() {
    }

    public ScanInstance(Mess mess, String userScanner) {
        this.mess = mess;
        this.userScanner = userScanner;
    }

    public int getScanInstanceId() {
        return scanInstanceId;
    }

    public int getMess() {
        return mess.getMessId();
    }

    public int getScanTime() {
        return scanTime;
    }

    public String getScanDate() {
        return scanDate.toString();
    }

    public String getUserScanner() {
        return userScanner;
    }


    public void setScanInstanceId(int scanInstanceId) {
        this.scanInstanceId = scanInstanceId;
    }

    public void setMess(Mess mess) {
        this.mess = mess;
    }

    public void setScanTime(int scanTime) {
        this.scanTime = scanTime;
    }

    public void setScanDate(LocalDate scanDate) {
        this.scanDate = scanDate;
    }

    public void setUserScanner(String userScanner) {
        this.userScanner = userScanner;
    }
}
