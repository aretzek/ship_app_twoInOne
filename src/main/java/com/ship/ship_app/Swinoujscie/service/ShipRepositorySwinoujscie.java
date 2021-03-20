package com.ship.ship_app.Swinoujscie.service;

import com.github.fabiomaffioletti.firebase.repository.DefaultFirebaseRealtimeDatabaseRepository;
import com.ship.ship_app.Swinoujscie.model.ShipSwinoujscie;
import org.springframework.stereotype.Repository;

@Repository
public class ShipRepositorySwinoujscie extends DefaultFirebaseRealtimeDatabaseRepository<ShipSwinoujscie, String> {
}
