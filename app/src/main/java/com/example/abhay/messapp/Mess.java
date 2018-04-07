package com.example.abhay.messapp;

public class Mess {
    private int messId;
    private String messName;
    private String messHead;
    private int dailyCost;
    private int capacity;
    private int nonvegCost;

    public Mess() {
    }

    public Mess(int messId, String messName, String messHead, int dailyCost, int capacity, int nonvegCost) {
        this.messId = messId;
        this.messName = messName;
        this.messHead = messHead;
        this.dailyCost = dailyCost;
        this.capacity = capacity;
        this.nonvegCost = nonvegCost;
    }

    public void setMessId(int messId) {
        this.messId = messId;
    }

    public void setMessName(String messName) {
        this.messName = messName;
    }

    public void setMessHead(String messHead) {
        this.messHead = messHead;
    }

    public void setDailyCost(int dailyCost) {
        this.dailyCost = dailyCost;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setNonvegCost(int nonvegCost) {
        this.nonvegCost = nonvegCost;
    }


    public int getMessId() {
        return messId;
    }

    public String getMessName() {
        return messName;
    }

    public String getMessHead() {
        return messHead;
    }

    public int getDailyCost() {
        return dailyCost;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNonvegCost() {
        return nonvegCost;
    }
}
