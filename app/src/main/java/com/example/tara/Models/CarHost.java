package com.example.tara.Models;

import android.widget.TextView;

public class CarHost {
    public String address1,address2,city,postcode,province,year,brand,transmission,drivetrain,seats,type,fuelType,mileage,model,plateNumber
            ,priceRate,description,bmy,location, municipality;

    public CarHost(){

    }
    public CarHost(String address1, String address2, String city, String postcode, String province,
                   String year, String brand, String transmission, String drivetrain, String seats,
                   String type, String fuelType, String mileage, String model, String plateNumber,
                   String priceRate, String description, String bmy, String location, String municipality) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.postcode = postcode;
        this.province = province;
        this.year = year;
        this.brand = brand;
        this.transmission = transmission;
        this.drivetrain = drivetrain;
        this.seats = seats;
        this.type = type;
        this.fuelType = fuelType;
        this.mileage = mileage;
        this.model = model;
        this.plateNumber = plateNumber;
        this.priceRate = priceRate;
        this.description = description;
        this.bmy = bmy;
        this.location = location;
        this.municipality = municipality;
    }
}

