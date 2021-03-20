package com.ship.ship_app.Swinoujscie.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fabiomaffioletti.firebase.document.FirebaseDocument;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;
import com.ship.ship_app.gdynia.model.ShipGdynia;

import java.util.HashMap;
import java.util.Map;

@FirebaseDocument("Swinoujscie")
public class ShipSwinoujscie {
    @FirebaseId
    @JsonProperty
    private String id;
    private String name;
    private String date;
    private String time;
    private String info;

    private String departurePlace;
    private String arrivalPlace;
    private Map<String, String> tokens =new HashMap<>();

    public ShipSwinoujscie(String id, String name, String date, String time, String info, String departurePlace, String arrivalPlace) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.info = info;
        this.departurePlace = departurePlace;
        this.arrivalPlace = arrivalPlace;
    }

    public ShipSwinoujscie() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Map<String, String> getTokens() {
        return tokens;
    }

    public void setTokens(Map<String, String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof ShipSwinoujscie) {
            ShipSwinoujscie shipSwinoujscieDetails = (ShipSwinoujscie) object;
            if (name.equals(shipSwinoujscieDetails.name) && info.equals(shipSwinoujscieDetails.info) && time.equals(shipSwinoujscieDetails.time) && date.equals(shipSwinoujscieDetails.date)&& arrivalPlace.equals(shipSwinoujscieDetails.arrivalPlace)) {
                return true;
            }
        }
        return false;

    }
    public void changingSingnsInShipsNames (ShipSwinoujscie shipGdynia) {
        shipGdynia.setName(shipGdynia.getName().replace('/', ' '));
        shipGdynia.setName(shipGdynia.getName().replace('.', ' '));
        shipGdynia.setName(shipGdynia.getName().replace('-', ' '));
        shipGdynia.setName(shipGdynia.getName().replace('+', ' '));
        shipGdynia.setName(shipGdynia.getName().replace('Ś', 'S'));
        shipGdynia.setName(shipGdynia.getName().replace('Ł', 'L'));
        shipGdynia.setName(shipGdynia.getName().replace('Ó', 'O'));
        shipGdynia.setName(shipGdynia.getName().replace('Ń', 'N'));
        shipGdynia.setName(shipGdynia.getName().replace('Ę', 'E'));
        shipGdynia.setName(shipGdynia.getName().replace('&', ' '));
    }
    public void changingSingnsInArrivalPlace (ShipSwinoujscie shipGdynia) {
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('/', ' '));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('.', ' '));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('-', ' '));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('+', ' '));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('Ś', 'S'));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('Ł', 'L'));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('Ó', 'O'));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('Ń', 'N'));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('Ę', 'E'));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('&', ' '));
    }
}
