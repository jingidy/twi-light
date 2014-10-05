package com.flukiness.twilight.models;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jing Jin on 9/28/14.
 */
public class User {
    public static final String userPrefix = "@";

    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String description;
    private int numFollowers;
    private int numFollowing;

    public static User fromJson(JSONObject json) {
        User u = new User();
        try {
            u.name = json.getString("name"); //TODO: Use static vars for keys
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");

            if (json.has("description")) {
                u.description = json.getString("description");
            }
            if (json.has("followers_count")) {
                u.numFollowers = json.getInt("followers_count");
            }
            if (json.has("friends_count")) {
                u.numFollowing = json.getInt("friends_count");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return u;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getScreenNameUIString() {
        return userPrefix + screenName;
    }

    public int getNumFollowing() {
        return numFollowing;
    }

    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }
}
