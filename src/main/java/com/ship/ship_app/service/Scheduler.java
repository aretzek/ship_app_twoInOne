package com.ship.ship_app.service;

import com.ship.ship_app.service.ports.SelfPinger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class Scheduler {
    private ShipManager shipManager;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private SelfPinger selfPinger;



    @Autowired
    public Scheduler(ShipManager shipManager, SelfPinger selfPinger) {
        this.shipManager = shipManager;

        this.selfPinger = selfPinger;
    }
    @Scheduled(fixedRate = 600000L)
    public void test2(){
        try {
            selfPinger.ping();
            Document doc = Jsoup.connect("https://serene-depths-36885.herokuapp.com/").get();
            System.out.println("DO NOT SLEEEP HEROKU!" + doc.title());
        } catch (Exception e) {
            System.out.println("something went wrong with ping myself... ");
        }
    }


    //@Scheduled(cron = "0 * * ? * *")
    @Scheduled(fixedRate = 60000L)

    public void test() {

        try {

            if (shipManager.getLastUpdatedShipList().isEmpty()) {
                shipManager.setInitialList();
            }
            shipManager.updateAllChanges();

            System.out.println("==========================");
            Date date = new Date(System.currentTimeMillis());
            System.out.println(formatter.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
