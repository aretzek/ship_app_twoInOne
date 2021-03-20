package com.ship.ship_app.Szczecin.service;

import com.github.fabiomaffioletti.firebase.exception.FirebaseRepositoryException;
import com.ship.ship_app.controller.NotificationSender;
import com.ship.ship_app.gdynia.model.ShipGdynia;
import com.ship.ship_app.gdynia.service.ShipManagerGdynia;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class ShipManagerSzczecin implements InitializingBean {
    private ShipRepositorySzczecin shipRepositorySzczecin;
    private NotificationSender notificationSender;
    private DecoderSzczecin decoderSzczecin;

    private List<ShipSzczecin> lastUpdatedShipSzczecinList = new ArrayList<>();
    @Autowired
    public ShipManagerSzczecin(ShipRepositorySzczecin shipRepositorySzczecin, NotificationSender notificationSender, DecoderSzczecin decoderSzczecin) {
        this.shipRepositorySzczecin = shipRepositorySzczecin;
        this.notificationSender = notificationSender;
        this.decoderSzczecin = decoderSzczecin;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public void setInitialList (){
        lastUpdatedShipSzczecinList = shipRepositorySzczecin.findAll();
    }
    public List<ShipSzczecin> getLastUpdatedShipGdyniaList (){return lastUpdatedShipSzczecinList;}
    private Map<String, String> getTokensFromFirebase(String id) {
        Map<String, String> tokensMap = new HashMap<>();
        try {
            tokensMap.putAll(shipRepositorySzczecin.get(id).getTokens());
        } catch (FirebaseRepositoryException | HttpClientErrorException e) {
            System.out.println(" nie znalazł statku o takim ID ... ");
        }
        return tokensMap;
    }
    public void updateAllChanges() throws Exception {

        List<ShipSzczecin> newShipsSzczecin = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfNewShipSzczecin);
        List<ShipSzczecin> outdatedShipsSzczecin = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfOutdatedShipSzczecin);
        for (ShipSzczecin outdatedShipSzczecin : outdatedShipsSzczecin) {
            System.out.println("removed: " + outdatedShipSzczecin.getName());
            shipRepositorySzczecin.remove(outdatedShipSzczecin.getId());
        }
        lastUpdatedShipSzczecinList.removeAll(outdatedShipsSzczecin);

        for (ShipSzczecin newShipSzczecin : newShipsSzczecin) {
            shipRepositorySzczecin.update(newShipSzczecin);
            Map<String, String> tempTokens = getTokensFromFirebase(newShipSzczecin.getId());
            if (!tempTokens.isEmpty()) {

                for (String tempToken : tempTokens.values()) {

                    notificationSender.pushFCMNotification(tempToken, newShipSzczecin.getName(),
                            newShipSzczecin.getDate(), newShipSzczecin.getInfo(), newShipSzczecin.getTime());

                    System.out.println(tempToken);
                }
            }

        }
        lastUpdatedShipSzczecinList.addAll(newShipsSzczecin);
    }



    private ShipManagerSzczecin.ChangesInShips findDifferencesBetweenLastUpdatedListAndActualList() throws Exception {
        List<ShipSzczecin> actualShipSzczecinList = decoderSzczecin.getShipsList();
        ShipManagerSzczecin.ChangesInShips changesInShips = new ShipManagerSzczecin.ChangesInShips();

        for (ShipSzczecin shipSzczecin : actualShipSzczecinList) {

            if (!lastUpdatedShipSzczecinList.contains(shipSzczecin)) {  //
                shipSzczecin.setTokens(getTokensFromFirebase(shipSzczecin.getId()));
                changesInShips.listOfNewShipSzczecin.add(shipSzczecin);
            }
        }
        for (ShipSzczecin shipSzczecin : lastUpdatedShipSzczecinList) {
            if (actualShipSzczecinList.contains(shipSzczecin)) {
                changesInShips.listOfUnchangedShipSzczecin.add(shipSzczecin);
            } else {
                changesInShips.listOfOutdatedShipSzczecin.add(shipSzczecin);
            }
        }

        for (ShipSzczecin shipSzczecin : lastUpdatedShipSzczecinList) {
            if (!actualShipSzczecinList.contains(shipSzczecin)) {
                changesInShips.listOfOutdatedShipSzczecin.add(shipSzczecin);
            }
        }
        return changesInShips;
    }

    static private class ChangesInShips {
        Set<ShipSzczecin> listOfNewShipSzczecin = new HashSet<>();
        Set<ShipSzczecin> listOfOutdatedShipSzczecin = new HashSet<>();
        Set<ShipSzczecin> listOfUnchangedShipSzczecin = new HashSet<>();
    }

}
