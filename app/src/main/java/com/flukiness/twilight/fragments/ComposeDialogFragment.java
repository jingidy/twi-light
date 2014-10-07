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
import com.flukiness.twilight.utils.TwitterApplication;
import com.flukiness.twilight.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class ComposeDialogFragment extends DialogFragment {

    private EditText etTweetText;
    private Button btnSubmit;

    public interface ComposeDialogListener {
        void onTweetPosted(Tweet t);
    }

    public static ComposeDialogFragment newInstance() {
        ComposeDialogFragment fragment = new ComposeDialogFragment();
        return fragment;
    }
    public ComposeDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compose, container, false);
        etTweetText = (EditText) v.findViewById(R.id.etTweetText);
        btnSubmit = (Button) v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTweet();
            }
        });

        etTweetText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return v;
    }

    public EditText getEtTweetText() {
        return etTweetText;
    }

    public Button getBtnSubmit() {
        return btnSubmit;
    }

    public void sendTweet() {
        TwitterClient client = TwitterApplication.getRestClient();
        String text = etTweetText.getText().toString();
        btnSubmit.setText(R.string.label_tweeting);
        if (text.length() > 0) {
            client.pushUpdate(text, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(JSONObject jsonObject) {
                    handleSuccess(jsonObject);
                }
                @Override
                public void onFailure(Throwable e, String s) {
                    handleError(e, s);
                }
            });
        }
    }

    private void handleSuccess(JSONObject jsonObject) {
        Tweet t = Tweet.fromJson(jsonObject);
        if (t == null) {
            Log.d("DEBUG", "successfully posted tweet but  no tweet json");
        } else if(getActivity() instanceof  ComposeDialogListener) {
            ComposeDialogListener listener = (ComposeDialogListener) getActivity();
            listener.onTweetPosted(t);
        }
        dismiss();
        btnSubmit.setText(R.string.label_tweet);
    }

    private void handleError(Throwable e, String s) {
        //TODO: Better error handling
        Log.d("DEBUG", e.toString());
        Toast.makeText(getActivity(), "Failed to post tweet", Toast.LENGTH_SHORT).show();
        btnSubmit.setText(R.string.label_tweet);
    }

}
