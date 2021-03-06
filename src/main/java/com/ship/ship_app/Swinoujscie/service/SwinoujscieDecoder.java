package com.ship.ship_app.Swinoujscie.service;

import com.ship.ship_app.Swinoujscie.model.ShipSwinoujscie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SwinoujscieDecoder {


    public List<ShipSwinoujscie> getShipList() {

        List<ShipSwinoujscie> shipList = new ArrayList<>();
        List<String> tempTable = new ArrayList<>();
        Document doc = null;

        {
            try {
                doc = Jsoup.connect("https://www.szczecinpilot.pl/ruch_old.php?a=swinoujscie").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Elements cellsSwinouscieIn = doc.select("table").get(0).select("tr").select("td");
        Elements cellsSwinouscieOut = doc.select("table").get(1).select("tr").select("td");
        Elements cellsSwinouscieShifting = doc.select("table").get(2).select("tr").select(("td"));
        tempTable.addAll(cellsSwinouscieIn.eachText());
        tempTable.addAll(cellsSwinouscieOut.eachText());
        tempTable.addAll(cellsSwinouscieShifting.eachText());

        for (int i = 0; i < tempTable.size(); i = i + 7) {
            ShipSwinoujscie ship = new ShipSwinoujscie();
            ship.setDate(tempTable.get(i));
            ship.setTime(tempTable.get(i + 1));
            ship.setInfo("");
            ship.setName(tempTable.get(i + 2));
            ship.setArrivalPlace(tempTable.get(i + 5));
            ship.setDeparturePlace((tempTable.get(i + 6)));
         //   ship.setPort("Swinoujscie");
            ship.changingSingnsInShipsNames(ship);
            ship.changingSingnsInArrivalPlace(ship);
            ship.setId(ship.getName() + ship.getArrivalPlace());
            shipList.add(ship);
        }

        return shipList;
    }
}









