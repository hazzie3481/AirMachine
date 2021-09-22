package com.example.hireme.model;

public class User {
    private int id;
    private String fname;
    private String email;
    private String mobileNo;
    private String lname;
    private String password;
    public User(){}
    public User(String username, String email) {
        this.fname = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return fname;
    }
    public void setFirstName(String name) {
        this.fname = fname;
    }
    public String getLastName() {
        return lname;
    }
    public void setLasstName(String name) {
        this.lname = lname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

}
