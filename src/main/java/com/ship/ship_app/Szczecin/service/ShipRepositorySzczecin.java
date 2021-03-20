package com.ship.ship_app.Szczecin.service;

import com.github.fabiomaffioletti.firebase.repository.DefaultFirebaseRealtimeDatabaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ShipRepositorySzczecin extends DefaultFirebaseRealtimeDatabaseRepository<ShipSzczecin, String> {
}
