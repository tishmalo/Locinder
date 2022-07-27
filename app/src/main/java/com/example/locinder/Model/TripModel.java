package com.example.locinder.Model;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TripModel {

    String currentuserid, date, location, photo, people, price;

    public TripModel() {
    }

    public TripModel(String currentuserid, String date, String location, String photo, String people, String price) {
        this.currentuserid = currentuserid;
        this.date = date;
        this.location = location;
        this.photo = photo;
        this.people = people;
        this.price = price;
    }

    public String getcurrentuserid() {
        return currentuserid;
    }

    public void setcurrentuserid(String currentuserid) {
        this.currentuserid = currentuserid;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }

    public String getphoto() {
        return photo;
    }

    public void setphoto(String photo) {
        this.photo = photo;
    }

    public String getpeople() {
        return people;
    }

    public void setpeople(String people) {
        this.people = people;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }
}
