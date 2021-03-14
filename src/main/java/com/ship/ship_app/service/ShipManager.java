package com.ship.ship_app.service;

import com.github.fabiomaffioletti.firebase.exception.FirebaseRepositoryException;
import com.ship.ship_app.model.Ship;
import com.ship.ship_app.controller.NotificationSender;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class ShipManager implements InitializingBean {
    private ShipRepository shipRepository;
    private DecoderGdansk decoderGdansk;
    private DecoderGdynia decoderGdynia;
    private NotificationSender notificationSender;
    private List<Ship> lastUpdatedShipList = new ArrayList<>();



    @Autowired
    public ShipManager(ShipRepository shipRepository, DecoderGdansk decoderGdansk,DecoderGdynia decoderGdynia, NotificationSender notificationSender) {
        this.shipRepository = shipRepository;
        this.decoderGdansk = decoderGdansk;
        this.decoderGdynia = decoderGdynia;
        this.notificationSender = notificationSender;
    }

    public void setInitialList() {
        lastUpdatedShipList = shipRepository.findAll();
    }

    public List<Ship> getLastUpdatedShipList() {
        return lastUpdatedShipList;
    }

    private List<String> getTokensFromFirebase(String id) {
        List<String> tokensList = new ArrayList<>();
        try {
            tokensList.addAll(shipRepository.get(id).getTokens());
        } catch (FirebaseRepositoryException | HttpClientErrorException e) {
            System.out.println(" nie znalazłem statku o takim ID ... ");
        }
        return tokensList;
    }


    public void updateAllChanges() throws Exception {

        List<Ship> newShips = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfNewShip);
        List<Ship> outdatedShips = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfOutdatedShip);
        for (Ship outdatedShip : outdatedShips) {
            System.out.println("removed: " + outdatedShip.getName());
            shipRepository.remove(outdatedShip.getId());
        }
        lastUpdatedShipList.removeAll(outdatedShips);

        for (Ship newShip : newShips) {
            shipRepository.push(newShip);    //zmieniłem z update
            List<String> tempTokens = getTokensFromFirebase(newShip.getId());
            if (!tempTokens.isEmpty()) {

                for (String tempToken : tempTokens) {

                    notificationSender.pushFCMNotification(tempToken, newShip.getName(), newShip.getDate(), newShip.getInfo(), newShip.getTime());

                    System.out.println(tempToken);
                }
            }

        }
        lastUpdatedShipList.addAll(newShips);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    private ChangesInShips findDifferencesBetweenLastUpdatedListAndActualList() throws Exception {
        List<Ship> actualShipList = decoderGdansk.getShipList();
        actualShipList.addAll(decoderGdynia.getShipsList());
        ChangesInShips changesInShips = new ChangesInShips();

        for (Ship ship : actualShipList) {

            if (!lastUpdatedShipList.contains(ship)) {  //
                ship.setTokens(getTokensFromFirebase(ship.getId()));
                changesInShips.listOfNewShip.add(ship);
            }
        }
        for (Ship ship : lastUpdatedShipList) {
            if (actualShipList.contains(ship)) {
                changesInShips.listOfUnchangedShip.add(ship);
            } else {
                changesInShips.listOfOutdatedShip.add(ship);
            }
        }

        for (Ship ship : lastUpdatedShipList) {
            if (!actualShipList.contains(ship)) {
                changesInShips.listOfOutdatedShip.add(ship);
            }
        }
        return changesInShips;
    }

    static private class ChangesInShips {
        Set<Ship> listOfNewShip = new HashSet<>();
        Set<Ship> listOfOutdatedShip = new HashSet<>();
        Set<Ship> listOfUnchangedShip = new HashSet<>();
    }
}
