package com.ship.ship_app.service.ports;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Service
public class SelfPinger {


    URLConnection connection;

    public void ping() {
    }

    {
        try {

            connection = new URL("https://serene-depths-36885.herokuapp.com/").openConnection();

            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


