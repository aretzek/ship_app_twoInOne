package com.ship.ship_app.controller;


import com.ship.ship_app.model.Ship;
import com.ship.ship_app.service.ShipManager;
import com.ship.ship_app.service.ShipRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.mail.internet.AddressException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping()
public class ShipsController {

    ShipRepository shipRepository;
    ShipManager shipManager;


    public ShipsController(ShipRepository shipRepository, ShipManager shipManager) {
        this.shipRepository = shipRepository;
        this.shipManager = shipManager;

    }

    @GetMapping("/allShipsGdansk")
    @ResponseBody
    public List<Ship> viewUnipilGdansk() {
        return shipManager.getLastUpdatedShipList().stream().filter(str -> str.getPort().contains("Gdansk")).collect(Collectors.toList());
    }

    @GetMapping("/allShipsGdynia")
    @ResponseBody
    public List<Ship> viewUnipilGdynia() throws AddressException {
 //       InternetAddress[] Emails = InternetAddress.parse("shipsdevteam@gmail.com");
 //       SendEmail.send(Emails, "Twój obserwowany statek", "Coś się zmieniło ! Twój obserwowany statek ma zmienione informacje, zobacz w aplikacji Ship app pilot");
        return  shipManager.getLastUpdatedShipList().stream().filter(str -> str.getPort().contains("Gdynia")).collect(Collectors.toList());
    }

    @GetMapping("/allShipsSzczecin")
    @ResponseBody
    public List<Ship> viewUnipilSzczecin() {
        return shipManager.getLastUpdatedShipList().stream().filter(str -> str.getPort().contains("Szczecin")).collect(Collectors.toList());
    }

    @GetMapping("/allShipsSwinoujscie")
    @ResponseBody
    public List<Ship> viewUnipilSwinoujscie() {
        return shipManager.getLastUpdatedShipList().stream().filter(str -> str.getPort().contains("Swinoujscie")).collect(Collectors.toList());
    }
    @PostMapping("/insertToken")

    public void addToken( @RequestBody Ship ship){
        shipManager.insertTokenToFirebase(ship);
    }
    @PostMapping("/removeToken/{shipId}/{token}")

    public void removeToken( @PathVariable String shipId, @PathVariable String token){
        shipManager.removeTokenFromFirebase(shipId,token);
    }


    }
//
//    @GetMapping("/allShipsGdyniaByArrivalPlace")
//    @ResponseBody
//    public List<ShipGdynia> shipsGdyniaSortedByBerth (){
//        List<ShipGdynia> allGdynia = shipRepositoryGdynia.findAll();
//        return allGdynia;
//        //allGdynia.stream().sorted(Comparator.comparing(ShipGdynia::getArrivalPlace)).collect(Collectors.toList());
//    }
//
//    @GetMapping("/allShipsGdanskByArrivalPlace")
//    @ResponseBody
//    public List<Ship> shipGdansksSortedByBerth (){
//        List<Ship> allGdansk = shipRepository.findAll();
//
//        List<Ship> collect = allGdansk.stream().sorted(Comparator.comparing(Ship::getArrivalPlace)).collect(Collectors.toList());
//        return collect;
//    }
//



