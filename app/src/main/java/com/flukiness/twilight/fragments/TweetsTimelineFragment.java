package com.flukiness.twilight.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.flukiness.twilight.R;
import com.flukiness.twilight.adapters.TweetArrayAdapter;
import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.models.User;
import com.flukiness.twilight.utils.EndlessScrollingListener;
import com.flukiness.twilight.utils.TwitterApplication;
import com.flukiness.twilight.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public abstract class TweetsTimelineFragment extends TwitterClientFragment {
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

    public void populateTimeline(final long greaterThanId, final long lessOrEqToId) {
        User u = getUserToShow();
        showProgressBar();
        getClient().getTimeline(getTimelineType(), greaterThanId, lessOrEqToId, u == null ? 0 : u.getUid(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                if (greaterThanId != 0) { // tack on new tweets
                    //TODO: Implement pull to refresh
                } else if (lessOrEqToId != 0) { // tack on old tweets
                    aTweets.addAll(Tweet.fromJsonArray(jsonArray));
                } else { // Doing a fresh load
                    aTweets.clear();
                    aTweets.addAll(Tweet.fromJsonArray(jsonArray));
                }
                hideProgressBar();
            }

            @Override
            public void onFailure(Throwable e, String s) {
                //TODO: Better error handling
                Log.d("DEBUG", e.toString());
                hideProgressBar();
            }
        });
    }

    public void onTweetPosted(Tweet t) {
        getLvList().smoothScrollToPosition(0);
        aTweets.insert(t, 0);
    }
}
