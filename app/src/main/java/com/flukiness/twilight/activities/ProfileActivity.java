package com.flukiness.twilight.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.flukiness.twilight.R;
import com.flukiness.twilight.fragments.ComposeDialogFragment;
import com.flukiness.twilight.fragments.ProfileTimelineListFragment;
import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.models.User;
import com.flukiness.twilight.utils.TwitterApplication;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

public class ProfileActivity extends FragmentActivity implements ComposeDialogFragment.ComposeDialogListener {

    public static final String USER_KEY = "user";

    private User user;
    ProfileTimelineListFragment fragmentUserTimeline;
    JsonHttpResponseHandler userRequestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = (User)getIntent().getParcelableExtra(USER_KEY);

        // Set up a user timeline fragment with the user ID, then load it into th eview.
        fragmentUserTimeline = new ProfileTimelineListFragment();
        fragmentUserTimeline.setUser(user);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flUserTimeline, fragmentUserTimeline);
        ft.commit();

        userRequestHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                User u = User.fromJson(json);
                getActionBar().setTitle(u.getScreenName());
                populateProfileHeader(u);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                Log.d("debug", s);
                super.onFailure(throwable, s);
            }
        };

        loadProfileInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
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

    private void loadProfileInfo() {
        if (user == null) { // Get self profile
            TwitterApplication.getRestClient().getMyInfo(userRequestHandler);
        } else {
            TwitterApplication.getRestClient().getUserInfo(user.getUid(), userRequestHandler);
        }
    }


    private void populateProfileHeader(User u) {
        user = u;
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        Button btnFollowers = (Button) findViewById(R.id.btnFollowers);
        Button btnFollowing = (Button) findViewById(R.id.btnFollowing);

        tvName.setText(u.getName());
        tvTagline.setText(u.getDescription());
        btnFollowers.setText(u.getNumFollowers() + getString(R.string.posfix_followers_label));
        btnFollowing.setText(u.getNumFollowing() + getString(R.string.posfix_following_label));

        btnFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFollowers();
            }
        });
        btnFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFollowing();
            }
        });

        ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
    }

    public void showFollowers() {
        Intent i = new Intent(this, FollowersActivity.class);
        i.putExtra(USER_KEY, this.user);
        startActivity(i);

    }

    public void showFollowing() {
        Intent i = new Intent(this, FollowingActivity.class);
        i.putExtra(USER_KEY, this.user);
        startActivity(i);
    }

    @Override
    public void onTweetPosted(Tweet t) {
        fragmentUserTimeline.onTweetPosted(t);
    }
}
