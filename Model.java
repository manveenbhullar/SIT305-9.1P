package com.example.myapplication;

public class Model {

    String title;
    String phone;
    String desc;
    String date;
    String location;
    String id;

    public Model(String id, String name, String phone, String description, String date, String location) {
        this.title = name;
        this.phone = phone;
        this.desc = description;
        this.date = date;
        this.location = location;
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
