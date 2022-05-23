package com.example.tara.Models;

public class Vehicle {
    public String bmy, plateNumber, exterior1Url;

    Vehicle(){ }

    public Vehicle(String bmy, String plateNumber, String exterior1Url) {
        this.bmy = bmy;
        this.plateNumber = plateNumber;
        this.exterior1Url = exterior1Url;
    }

    public String getBmy() {
        return bmy;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getCarUrl() { return exterior1Url; }
}
