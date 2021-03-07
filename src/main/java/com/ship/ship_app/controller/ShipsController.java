package com.ship.ship_app.controller;


import com.ship.ship_app.gdansk.model.ShipGdansk;
import com.ship.ship_app.gdansk.service.ShipRepository;
import com.ship.ship_app.gdynia.model.ShipGdynia;
import com.ship.ship_app.gdynia.service.ShipRepositoryGdynia;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
        System.out.println("tu");
         return allGdansk;

    }

    @GetMapping("/allShipsGdynia")
    @ResponseBody
    public List<ShipGdynia> viewUnipilGdynia() {
        List<ShipGdynia> allGdynia = shipRepositoryGdynia.findAll();
        System.out.println("tu");
        return allGdynia;

    }


}

