package com.flukiness.twilight.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jing Jin on 9/28/14.
 */
public class Tweet {
    private String body;
    private long uid;
    private String createdAt;
    private User user;

    public static Tweet fromJson(JSONObject json) {
        Tweet t = new Tweet();

        try {
            t.body = json.getString("text"); // TODO: Use static vars for keys
            t.uid = json.getLong("id");
            t.createdAt = json.getString("created_at");
            t.user = User.fromJson(json.getJSONObject("user"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return t;
    }

    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
        int length = jsonArray.length();
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(length);

        for (int i = 0; i < length; i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

            Tweet t = Tweet.fromJson(tweetJson);
            if (t != null) {
                tweets.add(t);
            }
        }

        return tweets;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return getBody() + " - " + getUser().getScreenName();
    }
}
