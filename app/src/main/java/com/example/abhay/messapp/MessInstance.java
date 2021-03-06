package com.example.abhay.messapp;

import android.database.Cursor;
import android.view.View;

import java.time.LocalDate;
import java.util.Date;

public class MessInstance {
    private int messInstanceId;
    private Mess mess;
    private int breakfastTotal;
    private int lunchTotal;
    private int dinnerTotal;
    private LocalDate date = LocalDate.now();

    public MessInstance() {
    }

    public MessInstance(Mess mess, int breakfastTotal, int lunchTotal, int dinnerTotal) {
        this.mess = mess;
        this.breakfastTotal = breakfastTotal;
        this.lunchTotal = lunchTotal;
        this.dinnerTotal = dinnerTotal;
    }

/*    public int[] getNumbers(Date date) {

        View v = inflater.inflate(R.layout.fragment_two, container, false);
        String q = "SELECT BREAKFASTTOTAL, LUNCHTOTAL, DINNERTOTAL FROM MESSES WHERE MESSNAME" + "=\"" + mess + "\";";
        Cursor c = messDBHandler.getResult(q);
    }*/

    public int getMessInstanceId() {
        return messInstanceId;
    }

    public int getMess() {
        return mess.getMessId();
    }

    public int getBreakfastTotal() {
        return breakfastTotal;
    }

    public int getLunchTotal() {
        return lunchTotal;
    }

    public int getDinnerTotal() {
        return dinnerTotal;
    }

    public String getDate() {
        return date.toString();
    }


    public void setMessInstanceId(int messInstanceId) {
        this.messInstanceId = messInstanceId;
    }

    public void setMess(Mess mess) {
        this.mess = mess;
    }

    public void setBreakfastTotal(int breakfastTotal) {
        this.breakfastTotal = breakfastTotal;
    }

    public void setLunchTotal(int lunchTotal) {
        this.lunchTotal = lunchTotal;
    }

    public void setDinnerTotal(int dinnerTotal) {
        this.dinnerTotal = dinnerTotal;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
