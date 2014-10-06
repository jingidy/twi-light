package com.flukiness.twilight.utils;

import org.json.JSONArray;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1"; // base API URL
	public static final String REST_CONSUMER_KEY = "DvYoz8OZ5qcVnnf3mVhtmV3j4";
	public static final String REST_CONSUMER_SECRET = "kJLCwg27Fkxo9pC82n3zPw1bRjAItfGjP9us97lWmyI7lmZQ46"; // FIXME: GAH! NOOO!!!
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets";

    private static final int userListSizeLimit = 100;

    public static enum TimelineType {
        HomeTimeline,
        MentionsTimeline,
        UserTimeline
    }

    public static enum UsersListType {
        Followers,
        Following
    }

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

    public void getTimeline(TimelineType type, long greaterThanId, long lessOrEqId,long uid,  AsyncHttpResponseHandler handler) {
        RequestParams params = null;
        if (greaterThanId != 0 || lessOrEqId != 0 || uid != 0) {
            params = new RequestParams();
            if (greaterThanId != 0) {
                params.put("since_id", String.valueOf(greaterThanId));
            }
            if (lessOrEqId != 0) {
                params.put("max_id", String.valueOf(lessOrEqId));
            }
            if (uid != 0) {
                params.put("user_id", String.valueOf(uid));
            }
        }

        switch(type) {
            case HomeTimeline:
                getHomeTimeline(params, handler);
                break;
            case MentionsTimeline:
                getMentionsTimeline(params, handler);
                break;
            case UserTimeline:
                getUserTimeline(params, handler);
                break;
        }
    }

    public void pushUpdate(String status, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", status);
        client.post(apiUrl, params, handler);
    }

    private void getHomeTimeline(RequestParams params, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        client.get(apiUrl, params, handler);
    }

    private void getMentionsTimeline(RequestParams params, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        client.get(apiUrl, params, handler);
    }

    private void getUserTimeline(RequestParams params, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        client.get(apiUrl, params, handler);
    }

    public void getMyInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        client.get(apiUrl, null, handler);
    }

    public void getUserInfo(long uid, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("user_id", String.valueOf(uid));
        client.get(apiUrl, params, handler);
    }

    public void getUsersForType(UsersListType type, long fromUid, long cursor, AsyncHttpResponseHandler handler) {
        switch (type) {
            case Followers:
                getFollowers(fromUid, cursor, handler);
                break;
            case Following:
                getFollowing(fromUid, cursor, handler);
                break;
        }
    }

    private void getFollowing(long fromUid, long cursor, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("friends/list.json");
        RequestParams params = new RequestParams();
        params.put("user_id", String.valueOf(fromUid));
        if (cursor != 0) {
            params.put("cursor", String.valueOf(cursor));
        }
        client.get(apiUrl, params, handler);
    }

    private void getFollowers(long fromUid, long cursor, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("followers/list.json");
        RequestParams params = new RequestParams();
        params.put("user_id", String.valueOf(fromUid));
        if (cursor != 0) {
            params.put("cursor", String.valueOf(cursor));
        }
        client.get(apiUrl, params, handler);
    }
}