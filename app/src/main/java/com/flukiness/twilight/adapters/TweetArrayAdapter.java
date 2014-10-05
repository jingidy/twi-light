package com.flukiness.twilight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flukiness.twilight.R;
import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.models.User;
import com.flukiness.twilight.views.ProfileImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Jing Jin on 9/28/14.
 */
public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
    private static ImageLoader imageLoader = ImageLoader.getInstance();

    public static class ViewHolder {
        ProfileImageView ivProfileImage;
        TextView tvUsername;
        TextView tvBody;
        TextView tvName;
        TextView tvTime;
    }

    public TweetArrayAdapter(Context context, ArrayList<Tweet> objects) {
        super(context, R.layout.tweet_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet t = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.tweet_item, parent, false);
            viewHolder.ivProfileImage = (ProfileImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User u = t.getUser();
        viewHolder.ivProfileImage.setUser(u);
        viewHolder.tvName.setText(u.getName());
        viewHolder.tvUsername.setText(u.getScreenName());
        viewHolder.tvBody.setText(t.getBody());
        viewHolder.tvTime.setText(t.getCreatedTimeUIString(getContext()));
        viewHolder.ivProfileImage.setTag(u);
        imageLoader.displayImage(u.getProfileImageUrl(), viewHolder.ivProfileImage);
        return convertView;
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
