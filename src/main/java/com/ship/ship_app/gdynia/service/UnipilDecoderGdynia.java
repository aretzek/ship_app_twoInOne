package com.ship.ship_app.gdynia.service;


import com.ship.ship_app.gdynia.model.ShipGdynia;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnipilDecoderGdynia {




    public List<ShipGdynia> getShipListGdynia() throws IOException {

        List<ShipGdynia> shipGdynias = new ArrayList<ShipGdynia>();
        String[] tempTable;
       Document doc = Jsoup.connect("https://www.unipil.pl/?a=ruch").get();
       //Document doc = Jsoup.connect("http://localhost:8080/").get();//


        Element table = doc.select("table").get(0); //select the first table.
        Elements rows = table.select("tr");

        String stream = rows.toString().replace(" <td class=\"data\">", ";").replace("<td>", ";")
                .replace("</td>", "").replace("<tr>", "").replace("</tr>", "").replace("\n", "");
        tempTable = stream.split(";");
        ShipGdynia shipGdynia = null;
        for (int i = 1; i < tempTable.length -1; i = i + 7) {
            shipGdynia =new ShipGdynia();

            shipGdynia.setDate(tempTable[i]);
            shipGdynia.setTime(" "+tempTable[i + 2]);
            shipGdynia.setInfo(tempTable[i + 1]);
            shipGdynia.setName(tempTable[i + 3]);
            shipGdynia.changingSingnsInShipsNames(shipGdynia);
            shipGdynia.setDeparturePlace(tempTable[i + 5]);
            shipGdynia.setArrivalPlace(tempTable[i + 6]);
            shipGdynia.changingSingnsInArrivalPlace(shipGdynia);
            shipGdynia.setId(shipGdynia.getName()+ shipGdynia.getArrivalPlace());
           // System.out.println(ship.getName());

            shipGdynias.add(shipGdynia);
        }
        return shipGdynias;
    }
}


