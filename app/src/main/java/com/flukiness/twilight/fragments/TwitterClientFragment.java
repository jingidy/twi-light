package com.flukiness.twilight.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.flukiness.twilight.R;
import com.flukiness.twilight.adapters.TweetArrayAdapter;
import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.models.User;
import com.flukiness.twilight.utils.EndlessScrollingListener;
import com.flukiness.twilight.utils.TwitterApplication;
import com.flukiness.twilight.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public abstract class TwitterClientFragment extends Fragment {
    private static TwitterClient client;
    private ProgressBar progressBar;

    private ListView lvList;

    public static TwitterClient getClient() {
        return client;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        lvList = (ListView)v.findViewById(R.id.lvList);
        return v;
    }

    /*
     * Shows the indeterminate progress bar in the footer.
     */
    public void showProgressBar() {
        if (progressBar == null) {
            progressBar = new ProgressBar(getActivity());
            progressBar.setIndeterminate(true);

        }
        if (lvList.getFooterViewsCount() == 0) {
            lvList.addFooterView(progressBar);
        }
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressBar() {
        if (progressBar == null) {
            return;
        }
        progressBar.setVisibility(View.GONE);
    }

    public ListView getLvList() {
        return lvList;
    }
}
