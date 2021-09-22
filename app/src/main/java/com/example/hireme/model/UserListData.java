package com.example.hireme.model;

public class UserListData {
    private String name;
    private String email;
    private String imgSrc;
    private boolean expanded;
    public UserListData() {
        this.email = "";
        this.imgSrc = "";
        this.name = "";
        this.expanded = false;
    }
    public UserListData(String name,String email) {
        this.email = email;
        this.imgSrc = "";
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTitle() {
        return name;
    }
    public void setTitle(String title) {
        this.name= title;
    }
    public String getImgSrc() {
        return imgSrc;
    }
    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
    public boolean getExpanded(){return this.expanded;}
    public void setExpanded(boolean isExpanded){this.expanded = isExpanded;}
}
