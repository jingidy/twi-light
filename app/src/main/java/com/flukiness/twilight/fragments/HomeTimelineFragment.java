package com.flukiness.twilight.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.utils.TwitterApplication;
import com.flukiness.twilight.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

/**
 * Created by Jing Jin on 10/2/14.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        //TODO: Uncomment
//        lvTweets.setOnScrollListener(new EndlessScrollingListener() {
//            @Override
//            public boolean onLoadMore(int page, int totalCount) {
//                Tweet t = aTweets.getOldest();
//                populateTimeline(0, t != null ? t.getUid() - 1 : 0);
//                return true;
//            }
//        });
        // FIXME: Don't duplicate content when coming back to this tab
        populateTimeline(0, 0);
        return v;
    }

    public void populateTimeline(long greaterThanId, long lessOrEqToId) {
        getClient().getHomeTimeline(greaterThanId, lessOrEqToId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                addAll(Tweet.fromJsonArray(jsonArray));
            }

            @Override
            public void onFailure(Throwable e, String s) {
                //TODO: Better error handling
                Log.d("DEBUG", e.toString());
            }
        });
    }
}
