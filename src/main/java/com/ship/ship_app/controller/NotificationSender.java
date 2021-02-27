package com.ship.ship_app.controller;



import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class NotificationSender {


    private final static String AUTH_KEY_FCM = "AAAAKPLurzU:APA91bFvB1roZ78gm1jPAfnKVLs7VPTluBSD46PDgHSQcL3p63w5ppGkuXkevIYlZRtcsnfX1FfQukY_qGHpdxlIia9AZlO1pb1MYxrTHT6ihnldp055Fmf85FNgO_bW3bTDZsqtAuxj";
    private final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public void pushFCMNotification(String DeviceIdKey, String shipId, String shipDate, String shipInfo, String shipTime) throws Exception {

        String authKey = AUTH_KEY_FCM;
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");
        JSONObject data = new JSONObject();
        data.put("to", DeviceIdKey.trim());
        JSONObject info = new JSONObject();
        info.put("title", "Ship_App Powiadomienie"); // Notification title
        info.put("body",shipId+" "+shipDate+"\n"+shipTime+shipInfo);
        data.put("notification", info);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data.toString());
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);

        }
        in.close();

    }

}
