package com.flukiness.twilight.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.flukiness.twilight.R;
import com.flukiness.twilight.fragments.ComposeDialog;
import com.flukiness.twilight.fragments.HomeTimelineFragment;
import com.flukiness.twilight.fragments.MentionsTimelineFragment;
import com.flukiness.twilight.utils.FragmentTabListener;
import com.flukiness.twilight.models.Tweet;

public class TimelineActivity extends FragmentActivity implements ComposeDialog.ComposeDialogListener {
    private FragmentTabListener<HomeTimelineFragment> homeTweetsFragmentListener;
    private FragmentTabListener<MentionsTimelineFragment> mentionsTweetsFragmentListener;

    private static enum TabTags {
        HomeTimelineTag,
        MentionsTimelineTag
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        homeTweetsFragmentListener = new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
                HomeTimelineFragment.class);
        mentionsTweetsFragmentListener = new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
                MentionsTimelineFragment.class);
        setupTabs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_compose) {
            showComposeDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        Tab tab1 = actionBar
                .newTab()
                .setText("Home")
                .setIcon(R.drawable.ic_launcher)
                .setTag(TabTags.HomeTimelineTag)
                .setTabListener(homeTweetsFragmentListener);

        actionBar.addTab(tab1);
        actionBar.selectTab(tab1);

        Tab tab2 = actionBar
                .newTab()
                .setText("Mentions")
                .setIcon(R.drawable.ic_launcher)
                .setTag(TabTags.MentionsTimelineTag)
                .setTabListener(mentionsTweetsFragmentListener);

        actionBar.addTab(tab2);
    }

    public void showComposeDialog () {
        FragmentManager fm = getSupportFragmentManager();
        ComposeDialog dialog = ComposeDialog.newInstance();
        dialog.show(fm, "fragment_compose");
    }

    @Override
    public void onTweetPosted(Tweet t) {
        HomeTimelineFragment home = (HomeTimelineFragment)homeTweetsFragmentListener.getFragment();
        if (home != null) {
            home.onTweetPosted(t);
        }
        MentionsTimelineFragment mentions = (MentionsTimelineFragment)mentionsTweetsFragmentListener.getFragment();
        if (mentions != null) {
            mentions.onTweetPosted(t);
        }
    }
}
