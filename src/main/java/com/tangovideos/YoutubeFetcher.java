package com.tangovideos;

import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import java.io.File;
import java.nio.file.Files;


public class YoutubeFetcher {
    final static String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    final static String API_TOKEN = "AIzaSyCW22iBpph-9DMs3rpHa3iXZDpTV0qsLCU";

    public static JSONObject getVideoInfo(String id) {
        try {
            String response = "";
            File file = new File("cache/" + id + ".json");
            if (file.exists()) {
                response = new String(Files.readAllBytes(file.toPath()));

            } else {
                Client client = ClientBuilder.newClient();
                WebTarget target = client.target(BASE_URL).path("videos")
                        .queryParam("key", API_TOKEN)
                        .queryParam("id", id)
                        .queryParam("part", "snippet,statistics,status,topicDetails");
                response = target.request("application/json").get(new GenericType<>(String.class));
                Files.write(file.toPath(), response.getBytes());

            }


            return new JSONObject(response);
        } catch (Exception e) {
            throw new RuntimeException("fixme");
        }
    }
}
