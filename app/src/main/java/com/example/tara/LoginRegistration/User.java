package com.example.tara.LoginRegistration;

public class User {
    public String name;
    public String email;
    public String userId;
    public String imageUrl;
    public boolean isHost;
    public boolean bookedCar;

    public User(){

    }

    public User(String name, String email, String userId, String imageUrl, boolean isHost, boolean bookedCar) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.isHost = isHost;
        this.bookedCar = bookedCar;
    }
}
