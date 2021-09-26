package com.example.fbasevideo;

public class MainModel {
    String name,availability,description,turl;

    MainModel() {

    }

    public MainModel(String name, String availability, String description, String turl) {
        this.name = name;
        this.availability = availability;
        this.description = description;
        this.turl = turl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}
