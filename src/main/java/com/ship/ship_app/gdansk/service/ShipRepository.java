package com.ship.ship_app.gdansk.service;

import com.github.fabiomaffioletti.firebase.repository.DefaultFirebaseRealtimeDatabaseRepository;
import com.ship.ship_app.gdansk.model.ShipGdansk;
import org.springframework.stereotype.Repository;


@Repository
public class ShipRepository extends DefaultFirebaseRealtimeDatabaseRepository<ShipGdansk, String> {
}



