package com.flukiness.twilight.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.flukiness.twilight.R;
import com.flukiness.twilight.utils.TwitterApplication;
import com.flukiness.twilight.utils.TwitterClient;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public abstract class TwitterClientListFragment extends Fragment {
    private static TwitterClient client;
    private ProgressBar progressBar;

    private ListView lvList;
    private SwipeRefreshLayout swipeContainer;

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
        lvList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        swipeContainer = (SwipeRefreshLayout)v.findViewById(R.id.swipeContainer);
        swipeContainer.setColorScheme(R.color.blue,R.color.pink, R.color.yellow,R.color.red);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });

        return v;
    }

    public abstract void refreshList();

    public boolean isRefreshing() {
        return swipeContainer.isRefreshing();
    }

    /*
     * Subclasses should call these in pairs.
     */
    public void startingTwitterClientRequest() {
        // Only show progress bar if we're not in pull to refresh
        if (!swipeContainer.isRefreshing()) {
            if (progressBar == null) {
                progressBar = new ProgressBar(getActivity());
                progressBar.setIndeterminate(true);

            }
            if (lvList.getFooterViewsCount() == 0) {
                lvList.addFooterView(progressBar);
            }
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void stoppedTwitterClientRequest() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        swipeContainer.setRefreshing(false);
    }

    public ListView getLvList() {
        return lvList;
    }
}
