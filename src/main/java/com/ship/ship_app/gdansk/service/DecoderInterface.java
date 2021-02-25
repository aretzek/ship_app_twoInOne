package com.ship.ship_app.service.ports;

import com.ship.ship_app.model.Ship;

import java.io.IOException;
import java.util.List;

public interface DecoderInterface {
 public List<Ship> getShipList() throws IOException;
}
