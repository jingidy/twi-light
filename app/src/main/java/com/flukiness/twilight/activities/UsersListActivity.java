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

public abstract  class UsersListActivity extends FragmentActivity {
    public static final String USER_KEY = "user";
    public static final String TYPE_INDEX_KEY = "type_index";

    private User user;

    public abstract TwitterClient.UsersListType getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        user = (User)getIntent().getParcelableExtra(ProfileActivity.USER_KEY);

        // Set up a user list fragment, then load it into the view.
        UsersListFragment fragmentUsersList = new UsersListFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_KEY, user);
        args.putInt(TYPE_INDEX_KEY, getType().ordinal());
        fragmentUsersList.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flUsers, fragmentUsersList);
        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
