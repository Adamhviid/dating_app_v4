package com.example.demo.Models;

public class Profile {

    private int id;
    private String name;
    private String kodeord;
    private String gender;
    private String email;
    private String description;
    private int admin;
    private String candidatelist;

    public Profile(int id, String name, String kodeord, String gender, String email, String description, int admin, String candidatelist) {
        this.id = id;
        this.name = name;
        this.kodeord = kodeord;
        this.gender = gender;
        this.email = email;
        this.description = description;
        this.admin = admin;
        //this.picture = picture;
        this.candidatelist = candidatelist;
    }

    @Override
    public String toString() {
        return "id=" + id + ",  " + name + ", " + kodeord + ", " + gender + ", " + email + ", " + description + ", " + admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKodeord() {
        return kodeord;
    }

    public void setKodeord(String kodeord) {
        this.kodeord = kodeord;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    /*
        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
     */
    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getCandidatelist() {
        return candidatelist;
    }

    public void setCandidatelist(String candidatelist) {
        this.candidatelist = candidatelist;
    }
}


