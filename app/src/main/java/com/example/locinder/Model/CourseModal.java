package com.example.locinder.Model;

import io.realm.RealmObject;

public class CourseModal {

    private String DESCRIPTION,LOCATIONS,PHOTO,CURRENTUSERID,RATING,REVIEW,COORDINATES,Lat,Long,distance;


    public CourseModal() {
    }

    public CourseModal(String DESCRIPTION, String LOCATIONS, String PHOTO, String CURRENTUSERID, String RATING, String REVIEW, String COORDINATES, String lat, String aLong, String distance) {
        this.DESCRIPTION = DESCRIPTION;
        this.LOCATIONS = LOCATIONS;
        this.PHOTO = PHOTO;
        this.CURRENTUSERID = CURRENTUSERID;
        this.RATING = RATING;
        this.REVIEW = REVIEW;
        this.COORDINATES = COORDINATES;
        Lat = lat;
        Long = aLong;
        this.distance = distance;
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

    public String getCOORDINATES() {
        return COORDINATES;
    }

    public void setCOORDINATES(String COORDINATES) {
        this.COORDINATES = COORDINATES;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLong() {
        return Long;
    }

    public void setLong(String aLong) {
        Long = aLong;
    }

    public String getdistance() {
        return distance;
    }

    public void setdistance(String distance) {
        this.distance = distance;
    }
}

