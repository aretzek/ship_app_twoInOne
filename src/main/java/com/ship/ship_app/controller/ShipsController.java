package com.ship.ship_app.controller;


import com.ship.ship_app.Swinoujscie.model.ShipSwinoujscie;
import com.ship.ship_app.Swinoujscie.service.ShipManagerSwinoujscie;
import com.ship.ship_app.Szczecin.service.ShipManagerSzczecin;
import com.ship.ship_app.Szczecin.service.ShipSzczecin;
import com.ship.ship_app.gdansk.model.ShipGdansk;
import com.ship.ship_app.gdansk.service.ShipManager;
import com.ship.ship_app.gdansk.service.ShipRepository;
import com.ship.ship_app.gdynia.model.ShipGdynia;
import com.ship.ship_app.gdynia.service.ShipManagerGdynia;
import com.ship.ship_app.gdynia.service.ShipRepositoryGdynia;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.stream.Collectors;


@Controller
@RequestMapping()
public class ShipsController {

    ShipManager shipManager;
    ShipManagerGdynia shipManagerGdynia;
    ShipManagerSzczecin shipManagerSzczecin;
    ShipManagerSwinoujscie shipManagerSwinoujscie;

    public ShipsController(ShipManager shipManager, ShipManagerGdynia shipManagerGdynia, ShipManagerSzczecin shipManagerSzczecin, ShipManagerSwinoujscie shipManagerSwinoujscie) {
        this.shipManager = shipManager;
        this.shipManagerGdynia = shipManagerGdynia;
        this.shipManagerSzczecin = shipManagerSzczecin;
        this.shipManagerSwinoujscie = shipManagerSwinoujscie;
    }

    @GetMapping("/gdansk")
    @ResponseBody
    public List<ShipGdansk> viewUnipilGdansk() {
         return shipManager.getLastUpdatedShipGdanskList();

    }

    @GetMapping("/Gdynia")
    @ResponseBody
    public List<ShipGdynia> shipsGdynia (){
        return shipManagerGdynia.getLastUpdatedShipGdyniaList();

    }

    @GetMapping("/Szczecin")
    @ResponseBody
    public List<ShipSzczecin> viewUnipilSzczecin() {
        return shipManagerSzczecin.getLastUpdatedShipGdyniaList();
    }

    @GetMapping("/Swinoujscie")
    @ResponseBody
    public List<ShipSwinoujscie> viewUnipilSwinoujscie() {
        return shipManagerSwinoujscie.getLastUpdatedShipSwinoujscieList();
    }
/*    @PostMapping("/insertToken")
    public void addToken( @RequestBody Ship ship){
        shipManager.insertTokenToFirebase(ship);
    }
    @PostMapping("/removeToken/{shipId}/{token}")

    public void removeToken( @PathVariable String shipId, @PathVariable String token){
        shipManager.removeTokenFromFirebase(shipId,token);
    }*/



}

