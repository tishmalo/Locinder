package com.example.locinder.Model;

public class UserHelperClass {

    private String DESCRIPTION,LOCATIONS,PHOTO,CURRENTUSERID,RATING,REVIEW;

    public UserHelperClass() {
    }

    public UserHelperClass(String DESCRIPTION, String LOCATIONS, String PHOTO, String CURRENTUSERID) {
        this.DESCRIPTION = DESCRIPTION;
        this.LOCATIONS = LOCATIONS;
        this.PHOTO = PHOTO;
        this.CURRENTUSERID = CURRENTUSERID;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getLOCATIONS() {
        return LOCATIONS;
    }

    public void setLOCATIONS(String LOCATIONS) {
        this.LOCATIONS = LOCATIONS;
    }

    public String getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(String PHOTO) {
        this.PHOTO = PHOTO;
    }

    public String getCURRENTUSERID() {
        return CURRENTUSERID;
    }

    public void setCURRENTUSERID(String CURRENTUSERID) {
        this.CURRENTUSERID = CURRENTUSERID;
    }
}
