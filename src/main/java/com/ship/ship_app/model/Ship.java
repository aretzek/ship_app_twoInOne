package com.ship.ship_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fabiomaffioletti.firebase.document.FirebaseDocument;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;
import com.google.api.client.util.DateTime;

import java.util.ArrayList;
import java.util.List;


@FirebaseDocument("/Ports")
public class Ship {

    @JsonProperty
    @FirebaseId
   private String id;
    private String name;
    private String date;
    private String time;
    private String info;
    private DateTime dateTime;


    private String port;
    private String departurePlace;
    private String arrivalPlace;
    private List<String> tokens = new ArrayList<>();
    private List<String> emails = new ArrayList<>();

    public Ship() {

    }


    public Ship( String id, String name, String date, String time, String info, String departurePlace, String arrivalPlace) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.info = info;
        this.departurePlace = departurePlace;
        this.arrivalPlace = arrivalPlace;
        this.id = id;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getArrivalPlace() {
        return arrivalPlace;
    }

    public void setArrivalPlace(String arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
       return id;
    }

    public void addToken(String token){
        tokens.add(token);
    }

    public void removeToken(String token){
        tokens.remove(token);
    }
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof Ship) {
            Ship shipGdanskDetails = (Ship) object;
            if (name.equals(shipGdanskDetails.name) && info.equals(shipGdanskDetails.info) && time.equals(shipGdanskDetails.time) && date.equals(shipGdanskDetails.date) && arrivalPlace.equals(shipGdanskDetails.arrivalPlace)) {
                return true;
            }
        }
        return false;


    }

    public void changingSignsInShipsNames(Ship ship) {
        ship.setName(ship.getName().replace('/', ' '));
        ship.setName(ship.getName().replace('.', ' '));
        ship.setName(ship.getName().replace('-', ' '));
        ship.setName(ship.getName().replace('+', ' '));
        ship.setName(ship.getName().replace('Ś', 'S'));
        ship.setName(ship.getName().replace('Ł', 'L'));
        ship.setName(ship.getName().replace('Ó', 'O'));
        ship.setName(ship.getName().replace('Ń', 'N'));
        ship.setName(ship.getName().replace('Ę', 'E'));
        ship.setName(ship.getName().replace('&', ' '));
    }


    public void changingSingnsInArrivalPlace(Ship ship) {
        ship.setArrivalPlace(ship.getArrivalPlace().replace('/', ' '));
        ship.setArrivalPlace(ship.getArrivalPlace().replace('.', ' '));
        ship.setArrivalPlace(ship.getArrivalPlace().replace('-', ' '));
        ship.setArrivalPlace(ship.getArrivalPlace().replace('+', ' '));
        ship.setArrivalPlace(ship.getArrivalPlace().replace('Ś', 'S'));
        ship.setArrivalPlace(ship.getArrivalPlace().replace('Ł', 'L'));
        ship.setArrivalPlace(ship.getArrivalPlace().replace('Ó', 'O'));
        ship.setArrivalPlace(ship.getArrivalPlace().replace('Ń', 'N'));
        ship.setArrivalPlace(ship.getArrivalPlace().replace('Ę', 'E'));
        ship.setArrivalPlace(ship.getArrivalPlace().replace('&', ' '));
    }


}
