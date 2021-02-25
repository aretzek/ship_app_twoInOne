package com.ship.ship_app.service.ports;

import com.ship.ship_app.model.Ship;
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




    public List<Ship> getShipList() throws IOException {

        List<Ship> shipList = new ArrayList<Ship>();
        String[] tempTable;
       Document doc = Jsoup.connect("http://www.gdanskpilot.pl/index.php?content=traffic").get();
       //Document doc = Jsoup.connect("http://localhost:8080/").get();//


        Element table = doc.select("table").get(0); //select the first table.
        Elements rows = table.select("tr");

        String stream = rows.toString().replace(" <td class=\"data\">", ";").replace("<td>", ";")
                .replace("</td>", "").replace("<tr>", "").replace("</tr>", "").replace("\n", "");
        tempTable = stream.split(";");
        Ship ship = null;
        for (int i = 1; i < tempTable.length -1; i = i + 7) {
            ship=new Ship();

            ship.setDate(tempTable[i]);
            ship.setTime(" "+tempTable[i + 2]);
            ship.setInfo(tempTable[i + 1]);
            ship.setName(tempTable[i + 3]);
            ship.changingSingnsInShipsNames(ship);
            ship.setDeparturePlace(tempTable[i + 5]);
            ship.setArrivalPlace(tempTable[i + 6]);
            ship.changingSingnsInArrivalPlace(ship);
            ship.setId(ship.getName()+ship.getArrivalPlace());
           // System.out.println(ship.getName());

            shipList.add(ship);
        }
        return shipList;
    }
}


