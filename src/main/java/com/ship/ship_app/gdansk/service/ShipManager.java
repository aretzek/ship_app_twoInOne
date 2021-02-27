package com.ship.ship_app.gdansk.service;

import com.github.fabiomaffioletti.firebase.exception.FirebaseRepositoryException;
import com.ship.ship_app.gdansk.model.ShipGdansk;
import com.ship.ship_app.controller.NotificationSender;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class ShipManager implements InitializingBean {
    private ShipRepository shipRepository;
    private UnipilDecoder unipilDecoder;
    private NotificationSender notificationSender;


    private List<ShipGdansk> lastUpdatedShipGdanskList = new ArrayList<>();


    @Autowired
    public ShipManager(ShipRepository shipRepository, UnipilDecoder unipilDecoder, NotificationSender notificationSender) {
        this.shipRepository = shipRepository;
        this.unipilDecoder = unipilDecoder;
        this.notificationSender = notificationSender;
    }

    public void setInitialList() {
        lastUpdatedShipGdanskList = shipRepository.findAll();
    }

    public List<ShipGdansk> getLastUpdatedShipGdanskList() {
        return lastUpdatedShipGdanskList;
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

        List<ShipGdansk> newShipGdansks = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfNewShipGdansks);
        List<ShipGdansk> outdatedShipGdansks = new ArrayList<>(findDifferencesBetweenLastUpdatedListAndActualList().listOfOutdatedShipGdansks);
        for (ShipGdansk outdatedShipGdansk : outdatedShipGdansks) {
            System.out.println("removed: " + outdatedShipGdansk.getName());
            shipRepository.remove(outdatedShipGdansk.getId());
        }
        lastUpdatedShipGdanskList.removeAll(outdatedShipGdansks);

        for (ShipGdansk newShipGdansk : newShipGdansks) {
            shipRepository.update(newShipGdansk);
            Map<String, String> tempTokens = getTokensFromFirebase(newShipGdansk.getId());
            if (!tempTokens.isEmpty()) {

                for (String tempToken : tempTokens.values()) {

                    notificationSender.pushFCMNotification(tempToken, newShipGdansk.getId(), newShipGdansk.getDate(), newShipGdansk.getInfo(), newShipGdansk.getTime());

                    System.out.println(tempToken);
                }
            }

        }
        lastUpdatedShipGdanskList.addAll(newShipGdansks);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    private ChangesInShips findDifferencesBetweenLastUpdatedListAndActualList() throws Exception {
        List<ShipGdansk> actualShipGdanskList = unipilDecoder.getShipList();
        ChangesInShips changesInShips = new ChangesInShips();

        for (ShipGdansk shipGdansk : actualShipGdanskList) {

            if (!lastUpdatedShipGdanskList.contains(shipGdansk)) {  //
                shipGdansk.setTokens(getTokensFromFirebase(shipGdansk.getId()));
                changesInShips.listOfNewShipGdansks.add(shipGdansk);
            }
        }
        for (ShipGdansk shipGdansk : lastUpdatedShipGdanskList) {
            if (actualShipGdanskList.contains(shipGdansk)) {
                changesInShips.listOfUnchangedShipGdansks.add(shipGdansk);
            } else {
                changesInShips.listOfOutdatedShipGdansks.add(shipGdansk);
            }
        }

        for (ShipGdansk shipGdansk : lastUpdatedShipGdanskList) {
            if (!actualShipGdanskList.contains(shipGdansk)) {
                changesInShips.listOfOutdatedShipGdansks.add(shipGdansk);
            }
        }
        return changesInShips;
    }

    static private class ChangesInShips {
        Set<ShipGdansk> listOfNewShipGdansks = new HashSet<>();
        Set<ShipGdansk> listOfOutdatedShipGdansks = new HashSet<>();
        Set<ShipGdansk> listOfUnchangedShipGdansks = new HashSet<>();
    }
}
