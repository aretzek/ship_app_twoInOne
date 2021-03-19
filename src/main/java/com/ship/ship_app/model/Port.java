package com.ship.ship_app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fabiomaffioletti.firebase.document.FirebaseDocument;

import java.util.List;
@FirebaseDocument("/Ports")

public class Port {
    @JsonProperty
    String portName;
    List<Ship> ShipList;

    public Port(String portName) {
        this.portName = portName;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public List<Ship> getShipList() {
        return ShipList;
    }

    public void setShipList(List<Ship> shipList) {
        ShipList = shipList;
    }

}
