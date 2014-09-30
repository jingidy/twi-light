package com.flukiness.twilight.activities;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.flukiness.twilight.R;
import com.flukiness.twilight.fragments.ComposeDialog;
import com.flukiness.twilight.utils.TwitterApplication;
import com.flukiness.twilight.utils.TwitterClient;
import com.flukiness.twilight.adapters.TweetArrayAdapter;
import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.utils.EndlessScrollingListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

public class TimelineActivity extends FragmentActivity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        lvTweets = (ListView)findViewById(R.id.lvTweets);

        client = TwitterApplication.getRestClient();
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);

        lvTweets.setOnScrollListener(new EndlessScrollingListener() {
            @Override
            public boolean onLoadMore(int page, int totalCount) {
                Tweet t = aTweets.getOldest();
                populateTimeline(0, t != null ? t.getUid() - 1 : 0);
                return true;
            }
        });

        populateTimeline(0, 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_compose) {
            showComposeDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void populateTimeline(long greaterThanId, long lessOrEqToId) {
        client.getHomeTimeline(greaterThanId, lessOrEqToId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                aTweets.addAll(Tweet.fromJsonArray(jsonArray));
            }

            @Override
            public void onFailure(Throwable e, String s) {
                //TODO: Better error handling
                Log.d("DEBUG", e.toString());
            }
        });
    }

    public void showComposeDialog () {
        FragmentManager fm = getSupportFragmentManager();
        ComposeDialog dialog = ComposeDialog.newInstance();
        dialog.show(fm, "fragment_compose");
    }
}
