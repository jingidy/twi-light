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
public class MentionsTimelineFragment extends TweetsListFragment {

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
}
