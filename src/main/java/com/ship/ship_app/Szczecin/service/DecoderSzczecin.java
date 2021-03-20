package com.ship.ship_app.Szczecin.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DecoderSzczecin {

        public List<ShipSzczecin> getShipsList() throws IOException {
            List<ShipSzczecin> shipList = new ArrayList<>();
            List<String> tempTable= new ArrayList<>();
            Document doc = Jsoup.connect("https://www.szczecinpilot.pl/ruch_old.php").get();

            Elements cellsSzczecinIn= doc.select("table").get(0).select("tr").select("td");
            Elements cellsSzczecinOut= doc.select("table").get(1).select("tr").select("td");
            Elements cellsSzczecinShifting= doc.select("table").get(2).select("tr").select(("td"));
            tempTable.addAll(cellsSzczecinIn.eachText());
            tempTable.addAll(cellsSzczecinOut.eachText());
            tempTable.addAll(cellsSzczecinShifting.eachText());


            for (int i = 0; i <tempTable.size(); i=i+7) {
                ShipSzczecin ship = new ShipSzczecin();
                ship.setDate(tempTable.get(i));
                ship.setTime(tempTable.get(i+1));
                ship.setInfo("");
                ship.setName(tempTable.get(i+2));
                ship.setArrivalPlace(tempTable.get(i+5));
                ship.setDeparturePlace((tempTable.get(i+6)));
                //ship.setPort("Szczecin");
                ship.changingSignsInShipsNames(ship);
                ship.changingSingnsInArrivalPlace(ship);
                ship.setId(ship.getName() + ship.getArrivalPlace());
                shipList.add(ship);
            }
            return shipList;
        }

    }



