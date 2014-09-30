package com.flukiness.twilight.utils;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
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

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// TODO: Remove this
	// DEFINE METHODS for different API endpoints here
//	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
//		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
//		// Can specify query string params directly or through RequestParams.
//		RequestParams params = new RequestParams();
//		params.put("format", "json");
//		client.get(apiUrl, params, handler);
//	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

    public void getHomeTimeline(long greaterThanId, long lessOrEqId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");

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

        client.get(apiUrl, params, handler);
    }

    public void pushUpdate(String status, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", status);
        client.post(apiUrl, params, handler);
    }
}