package com.example.tara.Models;

public class Booking {

    public String exterior1Url,bmy,location,priceRate, name,bookDate;

    Booking(){

    }

     public Booking(String exterior1Url, String bmy, String location, String priceRate, String name, String bookDate) {
        this.exterior1Url = exterior1Url;
        this.bmy = bmy;
        this.location = location;
        this.priceRate = priceRate;
        this.name = name;
        this.bookDate = bookDate;
    }

    public String getExterior1Url() {
        return exterior1Url;
    }

    public String getBmy() {
        return bmy;
    }

    public String getLocation() {
        return location;
    }

    public String getPriceRate() {
        return priceRate;
    }

    public String getName() {
        return name;
    }

    public String getBookDate() {
        return bookDate;
    }
}
