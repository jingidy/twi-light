package com.flukiness.twilight;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flukiness.twilight.fragments.ComposeDialogFragment;
import com.flukiness.twilight.fragments.ReplyDialogFragment;
import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.models.User;
import com.flukiness.twilight.views.ProfileImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Jing Jin on 10/6/14.
 */
public class TweetView extends RelativeLayout {
    private static ImageLoader imageLoader = ImageLoader.getInstance();

    public ProfileImageView ivProfileImage;
    public TextView tvUsername;
    public TextView tvBody;
    public TextView tvName;
    public TextView tvTime;
    public LinearLayout llActions;
    public Button btnReply;
    public Button btnRetweet;
    public Button btnFavorite;

    private Tweet tweet;

    public static TweetView createView(Context c, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(c);
        final TweetView v =  (TweetView) inflater.inflate(R.layout.tweet_list_item, parent, false);

        v.ivProfileImage = (ProfileImageView) v.findViewById(R.id.ivProfileImage);
        v.tvUsername = (TextView) v.findViewById(R.id.tvUsername);
        v.tvBody = (TextView) v.findViewById(R.id.tvBody);
        v.tvName = (TextView) v.findViewById(R.id.tvName);
        v.tvTime = (TextView) v.findViewById(R.id.tvTime);
        v.llActions = (LinearLayout) v.findViewById(R.id.llActions);
        v.btnReply = (Button) v.findViewById(R.id.btnReply);
        v.btnRetweet = (Button) v.findViewById(R.id.btnRetweet);
        v.btnFavorite = (Button) v.findViewById(R.id.btnFavorite);

        v.llActions.setVisibility(GONE);
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                v.toggleActionsVisibility(view);
            }
        });

        v.btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.replyButtonClicked(view);
            }
        });
        v.btnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.retweetButtonClicked(view);
            }
        });
        v.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.favoriteButtonClicked(view);
            }
        });

        return v;
    }

    public TweetView(Context context) {
        super(context);
    }

    public TweetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TweetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet t) {
        tweet = t;
        User u = t.getUser();
        ivProfileImage.setUser(u);
        tvName.setText(u.getName());
        tvUsername.setText(u.getScreenNameUIString());
        tvBody.setText(t.getBody());
        tvTime.setText(t.getCreatedTimeUIString(getContext()));
        ivProfileImage.setImageResource(0);
        ivProfileImage.setTag(u);
        imageLoader.displayImage(u.getProfileImageUrl(), ivProfileImage);
    }

    public void replyButtonClicked(View v) {
        Context context = getContext();
        if (!(context instanceof FragmentActivity)) {
            return;
        }

        FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
        ReplyDialogFragment dialog = ReplyDialogFragment.newInstance();
        Bundle args = new Bundle();
        args.putParcelable(ReplyDialogFragment.IN_REPLY_TO_KEY, tweet);
        dialog.setArguments(args);
        dialog.show(fm, "fragment_compose");
    }

    public void retweetButtonClicked(View v) {

    }

    public void favoriteButtonClicked(View v) {

    }

    public void toggleActionsVisibility(View v) {
        if (llActions.getVisibility() == VISIBLE) {
            llActions.setVisibility(GONE);
        } else {
            llActions.setVisibility(VISIBLE);
        }
    }
}
