package com.flukiness.twilight.activities;

import android.os.Bundle;

import com.flukiness.twilight.R;
import com.flukiness.twilight.utils.TwitterClient;

public class FollowingActivity extends UsersListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.following_title);
    }

    @Override
    public TwitterClient.UsersListType getType() {
        return TwitterClient.UsersListType.Following;
    }
}
