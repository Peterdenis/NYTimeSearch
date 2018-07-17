package com.example.peterson.newyorktimesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Peterson on 7/16/2018.
 */

public class Article implements Serializable {

    String webUrl;
    String headLine;
    String thumNail;

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadLine() {
        return headLine;
    }

    public String getThumNail() {
        return thumNail;
    }

    public Article(JSONObject jsonObject) {
        try {
            this.webUrl = jsonObject.getString("web_url");
            this.headLine = jsonObject.getJSONObject("headline").getString("main");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");

            if (multimedia.length() > 0) {
                JSONObject multimediJson = multimedia.getJSONObject(0);
                this.thumNail = "http://www.nytimes.com/" + multimediJson.getString("url");
            } else {
                this.thumNail = "";
            }
        }catch (JSONException e) {

        }
    }

    public static ArrayList<Article> fromJSOArray(JSONArray array) {
        ArrayList<Article> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Article(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
