package com.flukiness.twilight.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flukiness.twilight.R;
import com.flukiness.twilight.models.Tweet;
import com.flukiness.twilight.models.User;
import com.flukiness.twilight.utils.TwitterApplication;
import com.flukiness.twilight.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class ReplyDialogFragment extends ComposeDialogFragment {
    public static final String IN_REPLY_TO_KEY = "in_reply_to";

    public static ReplyDialogFragment newInstance() {
        ReplyDialogFragment fragment = new ReplyDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        getDialog().setTitle(R.string.reply_title);

        Tweet inReplyTo = (Tweet)getArguments().getParcelable(IN_REPLY_TO_KEY);
        if (inReplyTo != null) {
            EditText et = getEtTweetText();
            et.setText(inReplyTo.getUser().getScreenNameUIString() + " ");
            et.setSelection(et.getText().length());
        }
        return v;
    }
}
