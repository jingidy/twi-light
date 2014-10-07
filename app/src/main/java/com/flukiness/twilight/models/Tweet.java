package com.flukiness.twilight.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.flukiness.twilight.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jing Jin on 9/28/14.
 */
public class Tweet implements Parcelable {
    private static SimpleDateFormat dateParser = new SimpleDateFormat("EEE MMM d k:m:s z yyyy");
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d");
    private static long minInMilliseconds = 60 * 1000;
    private static long hourInMilliSeconds = 60 * minInMilliseconds;
    private static long dayInMilliseconds = 24 * hourInMilliSeconds;

    private String body;
    private long uid;
    private Date createdTime;
    private User user;

    public static Tweet fromJson(JSONObject json) {
        Tweet t = new Tweet();

        try {
            t.body = json.getString("text"); // TODO: Use static vars for keys
            t.uid = json.getLong("id");
            t.user = User.fromJson(json.getJSONObject("user"));

            String createdString = json.getString("created_at");
            t.createdTime = dateParser.parse(createdString);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTimeUIString(Context context) {
        long diffInMilliseconds = new Date().getTime() - createdTime.getTime();

        long daysAgo = (diffInMilliseconds) / dayInMilliseconds;
        if (daysAgo > 30) {
            return dateFormatter.format(createdTime);
        }
        if (daysAgo > 0) {
            return daysAgo + context.getString(R.string.day_abbreviation);
        }

        long hoursAgo = diffInMilliseconds / hourInMilliSeconds;
        if (hoursAgo > 0) {
            return hoursAgo + context.getString(R.string.hour_abbreviation);
        }

        long minAgo = diffInMilliseconds / minInMilliseconds;
        return minAgo + context.getString(R.string.min_abbreviation);

    }

    @Override
    public String toString() {
        return getBody() + " - " + getUser().getScreenName();
    }

    public boolean mentionsUser(String username) {
        return body == null ? false : body.matches(".*\\b\\" + User.USER_PREFIX + username + "\\b.*");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.body);
        dest.writeLong(this.uid);
        dest.writeLong(createdTime != null ? createdTime.getTime() : -1);
        dest.writeParcelable(this.user, 0);
    }

    public Tweet() {
    }

    private Tweet(Parcel in) {
        this.body = in.readString();
        this.uid = in.readLong();
        long tmpCreatedTime = in.readLong();
        this.createdTime = tmpCreatedTime == -1 ? null : new Date(tmpCreatedTime);
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }

        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}
