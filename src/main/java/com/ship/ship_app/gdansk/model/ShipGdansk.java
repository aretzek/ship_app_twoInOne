package com.ship.ship_app.gdansk.model;

import com.github.fabiomaffioletti.firebase.document.FirebaseDocument;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;

import java.util.HashMap;
import java.util.Map;


@FirebaseDocument("/GdanskTest")
public class ShipGdansk {
    @FirebaseId
    private String id;
    private String name;
    private String date;
    private String time;
    private String info;

    private String departurePlace;
    private String arrivalPlace;
    private Map<String, String> tokens =new HashMap<>();

    public ShipGdansk() {

    }


    public ShipGdansk(String id, String name, String date, String time, String info, String departurePlace, String arrivalPlace) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.info = info;
        this.departurePlace = departurePlace;
        this.arrivalPlace = arrivalPlace;
        this.id = id;
    }

    public Map<String, String> getTokens() {
        return tokens;
    }

    public void setTokens(Map<String, String> tokens) {
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

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof ShipGdansk) {
            ShipGdansk shipGdanskDetails = (ShipGdansk) object;
            if (name.equals(shipGdanskDetails.name) && info.equals(shipGdanskDetails.info) && time.equals(shipGdanskDetails.time) && date.equals(shipGdanskDetails.date)&& arrivalPlace.equals(shipGdanskDetails.arrivalPlace)) {
                return true;
            }
        }
        return false;


    }
    public void changingSingnsInShipsNames (ShipGdansk shipGdansk) {
        shipGdansk.setName(shipGdansk.getName().replace('/', ' '));
        shipGdansk.setName(shipGdansk.getName().replace('.', ' '));
        shipGdansk.setName(shipGdansk.getName().replace('-', ' '));
        shipGdansk.setName(shipGdansk.getName().replace('+', ' '));
        shipGdansk.setName(shipGdansk.getName().replace('Ś', 'S'));
        shipGdansk.setName(shipGdansk.getName().replace('Ł', 'L'));
        shipGdansk.setName(shipGdansk.getName().replace('Ó', 'O'));
        shipGdansk.setName(shipGdansk.getName().replace('Ń', 'N'));
        shipGdansk.setName(shipGdansk.getName().replace('Ę', 'E'));
        shipGdansk.setName(shipGdansk.getName().replace('&', ' '));
    }


    public void changingSingnsInArrivalPlace (ShipGdansk shipGdansk) {
        shipGdansk.setArrivalPlace(shipGdansk.getArrivalPlace().replace('/', ' '));
        shipGdansk.setArrivalPlace(shipGdansk.getArrivalPlace().replace('.', ' '));
        shipGdansk.setArrivalPlace(shipGdansk.getArrivalPlace().replace('-', ' '));
        shipGdansk.setArrivalPlace(shipGdansk.getArrivalPlace().replace('+', ' '));
        shipGdansk.setArrivalPlace(shipGdansk.getArrivalPlace().replace('Ś', 'S'));
        shipGdansk.setArrivalPlace(shipGdansk.getArrivalPlace().replace('Ł', 'L'));
        shipGdansk.setArrivalPlace(shipGdansk.getArrivalPlace().replace('Ó', 'O'));
        shipGdansk.setArrivalPlace(shipGdansk.getArrivalPlace().replace('Ń', 'N'));
        shipGdansk.setArrivalPlace(shipGdansk.getArrivalPlace().replace('Ę', 'E'));
        shipGdansk.setArrivalPlace(shipGdansk.getArrivalPlace().replace('&', ' '));
    }



}
