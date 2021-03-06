package com.flukiness.twilight.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flukiness.twilight.activities.FollowersActivity;
import com.flukiness.twilight.adapters.UserArrayAdapter;
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
public class UsersListListFragment extends TwitterClientListFragment {
    private User fromUser;
    private TwitterClient.UsersListType type;

    private ArrayList<User> users;
    private UserArrayAdapter aUsers;
    private JsonHttpResponseHandler userArrayRequestHandler;
    private long next_cursor = -1;

    //FIXME: It would be better to prevent duplicates with persistence or queuing in the twitter client
    private boolean gettingMoreUsers = false;

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
        getLvList().setDividerHeight(1);
        getLvList().setDivider(getResources().getDrawable(android.R.drawable.divider_horizontal_dark));
        getLvList().setAdapter(aUsers);
        getLvList().setOnScrollListener(new EndlessScrollingListener() {
            @Override
            public boolean onLoadMore(int page, int totalCount) {
                populateUsersList();
                return true;
            }
        });
        populateUsersList();
        return v;
    }

    public void populateUsersList() {
        if (gettingMoreUsers) {
            return;
        }

        Log.d("debug", "getting users with cursor " + next_cursor);
        // No more users to get
        if (next_cursor == 0) {
            return;
        }

        gettingMoreUsers = true;
        startingTwitterClientRequest();
        getClient().getUsersForType(type, fromUser.getUid(), next_cursor, userArrayRequestHandler);
    }

    private void updateUsersAndCursor(JSONObject json) {
        if (isRefreshing()) {
            aUsers.clear();
        }
        try {
            if (json.has("next_cursor")) {
                next_cursor = json.getLong("next_cursor");
            } else {
                // No more users to get
                next_cursor = 0;
            }
            Log.d("debug", "got users with cursor" + next_cursor);
            aUsers.addAll(User.fromJsonArray(json.getJSONArray("users")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        stoppedTwitterClientRequest();
        gettingMoreUsers = false;
    }

    @Override
    public void refreshList() {
        next_cursor = -1;
        populateUsersList();
    }
}
