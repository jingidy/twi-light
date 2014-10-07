package com.flukiness.twilight.activities;

import android.os.Bundle;

import com.flukiness.twilight.R;
import com.flukiness.twilight.utils.TwitterClient;

public class FollowersActivity extends UsersListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.followers_title);
    }

    @Override
    public TwitterClient.UsersListType getType() {
        return TwitterClient.UsersListType.Followers;
    }
}
