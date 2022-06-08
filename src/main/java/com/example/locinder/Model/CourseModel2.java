package com.example.locinder.Model;

public class CourseModel2 {

    private String coordinates, currentuserid, description, lat,locations, longi, photo, rating, review;
    private Double distance;

    public CourseModel2() {
    }

    public CourseModel2(String coordinates, String currentuserid, String description, String lat, String locations, String longi, String photo, String rating, String review, Double distance) {
        this.coordinates = coordinates;
        this.currentuserid = currentuserid;
        this.description = description;
        this.lat = lat;
        this.locations = locations;
        this.longi = longi;
        this.photo = photo;
        this.rating = rating;
        this.review = review;
        this.distance = distance;
    }

    public String getcoordinates() {
        return coordinates;
    }

    public void setcoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getcurrentuserid() {
        return currentuserid;
    }

    public void setcurrentuserid(String currentuserid) {
        this.currentuserid = currentuserid;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getlat() {
        return lat;
    }

    public void setlat(String lat) {
        this.lat = lat;
    }

    public String getlocations() {
        return locations;
    }

    public void setlocations(String locations) {
        this.locations = locations;
    }

    public String getlongi() {
        return longi;
    }

    public void setlongi(String longi) {
        this.longi = longi;
    }

    public String getphoto() {
        return photo;
    }

    public void setphoto(String photo) {
        this.photo = photo;
    }

    public String getrating() {
        return rating;
    }

    public void setrating(String rating) {
        this.rating = rating;
    }

    public String getreview() {
        return review;
    }

    public void setreview(String review) {
        this.review = review;
    }

    public Double getdistance() {
        return distance;
    }

    public void setdistance(Double distance) {
        this.distance = distance;
    }
}
