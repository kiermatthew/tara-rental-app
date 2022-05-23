package com.example.tara.Models;

public class Car  {
    public String exterior1Url, bmy, location, priceRate, userId;

    Car(){ }

    public Car(String exterior1Url, String bmy, String location, String priceRate, String userId){
        this.exterior1Url = exterior1Url;
        this.bmy = bmy;
        this.location = location;
        this.priceRate = priceRate;
        this.userId = userId;
    }


    public String getCarUrl() {
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

    public String getUserId() {
        return userId;
    }

    public void setExterior1Url(String exterior1Url) {
        this.exterior1Url = exterior1Url;
    }

    public void setBmy(String bmy) {
        this.bmy = bmy;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPriceRate(String priceRate) {
        this.priceRate = priceRate;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
