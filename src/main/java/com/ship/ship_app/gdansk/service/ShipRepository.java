package com.ship.ship_app.service;

import com.github.fabiomaffioletti.firebase.repository.DefaultFirebaseRealtimeDatabaseRepository;
import com.ship.ship_app.model.Ship;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
public class ShipRepository extends DefaultFirebaseRealtimeDatabaseRepository<Ship, String> {
}



