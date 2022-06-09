package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import androidx.versionedparcelable.ParcelField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String pic_url;

    public String id;

    // empty constructor needed by the Parceler library
    public Tweet(){}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        // tweet.body = jsonObject.getString("text");
        if (jsonObject.has("full_text")) {
            tweet.body = jsonObject.getString("full_text");
        }
        else if (jsonObject.has("extended_text")) {
            tweet.body = jsonObject.getString("extended_text");
        }
        else {
            tweet.body = jsonObject.getString("text");
        }
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        tweet.id = jsonObject.getString("id_str");

        if (!jsonObject.getJSONObject("entities").has("media")) {
            Log.d("Tweet", "no pictures!");
            tweet.pic_url = "none";
        }
        else {
            tweet.pic_url = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
        }

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
