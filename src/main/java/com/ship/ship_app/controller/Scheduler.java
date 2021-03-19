package com.ship.ship_app.controller;

import com.ship.ship_app.service.PortManager;
import com.ship.ship_app.service.ShipManager;
//import com.ship.ship_app.gdynia.service.ShipManagerGdynia;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class  Scheduler {
    private ShipManager shipManager;
   // private ShipManagerGdynia shipManagerGdynia;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private SelfPinger selfPinger;
    private PortManager portManager;


    @Autowired
    public Scheduler(PortManager portManager,ShipManager shipManager, SelfPinger selfPinger) {
        this.shipManager = shipManager;
        this.selfPinger = selfPinger;
        this.portManager =portManager;
    }
    @Scheduled(fixedRate = 600000L)
    public void test2(){
        try {
            selfPinger.ping();
            Document doc = Jsoup.connect("https://unipilot.herokuapp.com/").get();
            System.out.println("DO NOT SLEEEP HEROKU!" + doc.title());
        } catch (Exception e) {
            System.out.println("something went wrong with ping myself... ");
        }
    }


    //@Scheduled(cron = "0 * * ? * *")
    @Scheduled(fixedRate = 180000L)

    public void test() {

        try {
            portManager.updatePort("Gdynia");
            portManager.updatePort("Gdansk");
            portManager.updatePort("Szczecin");
            portManager.updatePort("Swinoujscie");
//            if (shipManager.getLastUpdatedShipList().isEmpty()) {
//                shipManager.setInitialList();
//            }
//            shipManager.updateAllChanges();

            System.out.println("==========================");
            Date date = new Date(System.currentTimeMillis());
            System.out.println(formatter.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
 //   @Scheduled(fixedRate = 60000L)

//    public void test3() {
//
//        try {
//
//            if (shipManagerGdynia.getLastUpdatedShipGdyniaList().isEmpty()) {
//                shipManagerGdynia.setInitialList();
//            }
//            shipManagerGdynia.updateAllChanges();
//
//            System.out.println("==========================");
//            Date date = new Date(System.currentTimeMillis());
//            System.out.println(formatter.format(date));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
