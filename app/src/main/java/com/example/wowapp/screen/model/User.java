package com.example.wowapp.screen.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User  implements Serializable {

    private String userId, username, birthdate, phone, avatarUri, email;
     private  boolean isPrivate;
     List<String> followers;

    public User(String userName) {
        this.username = userName;
    }

    public User(String userId, String userName) {
        this.userId = userId;
        this.username = userName;
    }

    public User(String userId, String userName, String phone, String email) {
        this.userId = userId;
        this.username = userName;
        this.phone = phone;
        this.email = email;
        this.birthdate = this.avatarUri = "";

        isPrivate = false;
//        totalLikes = 0;
    }

    public User(String userId, String userName, String phone, String email, List<String> followers) {
        this.userId = userId;
        this.username = userName;
        this.phone = phone;
        this.email = email;
        this.birthdate = this.avatarUri = "";
        this.followers = followers;
//        followers = new ArrayList<>();
//        following = new ArrayList<>();
//        myVideoUri = new ArrayList<>();
//        myFavoriteVideoUri = new ArrayList<>();
        isPrivate = false;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("username", username);
        result.put("birthdate", birthdate);
        result.put("avatarrUri", avatarUri);
        result.put("email", email);
        result.put("isPrivate", isPrivate);
        result.put("phone", phone);
        return result;

    }
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return username;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public String getEmail() {
        return email;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public List<String> getFollowers() {
        return followers;
    }

}
