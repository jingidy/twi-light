package com.flukiness.twilight.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flukiness.twilight.utils.TwitterClient;

/**
 * Created by Jing Jin on 10/2/14.
 */
public class HomeTimelineListFragment extends TweetsTimelineListFragment {

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
        return TwitterClient.TimelineType.HomeTimeline;
    }
}
