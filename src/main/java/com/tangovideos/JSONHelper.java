package com.tangovideos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {
    public static JSONObject getSnippet(JSONObject data){
        try {
            final JSONArray items = (JSONArray) data.get("items");
            final JSONObject item = (JSONObject) items.get(0);
            return (JSONObject) item.get("snippet");
        } catch (JSONException e) {
            throw new RuntimeException("fixme");
        }
    }

    public static String getTitle(JSONObject jsonObject) {
        try {
            return getSnippet(jsonObject).getString("title");
        } catch (JSONException e) {
            throw new RuntimeException("fixme");
        }


    }
}
