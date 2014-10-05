package com.flukiness.twilight.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.flukiness.twilight.R;
import com.flukiness.twilight.models.User;
import com.flukiness.twilight.utils.TwitterApplication;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
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
        });
    }

    private void populateProfileHeader(User u) {
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        tvName.setText(u.getName());
        tvTagline.setText(u.getDescription());
        tvFollowers.setText(u.getNumFollowers() + getString(R.string.posfix_followers_label));
        tvFollowing.setText(u.getNumFollowing() + getString(R.string.posfix_following_label));

        ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
    }
}
