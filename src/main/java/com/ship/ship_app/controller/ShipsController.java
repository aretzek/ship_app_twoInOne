package com.ship.ship_app.controller;


import com.ship.ship_app.model.Ship;
import com.ship.ship_app.service.ShipManager;
import com.ship.ship_app.service.ShipRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Controller
@RequestMapping()
public class ShipsController {

    ShipRepository shipRepository;
    ShipManager shipManager;



    //ShipRepositoryGdynia shipRepositoryGdynia;

    public ShipsController(ShipRepository shipRepository, ShipManager shipManager) {
        this.shipRepository = shipRepository;
        this.shipManager = shipManager;

    }

    @GetMapping("/allShipsGdansk")
    @ResponseBody
    public List<Ship> viewGdansk() {

        List<Ship> allShips = shipManager.getLastUpdatedShipList();
        allShips.stream().filter(str -> str.getPort().contains("Gdansk")).collect(Collectors.toList());
        return allShips;

    }

    @GetMapping("/allShipsGdynia")
    @ResponseBody
    public List<Ship> viewGdynia() {
        List<Ship> allShips = shipManager.getLastUpdatedShipList();
        allShips.stream().filter(str -> str.getPort().contains("Gdynia")).collect(Collectors.toList());
        return allShips;
    }
    @GetMapping("/allShipsSzczecin")
    @ResponseBody
    public List<Ship> viewSzczecin() {
        List<Ship> allShips = shipManager.getLastUpdatedShipList();
        allShips.stream().filter(str -> str.getPort().contains("Szczecin")).collect(Collectors.toList());
        return allShips;
    }
    @GetMapping("/allShipsSwinoujscie")
    @ResponseBody
    public List<Ship> viewSwinoujscie() {
        List<Ship> allShips = shipManager.getLastUpdatedShipList();
        allShips.stream().filter(str -> str.getPort().contains("Swinoujscie")).collect(Collectors.toList());
        return allShips;
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

}

