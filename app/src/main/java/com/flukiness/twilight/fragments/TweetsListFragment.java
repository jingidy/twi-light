package com.flukiness.twilight.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.flukiness.twilight.R;
import com.flukiness.twilight.adapters.TweetArrayAdapter;
import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.utils.EndlessScrollingListener;
import com.flukiness.twilight.utils.TwitterApplication;
import com.flukiness.twilight.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public abstract class TweetsListFragment extends Fragment {
    private static TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetArrayAdapter aTweets;
    private ListView lvTweets;

    public static TwitterClient getClient() {
        return client;
    }

    public abstract TwitterClient.TimelineType getTimelineType();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        tweets = new ArrayList<Tweet>();
        aTweets = new TweetArrayAdapter(getActivity(), tweets);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView)v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);

        lvTweets.setOnScrollListener(new EndlessScrollingListener() {
            @Override
            public boolean onLoadMore(int page, int totalCount) {
                Tweet t = aTweets.getOldest();
                populateTimeline(0, t != null ? t.getUid() - 1 : 0);
                return true;
            }
        });

        if (aTweets.getCount() == 0) { // First load
            populateTimeline(0, 0);
        }

        return v;
    }

    public void addAll(ArrayList<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void populateTimeline(final long greaterThanId, final long lessOrEqToId) {
        getClient().getTimeline(getTimelineType(), greaterThanId, lessOrEqToId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                if (greaterThanId != 0) { // tack on new tweets
                    //TODO: Implement pull to refresh
                } else if (lessOrEqToId != 0) { // tack on old tweets
                    aTweets.addAll(Tweet.fromJsonArray(jsonArray));
                } else { // Doing a fresh load
                    aTweets.clear();
                    aTweets.addAll(Tweet.fromJsonArray(jsonArray));
                }
            }

            @Override
            public void onFailure(Throwable e, String s) {
                //TODO: Better error handling
                Log.d("DEBUG", e.toString());
            }
        });
    }
}
