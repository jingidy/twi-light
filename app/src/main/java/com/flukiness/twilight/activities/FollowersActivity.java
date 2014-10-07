package com.flukiness.twilight.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import com.flukiness.twilight.R;
import com.flukiness.twilight.fragments.UsersListFragment;
import com.flukiness.twilight.models.User;
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
