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
        return getSnippetInfo(jsonObject, "title");
    }
    public static String getDescription(JSONObject jsonObject) {
        return getSnippetInfo(jsonObject, "description");
    }

    private static String getSnippetInfo(JSONObject jsonObject, String description) {
        try {
            return getSnippet(jsonObject).getString(description);
        } catch (JSONException e) {
            throw new RuntimeException("fixme");
        }
    }
}
