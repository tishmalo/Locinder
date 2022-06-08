package com.example.locinder.Model;

public class OrganizeModel {

   private String LOCATIONS, DESCRIPTION, PHOTO;

    public OrganizeModel() {
    }


    public OrganizeModel(String LOCATIONS, String DESCRIPTION, String PHOTO) {
        this.LOCATIONS = LOCATIONS;
        this.DESCRIPTION = DESCRIPTION;
        this.PHOTO = PHOTO;
    }

    public String getLOCATIONS() {
        return LOCATIONS;
    }

    public void setLOCATIONS(String LOCATIONS) {
        this.LOCATIONS = LOCATIONS;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(String PHOTO) {
        this.PHOTO = PHOTO;
    }
}
