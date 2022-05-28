package com.example.tara.LoginSignup;

public class User {
    public String name;
    public String contactNum;
    public String email;
    public String userId;
    public String imageUrl;
    public boolean isHost;
    public boolean bookedCar;
    public boolean isVerified;

    public User(){

    }

    public User(String name,String contactNum, String email, String userId, String imageUrl, boolean isHost, boolean bookedCar, boolean isVerified) {
        this.name = name;
        this.contactNum = contactNum;
        this.email = email;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.isHost = isHost;
        this.bookedCar = bookedCar;
        this.isVerified = isVerified;
    }
}
