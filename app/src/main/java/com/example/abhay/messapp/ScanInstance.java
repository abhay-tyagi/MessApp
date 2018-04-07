package com.example.abhay.messapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalTime;

public class ScanInstance {
//    private LocalTime scanTime = java.time.LocalTime.now();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    Date now = new Date();
    String strDate = sdf.format(now);
    String[] dateFull = strDate.split("/");

    SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
    String strTime = stf.format(now);
    String[] timeFull = strTime.split(":");

    private int scanInstanceId;
    private Mess mess;
    private int scanTime = Integer.parseInt(timeFull[0]);
    private Date scanDate = new Date(Integer.parseInt(dateFull[0]), Integer.parseInt(dateFull[1]), Integer.parseInt(dateFull[0]));
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

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    public void setUserScanner(String userScanner) {
        this.userScanner = userScanner;
    }
}
