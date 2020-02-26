package com.ship.ship_app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping()
public class ShipsController {






    @GetMapping("/index")
    public String viewUnipil() {
        return "index";

    }


}

