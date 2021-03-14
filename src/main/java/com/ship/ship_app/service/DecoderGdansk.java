package com.ship.ship_app.service;

import com.ship.ship_app.model.Ship;
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
public class DecoderGdansk {




    public List<Ship> getShipList() throws IOException {

        List<Ship> shipGdanskList = new ArrayList<Ship>();
        String[] tempTable;
        Document doc = Jsoup.connect("http://www.gdanskpilot.pl/index.php?content=traffic").get();
        //Document doc = Jsoup.connect("http://localhost:8080/").get();//


        Element table = doc.select("table").get(0); //select the first table.
        Elements rows = table.select("tr");
        List<Node> nodes = table.childNodes().get(1).childNodes();
        String tablica = nodes.toString();
        String stream = rows.toString().replace(" <td class=\"data\">", ";").replace("<td>", ";")
                .replace("</td>", "").replace("<tr>", "").replace("</tr>", "").replace("\n", "");
        tempTable = stream.split(";");
        Ship shipGdansk = null;
        for (int i = 1; i < nodes.size() -2;  i++ ) {
            shipGdansk =new Ship();

            shipGdansk.setDate(((Element) nodes.get(i+1)).child(0).getElementsByTag("td").text());
            shipGdansk.setTime(((Element) nodes.get(i+1)).child(2).getElementsByTag("td").text());
            shipGdansk.setInfo(((Element) nodes.get(i+1)).child(1).getElementsByTag("td").text());
            shipGdansk.setName(((Element) nodes.get(i+1)).child(3).getElementsByTag("td").text());
            //     shipGdansk.changingSingnsInShipsNames(shipGdansk);
            shipGdansk.setDeparturePlace(((Element) nodes.get(i+1)).child(5).getElementsByTag("td").text());
            shipGdansk.setArrivalPlace(((Element) nodes.get(i+1)).child(6).getElementsByTag("td").text());
            shipGdansk.changingSingnsInArrivalPlace(shipGdansk);
            shipGdansk.setPort("Gdansk");
            shipGdansk.setId(shipGdansk.getName()+ shipGdansk.getArrivalPlace());
            System.out.println(shipGdansk.getName());

            shipGdanskList.add(shipGdansk);
        }
        return shipGdanskList;
    }
}


