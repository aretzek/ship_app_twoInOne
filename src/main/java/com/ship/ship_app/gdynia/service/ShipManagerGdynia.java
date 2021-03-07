package com.ship.ship_app.gdynia.service;

import com.github.fabiomaffioletti.firebase.exception.FirebaseRepositoryException;
import com.ship.ship_app.controller.NotificationSender;
import com.ship.ship_app.gdansk.model.ShipGdansk;
import com.ship.ship_app.gdansk.service.ShipManager;
import com.ship.ship_app.gdynia.model.ShipGdynia;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class ShipManagerGdynia implements InitializingBean {
    private ShipRepositoryGdynia shipRepositoryGdynia;
    private NotificationSender notificationSender;
    private UnipilDecoderGdynia unipilDecoderGdynia;

    private List<ShipGdynia> lastUpdatedShipGdyniaList = new ArrayList<>();

    @Autowired
    public ShipManagerGdynia(ShipRepositoryGdynia shipRepositoryGdynia, NotificationSender notificationSender, UnipilDecoderGdynia unipilDecoderGdynia) {
        this.shipRepositoryGdynia = shipRepositoryGdynia;
        this.notificationSender = notificationSender;
        this.unipilDecoderGdynia = unipilDecoderGdynia;
    }

    public void setInitialList (){
        lastUpdatedShipGdyniaList = shipRepositoryGdynia.findAll();
    }
    public List<ShipGdynia> getLastUpdatedShipGdyniaList (){return lastUpdatedShipGdyniaList;}
    private Map<String, String> getTokensFromFirebase(String id) {
        Map<String, String> tokensMap = new HashMap<>();
        try {
            tokensMap.putAll(shipRepositoryGdynia.get(id).getTokens());
        } catch (FirebaseRepositoryException | HttpClientErrorException e) {
            System.out.println(" nie znalazł statku o takim ID ... ");
        }
        return tokensMap;
    }
    public void updateAllChanges() throws Exception {

        List<ShipGdynia> newShipsGdynia = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfNewShipGdansks);
        List<ShipGdynia> outdatedShipsGdynia = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfOutdatedShipGdansks);
        for (ShipGdynia outdatedShipGdynia : outdatedShipsGdynia) {
            System.out.println("removed: " + outdatedShipGdynia.getName());
            shipRepositoryGdynia.remove(outdatedShipGdynia.getId());
        }
        lastUpdatedShipGdyniaList.removeAll(outdatedShipsGdynia);

        for (ShipGdynia newShipGdynia : newShipsGdynia) {
            shipRepositoryGdynia.update(newShipGdynia);
            Map<String, String> tempTokens = getTokensFromFirebase(newShipGdynia.getId());
            if (!tempTokens.isEmpty()) {

                for (String tempToken : tempTokens.values()) {

                    notificationSender.pushFCMNotification(tempToken, newShipGdynia.getName(), newShipGdynia.getDate(), newShipGdynia.getInfo(), newShipGdynia.getTime());

                    System.out.println(tempToken);
                }
            }

        }
        lastUpdatedShipGdyniaList.addAll(newShipsGdynia);
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
    private ShipManagerGdynia.ChangesInShips findDifferencesBetweenLastUpdatedListAndActualList() throws Exception {
        List<ShipGdynia> actualShipGdyniaList = unipilDecoderGdynia.getShipListGdynia();
        ShipManagerGdynia.ChangesInShips changesInShips = new ShipManagerGdynia.ChangesInShips();

        for (ShipGdynia shipGdynia : actualShipGdyniaList) {

            if (!lastUpdatedShipGdyniaList.contains(shipGdynia)) {  //
                shipGdynia.setTokens(getTokensFromFirebase(shipGdynia.getId()));
                changesInShips.listOfNewShipGdansks.add(shipGdynia);
            }
        }
        for (ShipGdynia shipGdynia : lastUpdatedShipGdyniaList) {
            if (actualShipGdyniaList.contains(shipGdynia)) {
                changesInShips.listOfUnchangedShipGdansks.add(shipGdynia);
            } else {
                changesInShips.listOfOutdatedShipGdansks.add(shipGdynia);
            }
        }

        for (ShipGdynia shipGdynia : lastUpdatedShipGdyniaList) {
            if (!actualShipGdyniaList.contains(shipGdynia)) {
                changesInShips.listOfOutdatedShipGdansks.add(shipGdynia);
            }
        }
        return changesInShips;
    }

    static private class ChangesInShips {
        Set<ShipGdynia> listOfNewShipGdansks = new HashSet<>();
        Set<ShipGdynia> listOfOutdatedShipGdansks = new HashSet<>();
        Set<ShipGdynia> listOfUnchangedShipGdansks = new HashSet<>();
    }

}
