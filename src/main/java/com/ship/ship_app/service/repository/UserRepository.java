package com.ship.ship_app.service.repository;

import com.github.fabiomaffioletti.firebase.repository.DefaultFirebaseRealtimeDatabaseRepository;
import com.ship.ship_app.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends DefaultFirebaseRealtimeDatabaseRepository <User, String> {

}
