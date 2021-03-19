package com.ship.ship_app.service;


import com.ship.ship_app.service.repository.PortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PortManager {
    ShipManager shipManager;
    PortRepository portRepository;
@Autowired
    public PortManager(ShipManager shipManager, PortRepository portRepository) {
        this.shipManager = shipManager;
        this.portRepository = portRepository;
    }

    public void updatePort (String portName) throws Exception {

    shipManager.updateAllChanges();
    }
}

