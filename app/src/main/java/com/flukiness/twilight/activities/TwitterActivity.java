package com.flukiness.twilight.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * Created by Jing Jin on 10/4/14.
 *
 * Reports whether it is visible or not to the application, allowing the application to  notify
 * it whether a network request has started or ended.
 */
public class TwitterActivity extends FragmentActivity {
    int ongoingNetworkRequests = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    }

    public void networkRequestStarted() {
        if (ongoingNetworkRequests == 0) {
            setProgressBarIndeterminateVisibility(true);
        }
        ongoingNetworkRequests++;
    }

    public void networkRequestEnded() {
        ongoingNetworkRequests--;
        if (ongoingNetworkRequests == 0) {
            setProgressBarIndeterminateVisibility(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
