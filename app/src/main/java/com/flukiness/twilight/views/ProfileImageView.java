package com.flukiness.twilight.views;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.flukiness.twilight.activities.ProfileActivity;
import com.flukiness.twilight.models.User;

/**
 * Created by Jing Jin on 10/4/14.
 */
public class ProfileImageView extends ImageView {
    private User user;

    public ProfileImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupOnClickListener();
    }
    public ProfileImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupOnClickListener();
    }

    private void setupOnClickListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null) {
                    return;
                }

                Context c = getContext();
                Intent i = new Intent(c, ProfileActivity.class);
                i.putExtra(ProfileActivity.USER_KEY, user);
                c.startActivity(i);
            }
        });
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
