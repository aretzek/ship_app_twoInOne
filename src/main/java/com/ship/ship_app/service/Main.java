package com.ship.ship_app.service;

import com.ship.ship_app.service.PageObservers.DecoderSzczecin;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        DecoderSzczecin decoderSzczecin = new DecoderSzczecin();
        System.out.println(decoderSzczecin.getShipsList());

        System.out.println("EXIT");


    }
}
