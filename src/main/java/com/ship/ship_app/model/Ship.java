package com.ship.ship_app.model;

import com.github.fabiomaffioletti.firebase.document.FirebaseDocument;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;

import java.util.HashMap;
import java.util.Map;


@FirebaseDocument("/Gdansk")
public class Ship {
    @FirebaseId
    private String id;
    private String name;
    private String date;
    private String time;
    private String info;

    private String departurePlace;
    private String arrivalPlace;
    private Map<String, String> tokens =new HashMap<>();

    public Ship() {

    }


    public Ship(String id, String name, String date, String time, String info, String departurePlace, String arrivalPlace) {
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
        if (object instanceof Ship) {
            Ship shipDetails = (Ship) object;
            if (name.equals(shipDetails.name) && info.equals(shipDetails.info) && time.equals(shipDetails.time) && date.equals(shipDetails.date)&& arrivalPlace.equals(shipDetails.arrivalPlace)) {
                return true;
            }
        }
        return false;


    }
    public void changingSingnsInShipsNames (Ship ship) {
        ship.setName(ship.getName().replace('/', ' '));
        ship.setName(ship.getName().replace('.', ' '));
        ship.setName(ship.getName().replace('-', ' '));
    }


    public void changingSingnsInArrivalPlace (Ship ship) {
        ship.setArrivalPlace(ship.getArrivalPlace().replace('/', ' '));
        ship.setArrivalPlace(ship.getArrivalPlace().replace('.', ' '));
        ship.setArrivalPlace(ship.getArrivalPlace().replace('-', ' '));
    }

}