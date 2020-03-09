package com.ship.ship_app.service;

import com.github.fabiomaffioletti.firebase.exception.FirebaseRepositoryException;
import com.ship.ship_app.model.Ship;
import com.ship.ship_app.service.ports.UnipilDecoder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.*;

@Service
public class ShipManager implements InitializingBean {
    private ShipRepository shipRepository;
    private UnipilDecoder unipilDecoder;
    private NotificationSender notificationSender;


    private List<Ship> lastUpdatedShipList = new ArrayList<>();


    @Autowired
    public ShipManager(ShipRepository shipRepository, UnipilDecoder unipilDecoder, NotificationSender notificationSender) {
        this.shipRepository = shipRepository;
        this.unipilDecoder = unipilDecoder;
        this.notificationSender = notificationSender;
    }

    public void setInitialList() {
        lastUpdatedShipList = shipRepository.findAll();
    }

    public List<Ship> getLastUpdatedShipList() {
        return lastUpdatedShipList;
    }

    private Map<String, String> getTokensFromFirebase(String id) {
        Map<String, String> tokensMap = new HashMap<>();
        try {
            tokensMap.putAll(shipRepository.get(id).getTokens());
        } catch (FirebaseRepositoryException | HttpClientErrorException e) {
            System.out.println(" nie znalazł statku o takim ID ... ");
        }
        return tokensMap;
    }


    public void updateAllChanges() throws Exception {

        List<Ship> newShips = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfNewShips);
        List<Ship> outdatedShips = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfOutdatedShips);
        for (Ship outdatedShip : outdatedShips) {
            System.out.println("removed: " + outdatedShip.getName());
            shipRepository.remove(outdatedShip.getId());
        }
        lastUpdatedShipList.removeAll(outdatedShips);

        for (Ship newShip : newShips) {
            shipRepository.update(newShip);
            Map<String, String> tempTokens = getTokensFromFirebase(newShip.getId());
            if (!tempTokens.isEmpty()) {

                for (String tempToken : tempTokens.values()) {

                    notificationSender.pushFCMNotification(tempToken, newShip.getId(), newShip.getDate(), newShip.getInfo(), newShip.getTime());

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
        List<Ship> actualShipList = unipilDecoder.getShipList();
        ChangesInShips changesInShips = new ChangesInShips();

        for (Ship ship : actualShipList) {

            if (!lastUpdatedShipList.contains(ship)) {  //

                ship.setTokens(getTokensFromFirebase(ship.getId()));

                changesInShips.listOfNewShips.add(ship);
            }
        }
        for (Ship ship : lastUpdatedShipList) {
            if (actualShipList.contains(ship)) {
                changesInShips.listOfUnchangedShips.add(ship);
            } else {
                changesInShips.listOfOutdatedShips.add(ship);
            }
        }

        for (Ship ship : lastUpdatedShipList) {
            if (!actualShipList.contains(ship)) {
                changesInShips.listOfOutdatedShips.add(ship);
            }
        }
        return changesInShips;
    }

    static private class ChangesInShips {
        Set<Ship> listOfNewShips = new HashSet<>();
        Set<Ship> listOfOutdatedShips = new HashSet<>();
        Set<Ship> listOfUnchangedShips = new HashSet<>();
    }
}
