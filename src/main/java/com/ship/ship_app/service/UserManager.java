package com.ship.ship_app.service;


import com.ship.ship_app.model.User;
import com.ship.ship_app.service.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class UserManager implements InitializingBean {

    UserRepository userRepository;
    List<User> usersList;


    public UserManager(UserRepository userRepository, List<User> usersList) {
        this.userRepository = userRepository;
        this.usersList = usersList;
    }


    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public HttpStatus createNewUser(String login, String password, String email) {
        userRepository.get(login);
        if (userRepository.get(login).getLogin().isEmpty()) {   //ta metoda do porprawy bo gdy nie znajdzie uzytkownika w repo,to wyjebie błąd
            User newUsuer = new User(login, password, email);
            userRepository.push(newUsuer);

            return HttpStatus.CREATED;

        } else {
            return HttpStatus.IM_USED;
        }
    }

    public User getUser(String login) {
        return userRepository.get(login);
    }

    public void addFavoriteShip(String login, String shipId){
        User tempUser = userRepository.get(login);
       Set tempFavoritesShipList = userRepository.get(login).getFavoriteShipList();
       tempFavoritesShipList.add(shipId);
       tempUser.setFavoriteShipList(tempFavoritesShipList);
       userRepository.update(tempUser);
    }

    public Set<String> getFavoritesShipList (String id) {
        return userRepository.get(id).getFavoriteShipList();

    }





}
