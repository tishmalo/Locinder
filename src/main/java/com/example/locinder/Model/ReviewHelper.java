package com.example.locinder.Model;

public class ReviewHelper {

    private String CURRENTUSERID,RATING,REVIEW,LOCATION;

    public ReviewHelper() {
    }

    public ReviewHelper(String CURRENTUSERID, String RATING, String REVIEW, String LOCATION) {
        this.CURRENTUSERID = CURRENTUSERID;
        this.RATING = RATING;
        this.REVIEW = REVIEW;
        this.LOCATION = LOCATION;
    }

    public String getCURRENTUSERID() {
        return CURRENTUSERID;
    }

    public void setCURRENTUSERID(String CURRENTUSERID) {
        this.CURRENTUSERID = CURRENTUSERID;
    }

    public String getRATING() {
        return RATING;
    }

    public void setRATING(String RATING) {
        this.RATING = RATING;
    }

    public String getREVIEW() {
        return REVIEW;
    }

    public void setREVIEW(String REVIEW) {
        this.REVIEW = REVIEW;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }
}
