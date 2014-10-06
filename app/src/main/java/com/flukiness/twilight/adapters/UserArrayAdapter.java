package com.flukiness.twilight.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.flukiness.twilight.R;
import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.models.User;
import com.flukiness.twilight.views.ProfileImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Jing Jin on 9/28/14.
 */
public class UserArrayAdapter extends ArrayAdapter<User> {
    private static ImageLoader imageLoader = ImageLoader.getInstance();

    public static class ViewHolder {
        ProfileImageView ivProfileImage;
        TextView tvUsername;
        TextView tvCaption;
        TextView tvName;
    }

    public UserArrayAdapter(Context context, ArrayList<User> objects) {
        super(context, R.layout.user_list_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User u = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.user_list_item, parent, false);
            viewHolder.ivProfileImage = (ProfileImageView) convertView.findViewById(R.id.ivProfileImage);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivProfileImage.setUser(u);
        viewHolder.tvName.setText(u.getName());
        viewHolder.tvUsername.setText(u.getScreenName());
        viewHolder.tvCaption.setText(u.getDescription());
        viewHolder.ivProfileImage.setTag(u);
        imageLoader.displayImage(u.getProfileImageUrl(), viewHolder.ivProfileImage);
        return convertView;
    }

    public User getOldest() {
        if (getCount() == 0) {
            return null;
        }
        return getItem(getCount() - 1);
    }

    public User getNewest() {
        if (getCount() == 0) {
            return null;
        }
        return getItem(0);
    }
}
