package com.flukiness.twilight.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jing Jin on 9/28/14.
 */
public class User implements Parcelable {
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

    public static ArrayList<User> fromJsonArray(JSONArray jsonArray) {
        int length = jsonArray.length();
        ArrayList<User> users = new ArrayList<User>(length);

        for (int i = 0; i < length; i++) {
            JSONObject userJson = null;
            try {
                userJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

            User u = User.fromJson(userJson);
            if (u != null) {
                users.add(u);
            }
        }

        return users;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.uid);
        dest.writeString(this.screenName);
        dest.writeString(this.profileImageUrl);
        dest.writeString(this.description);
        dest.writeInt(this.numFollowers);
        dest.writeInt(this.numFollowing);
    }

    public User() {
    }

    private User(Parcel in) {
        this.name = in.readString();
        this.uid = in.readLong();
        this.screenName = in.readString();
        this.profileImageUrl = in.readString();
        this.description = in.readString();
        this.numFollowers = in.readInt();
        this.numFollowing = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
