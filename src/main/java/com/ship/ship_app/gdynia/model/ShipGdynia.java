package com.ship.ship_app.gdynia.model;

import com.github.fabiomaffioletti.firebase.document.FirebaseDocument;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;
import com.ship.ship_app.gdansk.model.ShipGdansk;

import java.util.HashMap;
import java.util.Map;

@FirebaseDocument("/GdyniaTest")
public class ShipGdynia {
    @FirebaseId
    private String id;
    private String name;
    private String date;
    private String time;
    private String info;

    private String departurePlace;
    private String arrivalPlace;
    private Map<String, String> tokens =new HashMap<>();

    public ShipGdynia() {
    }

    public ShipGdynia(String id, String name, String date, String time, String info, String departurePlace, String arrivalPlace) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.info = info;
        this.departurePlace = departurePlace;
        this.arrivalPlace = arrivalPlace;
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
        if (object instanceof ShipGdynia) {
            ShipGdynia shipGdyniaDetails = (ShipGdynia) object;
            if (name.equals(shipGdyniaDetails.name) && info.equals(shipGdyniaDetails.info) && time.equals(shipGdyniaDetails.time) && date.equals(shipGdyniaDetails.date)&& arrivalPlace.equals(shipGdyniaDetails.arrivalPlace)) {
                return true;
            }
        }
        return false;

    }
    public void changingSingnsInShipsNames (ShipGdynia shipGdynia) {
        shipGdynia.setName(shipGdynia.getName().replace('/', ' '));
        shipGdynia.setName(shipGdynia.getName().replace('.', ' '));
        shipGdynia.setName(shipGdynia.getName().replace('-', ' '));
        shipGdynia.setName(shipGdynia.getName().replace('+', ' '));
        shipGdynia.setName(shipGdynia.getName().replace('Ś', 'S'));
        shipGdynia.setName(shipGdynia.getName().replace('Ł', 'L'));
        shipGdynia.setName(shipGdynia.getName().replace('Ó', 'O'));
        shipGdynia.setName(shipGdynia.getName().replace('Ń', 'N'));
    }
    public void changingSingnsInArrivalPlace (ShipGdynia shipGdynia) {
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('/', ' '));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('.', ' '));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('-', ' '));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('+', ' '));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('Ś', 'S'));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('Ł', 'L'));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('Ó', 'O'));
        shipGdynia.setArrivalPlace(shipGdynia.getArrivalPlace().replace('Ń', 'N'));
    }
}
