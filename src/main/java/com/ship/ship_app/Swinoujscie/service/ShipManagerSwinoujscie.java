package com.ship.ship_app.Swinoujscie.service;

import com.github.fabiomaffioletti.firebase.exception.FirebaseRepositoryException;
import com.ship.ship_app.Swinoujscie.model.ShipSwinoujscie;
import com.ship.ship_app.controller.NotificationSender;
import com.ship.ship_app.gdynia.model.ShipGdynia;
import com.ship.ship_app.gdynia.service.ShipManagerGdynia;
import com.ship.ship_app.gdynia.service.ShipRepositoryGdynia;
import com.ship.ship_app.gdynia.service.UnipilDecoderGdynia;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class ShipManagerSwinoujscie implements InitializingBean {
    private ShipRepositorySwinoujscie shipRepositorySwinoujscie;
    private NotificationSender notificationSender;
    private SwinoujscieDecoder swinoujscieDecoder;

    private List<ShipSwinoujscie> lastUpdatedSwinoujscieList = new ArrayList<>();

    @Autowired
    public ShipManagerSwinoujscie(ShipRepositorySwinoujscie shipRepositorySwinoujscie, NotificationSender notificationSender, SwinoujscieDecoder swinoujscieDecoder) {
        this.shipRepositorySwinoujscie = shipRepositorySwinoujscie;
        this.notificationSender = notificationSender;
        this.swinoujscieDecoder = swinoujscieDecoder;
    }


    public void setInitialList (){
        lastUpdatedSwinoujscieList = shipRepositorySwinoujscie.findAll();
    }

    public List<ShipSwinoujscie> getLastUpdatedShipSwinoujscieList ()
    {return lastUpdatedSwinoujscieList;}


    private Map<String, String> getTokensFromFirebase(String id) {
        Map<String, String> tokensMap = new HashMap<>();
        try {
            tokensMap.putAll(shipRepositorySwinoujscie.get(id).getTokens());
        } catch (FirebaseRepositoryException | HttpClientErrorException e) {
            System.out.println(" nie znalazł statku o takim ID ... ");
        }
        return tokensMap;
    }
    public void updateAllChanges() throws Exception {

        List<ShipSwinoujscie> newShipsSwinoujscie = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfNewShipSwinoujscie);
        List<ShipSwinoujscie> outdatedShipsSwinoujscie = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfOutdatedShipSwinoujscie);
        for (ShipSwinoujscie outdatedShipSwinoujscie : outdatedShipsSwinoujscie) {
            System.out.println("removed: " + outdatedShipSwinoujscie.getName());
            shipRepositorySwinoujscie.remove(outdatedShipSwinoujscie.getId());
        }
        lastUpdatedSwinoujscieList.removeAll(outdatedShipsSwinoujscie);

        for (ShipSwinoujscie newShipSwinoujscie : newShipsSwinoujscie) {
            shipRepositorySwinoujscie.update(newShipSwinoujscie);
            Map<String, String> tempTokens = getTokensFromFirebase(newShipSwinoujscie.getId());
            if (!tempTokens.isEmpty()) {

                for (String tempToken : tempTokens.values()) {

                    notificationSender.pushFCMNotification(tempToken, newShipSwinoujscie.getName(), newShipSwinoujscie.getDate(), newShipSwinoujscie.getInfo(), newShipSwinoujscie.getTime());

                    System.out.println(tempToken);
                }
            }

        }
        lastUpdatedSwinoujscieList.addAll(newShipsSwinoujscie);
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
    private ShipManagerSwinoujscie.ChangesInShips findDifferencesBetweenLastUpdatedListAndActualList() throws Exception {
        List<ShipSwinoujscie> actualShipGdyniaList = swinoujscieDecoder.getShipList();
        ShipManagerSwinoujscie.ChangesInShips changesInShips = new ShipManagerSwinoujscie.ChangesInShips();

        for (ShipSwinoujscie shipSwinoujscie : actualShipGdyniaList) {

            if (!lastUpdatedSwinoujscieList.contains(shipSwinoujscie)) {  //
                shipSwinoujscie.setTokens(getTokensFromFirebase(shipSwinoujscie.getId()));
                changesInShips.listOfNewShipSwinoujscie.add(shipSwinoujscie);
            }
        }
        for (ShipSwinoujscie shipGdynia : lastUpdatedSwinoujscieList) {
            if (actualShipGdyniaList.contains(shipGdynia)) {
                changesInShips.listOfUnchangedShipSwinoujscie.add(shipGdynia);
            } else {
                changesInShips.listOfOutdatedShipSwinoujscie.add(shipGdynia);
            }
        }

        for (ShipSwinoujscie shipSwinooujscie : lastUpdatedSwinoujscieList) {
            if (!actualShipGdyniaList.contains(shipSwinooujscie)) {
                changesInShips.listOfOutdatedShipSwinoujscie.add(shipSwinooujscie);
            }
        }
        return changesInShips;
    }

    static private class ChangesInShips {
        Set<ShipSwinoujscie> listOfNewShipSwinoujscie = new HashSet<>();
        Set<ShipSwinoujscie> listOfOutdatedShipSwinoujscie = new HashSet<>();
        Set<ShipSwinoujscie> listOfUnchangedShipSwinoujscie = new HashSet<>();
    }

}
