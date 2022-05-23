package com.example.tara.Models;

public class User {
    public String name;
    public String email;
    public String userId;
    public String imageUrl;
    public boolean isHost;

    public User(){

    }

    public User(String name, String email, String userId, String imageUrl, boolean isHost) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.imageUrl = imageUrl;
        this.isHost = isHost;
    }
}
