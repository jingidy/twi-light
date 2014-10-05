package com.flukiness.twilight.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.utils.TwitterClient;

/**
 * Created by Jing Jin on 10/2/14.
 */
public class MentionsTimelineFragment extends TweetsTimelineFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }

    @Override
    public TwitterClient.TimelineType getTimelineType() {
        return TwitterClient.TimelineType.MentionsTimeline;
    }

    @Override
    public void onTweetPosted(Tweet t) {
        // Only update if the tweet mentions the user
        Log.d("debug", "username = " + t.getUser().getScreenName());
        if (t.mentionsUser(t.getUser().getScreenName())) {
            super.onTweetPosted(t);
        }
    }
}
