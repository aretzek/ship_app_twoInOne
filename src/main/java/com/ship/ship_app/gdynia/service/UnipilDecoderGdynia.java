package com.ship.ship_app.gdansk.service;

import com.ship.ship_app.gdansk.model.ShipGdansk;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnipilDecoder  {




    public List<ShipGdansk> getShipList() throws IOException {

        List<ShipGdansk> shipGdanskList = new ArrayList<ShipGdansk>();
        String[] tempTable;
       Document doc = Jsoup.connect("http://www.gdanskpilot.pl/index.php?content=traffic").get();
       //Document doc = Jsoup.connect("http://localhost:8080/").get();//


        Element table = doc.select("table").get(0); //select the first table.
        Elements rows = table.select("tr");

        String stream = rows.toString().replace(" <td class=\"data\">", ";").replace("<td>", ";")
                .replace("</td>", "").replace("<tr>", "").replace("</tr>", "").replace("\n", "");
        tempTable = stream.split(";");
        ShipGdansk shipGdansk = null;
        for (int i = 1; i < tempTable.length -1; i = i + 7) {
            shipGdansk =new ShipGdansk();

            shipGdansk.setDate(tempTable[i]);
            shipGdansk.setTime(" "+tempTable[i + 2]);
            shipGdansk.setInfo(tempTable[i + 1]);
            shipGdansk.setName(tempTable[i + 3]);
            shipGdansk.changingSingnsInShipsNames(shipGdansk);
            shipGdansk.setDeparturePlace(tempTable[i + 5]);
            shipGdansk.setArrivalPlace(tempTable[i + 6]);
            shipGdansk.changingSingnsInArrivalPlace(shipGdansk);
            shipGdansk.setId(shipGdansk.getName()+ shipGdansk.getArrivalPlace());
           // System.out.println(ship.getName());

            shipGdanskList.add(shipGdansk);
        }
        return shipGdanskList;
    }
}


