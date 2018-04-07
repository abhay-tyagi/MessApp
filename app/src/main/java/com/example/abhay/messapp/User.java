package com.example.abhay.messapp;

public class User {
    private String username;
    private String password;
    private int year;
    private Mess mess;
    private String course;
    private String branch;

    public User() {
    }

    public User(String username, String password, int year, Mess mess, String course, String branch) {
        this.username = username;
        this.password = password;
        this.year = year;
        this.mess = mess;
        this.course = course;
        this.branch = branch;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getYear() {
        return year;
    }

    public int getMess() {
        return mess.getMessId();
    }

    public String getCourse() {
        return course;
    }

    public String getBranch() {
        return branch;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMess(Mess mess) {
        this.mess = mess;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
