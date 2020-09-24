package com.uc.saa1_0706011910028.model;

public class Lecturer {
    private String id, name, gender, expertise;

    public Lecturer(){}

    public Lecturer(String id, String name, String gender, String expertise) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.expertise = expertise;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }
}
