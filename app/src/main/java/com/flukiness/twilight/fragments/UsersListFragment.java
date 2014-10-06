package com.flukiness.twilight.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flukiness.twilight.activities.FollowersActivity;
import com.flukiness.twilight.adapters.UserArrayAdapter;
import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.models.User;
import com.flukiness.twilight.utils.EndlessScrollingListener;
import com.flukiness.twilight.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jing Jin on 10/2/14.
 */
public class UsersListFragment extends TwitterClientFragment {
    private User fromUser;
    private TwitterClient.UsersListType type;

    private ArrayList<User> users;
    private UserArrayAdapter aUsers;
    private JsonHttpResponseHandler userArrayRequestHandler;
    private long next_cursor = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = new ArrayList<User>();
        aUsers = new UserArrayAdapter(getActivity(), users);
        fromUser = (User)getArguments().getParcelable(FollowersActivity.USER_KEY);

        int typeIndex = getArguments().getInt(FollowersActivity.TYPE_INDEX_KEY);
        type = TwitterClient.UsersListType.values()[typeIndex];

        userArrayRequestHandler = new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject json) {
                updateUsersAndCursor(json);
            }

            @Override
            public void onFailure(Throwable e, String s) {
                Log.d("debug", e.toString());
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getLvList().setAdapter(aUsers);

        getLvList().setOnScrollListener(new EndlessScrollingListener() {
            @Override
            public boolean onLoadMore(int page, int totalCount) {
                showMoreUsers();
                return true;
            }
        });

        showUsers();

        return v;
    }

    public void showUsers() {
        aUsers.clear();
        next_cursor = -1;
        showMoreUsers();
    }

    public void showMoreUsers() {
        showProgressBar();
        getClient().getUsersForType(type, fromUser.getUid(), next_cursor, userArrayRequestHandler);
    }

    private void updateUsersAndCursor(JSONObject json) {
        hideProgressBar();
        try {
            if (json.has("next_cursor")) {
                next_cursor = json.getLong("next_cursor");
            }
            aUsers.addAll(User.fromJsonArray(json.getJSONArray("users")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
