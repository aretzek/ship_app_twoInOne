package com.ship.ship_app.gdynia.service;


import com.ship.ship_app.gdynia.model.ShipGdynia;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
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
        List<Node> nodes = table.childNodes().get(1).childNodes();
        String stream = rows.toString().replace(" <td class=\"data\">", ";").replace("<td>", ";")
                .replace("</td>", "").replace("<tr>", "").replace("</tr>", "").replace("\n", "");
        tempTable = stream.split(";");
        ShipGdynia shipGdynia = null;
        for (int i = 1; i < nodes.size() -1; i++) {
            shipGdynia =new ShipGdynia();

            shipGdynia.setDate(((Element) nodes.get(i)).child(0).getElementsByTag("td").text());
            shipGdynia.setInfo(((Element) nodes.get(i)).child(1).getElementsByTag("td").text());
            shipGdynia.setTime(((Element) nodes.get(i)).child(2).getElementsByTag("td").text());
            shipGdynia.setName(((Element) nodes.get(i)).child(3).getElementsByTag("td").text());
          //  shipGdynia.changingSingnsInShipsNames(shipGdynia);
            shipGdynia.setDeparturePlace(((Element) nodes.get(i)).child(5).getElementsByTag("td").text());
            shipGdynia.setArrivalPlace(((Element) nodes.get(i)).child(6).getElementsByTag("td").text());
        //    shipGdynia.changingSingnsInArrivalPlace(shipGdynia);
         //   shipGdynia.setId(shipGdynia.getName()+ shipGdynia.getArrivalPlace());
           // System.out.println(ship.getName());

            shipGdynias.add(shipGdynia);
        }
        return shipGdynias;
    }
}


