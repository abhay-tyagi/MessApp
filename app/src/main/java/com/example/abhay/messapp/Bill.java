package com.example.abhay.messapp;

public class Bill {
    private int billId;
    private User billUser;
    private int mainCost;
    private int extrasCost;
    private int billYear;
    private int billMonth;
    private int billDay;

    public Bill() {
    }

    public Bill(int billId, User billUser, int billMonth, int billDay, int billYear, int mainCost, int extrasCost) {
        this.billId = billId;
        this.billUser = billUser;
        this.billMonth = billMonth;
        this.billDay = billDay;
        this.billYear = billYear;
        this.mainCost = mainCost;
        this.extrasCost = extrasCost;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setBillUser(User billUser) {
        this.billUser = billUser;
    }

    public void setBillMonth(int billMonth) {
        this.billMonth = billMonth;
    }

    public void setBillDay(int billDay) {
        this.billDay = billDay;
    }

    public void setBillYear(int billYear) {
        this.billYear = billYear;
    }

    public void setMainCost(int mainCost) {
        this.mainCost = mainCost;
    }

    public void setExtrasCost(int extrasCost) {
        this.extrasCost = extrasCost;
    }

    public int getBillId() {
        return billId;
    }

    public String getBillUser() {
        return billUser.getUsername();
    }

    public int getBillMonth() {
        return billMonth;
    }

    public int getBillDay() {
        return billDay;
    }

    public int getBillYear() {
        return billYear;
    }

    public int getMainCost() {
        return mainCost;
    }

    public int getExtrasCost() {
        return extrasCost;
    }
}
