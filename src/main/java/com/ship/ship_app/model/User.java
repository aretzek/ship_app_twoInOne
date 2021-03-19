package com.ship.ship_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fabiomaffioletti.firebase.document.FirebaseDocument;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;

import java.util.Date;
import java.util.Set;

@FirebaseDocument("/Users")

public class User {

    @JsonProperty
    @FirebaseId
    private String login;
    private String password;
    private String email;
    private Set<String> favoriteShipList;
    private Date lastActivity;
    private String myToken;
    private Set<String> favoritePorts;

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getFavoriteShipList() {
        return favoriteShipList;
    }

    public void setFavoriteShipList(Set<String> favoriteShipList) {
        this.favoriteShipList = favoriteShipList;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getMyToken() {
        return myToken;
    }

    public void setMyToken(String myToken) {
        this.myToken = myToken;
    }
}
