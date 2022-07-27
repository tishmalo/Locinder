package com.example.locinder.Model;

public class ShereheModel {

    private String Attractions, Contact, How, Park;

    public ShereheModel() {
    }

    public ShereheModel(String attractions, String contact, String how, String park) {
        Attractions = attractions;
        Contact = contact;
        How = how;
        Park = park;
    }

    public String getAttractions() {
        return Attractions;
    }

    public void setAttractions(String attractions) {
        Attractions = attractions;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getHow() {
        return How;
    }

    public void setHow(String how) {
        How = how;
    }

    public String getPark() {
        return Park;
    }

    public void setPark(String park) {
        Park = park;
    }
}
