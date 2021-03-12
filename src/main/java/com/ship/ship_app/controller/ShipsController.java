package com.ship.ship_app.controller;


import com.ship.ship_app.gdansk.model.ShipGdansk;
import com.ship.ship_app.gdansk.service.ShipRepository;
import com.ship.ship_app.gdynia.model.ShipGdynia;
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

    ShipRepository shipRepository;
    ShipRepositoryGdynia shipRepositoryGdynia;

    public ShipsController(ShipRepository shipRepository, ShipRepositoryGdynia shipRepositoryGdynia) {
        this.shipRepository = shipRepository;
        this.shipRepositoryGdynia = shipRepositoryGdynia;
    }

    @GetMapping("/allShipsGdansk")
    @ResponseBody
    public List<ShipGdansk> viewUnipilGdansk() {
        List<ShipGdansk> allGdansk = shipRepository.findAll();
        shipRepository.shipGdanskWithId();
        System.out.println("tu");
         return allGdansk;

    }

    @GetMapping("/allShipsGdynia")
    @ResponseBody
    public List<ShipGdynia> viewUnipilGdynia() {

        return  shipRepositoryGdynia.findAll();
    }

    @GetMapping("/allShipsGdyniaByArrivalPlace")
    @ResponseBody
    public List<ShipGdynia> shipsGdyniaSortedByBerth (){
        List<ShipGdynia> allGdynia = shipRepositoryGdynia.findAll();
        return allGdynia.stream().sorted(Comparator.comparing(ShipGdynia::getArrivalPlace)).collect(Collectors.toList());
    }

    @GetMapping("/allShipsGdanskByArrivalPlace")
    @ResponseBody
    public List<ShipGdansk> shipGdansksSortedByBerth (){
        return shipRepository.findAll().stream().sorted(Comparator.comparing(ShipGdansk::getArrivalPlace)).collect(Collectors.toList());

    }


}

