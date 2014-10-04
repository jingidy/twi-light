package com.flukiness.twilight.utils;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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

    public enum TimelineType {
        HomeTimeline,
        MentionsTimeline
    }

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

    public void getTimeline(TimelineType type, long greaterThanId, long lessOrEqId, AsyncHttpResponseHandler handler) {
        RequestParams params = null;
        if (greaterThanId != 0 || lessOrEqId != 0) {
            params = new RequestParams();
            if (greaterThanId != 0) {
                params.put("since_id", String.valueOf(greaterThanId));
            }
            if (lessOrEqId != 0) {
                params.put("max_id", String.valueOf(lessOrEqId));
            }
        }

        switch(type) {
            case HomeTimeline:
                getHomeTimeline(params, handler);
                break;
            case MentionsTimeline:
                getMentionsTimeline(params, handler);
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
}