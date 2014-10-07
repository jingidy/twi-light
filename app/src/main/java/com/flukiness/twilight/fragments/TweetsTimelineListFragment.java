package com.flukiness.twilight.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flukiness.twilight.adapters.TweetArrayAdapter;
import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.models.User;
import com.flukiness.twilight.utils.EndlessScrollingListener;
import com.flukiness.twilight.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public abstract class TweetsTimelineListFragment extends TwitterClientListFragment {
    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;

    public abstract TwitterClient.TimelineType getTimelineType();

    public User getUserToShow() {
        // Sublcasses and override to show only 1 user's timeline
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(getActivity(), tweets);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getLvList().setAdapter(aTweets);

        getLvList().setOnScrollListener(new EndlessScrollingListener() {
            @Override
            public boolean onLoadMore(int page, int totalCount) {
                Tweet t = aTweets.getOldest();
                populateTimeline(0, t != null ? t.getUid() - 1 : 0);
                return true;
            }
        });

        if (aTweets.getCount() == 0) { // First load
            populateTimeline(0, 0);
        }

        return v;
    }

    @Override
    public void refreshList() {
        populateTimeline(0, 0);
    }

    private void populateTimeline(final long greaterThanId, final long lessOrEqToId) {
        User u = getUserToShow();
        startingTwitterClientRequest();

        getClient().getTimeline(getTimelineType(), greaterThanId, lessOrEqToId, u == null ? 0 : u.getUid(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                if (isRefreshing()) {
                    aTweets.clear();
                }
                aTweets.addAll(Tweet.fromJsonArray(jsonArray));
                stoppedTwitterClientRequest();
            }

            @Override
            public void onFailure(Throwable e, String s) {
                //TODO: Better error handling
                Log.d("DEBUG", e.toString());
                stoppedTwitterClientRequest();
            }
        });
    }

    public void onTweetPosted(Tweet t) {
        getLvList().smoothScrollToPosition(0);
        aTweets.insert(t, 0);
    }
}
