package com.ship.ship_app.gdansk.service;

import com.ship.ship_app.gdansk.model.ShipGdansk;

import java.io.IOException;
import java.util.List;

public interface DecoderInterface {
 public List<ShipGdansk> getShipList() throws IOException;
}
