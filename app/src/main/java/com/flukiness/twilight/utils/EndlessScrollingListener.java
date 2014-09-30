package com.flukiness.twilight.utils;

import android.widget.AbsListView;

/**
 * Created by Jing Jin on 9/21/14.
 */
public abstract class EndlessScrollingListener implements AbsListView.OnScrollListener {
    private int visibleThreshold; // Min # of items below your current scroll position to load more
    private int currentPage = 0; // Current index offset
    private int previousTotalItemCount = 0; // # of items in the data set after last load
    private boolean loading = true; // true if still waiting for last set of data to load
    private int startingPageIndex = 0;

    public EndlessScrollingListener() {

    }

    public EndlessScrollingListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public EndlessScrollingListener(int visibleThreshold, int startPage) {
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startPage;
        this.currentPage = startPage;
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleCount, int totalCount) {

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalCount;
            if (totalCount == 0) {
                this.loading = true;
            }
        }

        // If it’s still loading, we check to see if the data set count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalCount;
            currentPage++;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        if (!loading && (totalCount - visibleCount) <= (firstVisibleItem + visibleThreshold)) {
            loading = onLoadMore(currentPage + 1, totalCount);
        }
    }

    // Returns whether or not implementer is actually loading more.
    public abstract boolean onLoadMore(int page, int totalCount);

    @Override
    public void onScrollStateChanged(AbsListView absListView, int state) {
        // Don't take any action on changed.
    }
}
