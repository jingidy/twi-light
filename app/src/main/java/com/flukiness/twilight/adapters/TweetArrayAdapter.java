package com.flukiness.twilight.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.flukiness.twilight.R;
import com.flukiness.twilight.views.TweetView;
import com.flukiness.twilight.models.Tweet;

import java.util.ArrayList;

/**
 * Created by Jing Jin on 9/28/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetArrayAdapter(Context context, ArrayList<Tweet> objects) {
        super(context, R.layout.tweet_list_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet t = getItem(position);

        TweetView v = (TweetView) convertView;
        if (convertView == null) {
            v = TweetView.createView(getContext(), parent);
        }

        v.setTweet(t);
        return v;
    }

    public Tweet getOldest() {
        if (getCount() == 0) {
            return null;
        }
        return getItem(getCount() - 1);
    }

    public Tweet getNewest() {
        if (getCount() == 0) {
            return null;
        }
        return getItem(0);
    }

}
