package com.marius.valeyou.util.view;

import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by jatin on 6/5/2018.
 */

public class EndlessVerticalScroller extends RecyclerView.OnScrollListener {
    private int totalItemCount;
    private int visibleItemCount;
    private int pastVisiblesItems;
    private boolean loading;
    private LinearLayoutManager linearLayoutManager;
    private int VISIBLE_THRESHOLD = 5;
    private OnEndLessScrollListener onEndLessScrollListener;
    private boolean revrseLayout;
    private FloatingActionButton actionButton;
    private FloatingActionButton.OnVisibilityChangedListener listioner;

    public EndlessVerticalScroller(LinearLayoutManager linearLayoutManager, int VISIBLE_THRESHOLD) {
        this.linearLayoutManager = linearLayoutManager;
        this.VISIBLE_THRESHOLD = VISIBLE_THRESHOLD;
        loading = false;
        this.revrseLayout = linearLayoutManager.getReverseLayout();
    }

    public void fabVisibility(boolean visible) {
        if (actionButton != null) {
            if (visible) {
                actionButton.show();
            } else {
                actionButton.hide();
            }
        }
    }

    public void setFabListioner(FloatingActionButton.OnVisibilityChangedListener listioner) {
        this.listioner = listioner;
    }

    public boolean isFabVisible() {
        if (actionButton == null)
            return false;
        return actionButton.getVisibility() == View.VISIBLE;
    }

    public void setActionButton(@NonNull final FloatingActionButton actionButton) {
        this.actionButton = actionButton;
        this.actionButton.hide();
    }

    public EndlessVerticalScroller(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
        loading = false;
        this.revrseLayout = linearLayoutManager.getReverseLayout();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        Log.d("scroll", dy + "");
        if (revrseLayout) {
            if (dy < 0) //check for scroll down
            {
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findLastVisibleItemPosition();

                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - VISIBLE_THRESHOLD) {
                        loading = true;
                        if (onEndLessScrollListener != null)
                            onEndLessScrollListener.onLoadMore();
                    }
                }

            }

            if (actionButton != null) {
                if (dy < 0 && actionButton.getVisibility() != View.VISIBLE) {
                    actionButton.show(listioner);
                } else if (dy > 0 && actionButton.getVisibility() == View.VISIBLE && linearLayoutManager.findFirstVisibleItemPosition() == 0)
                    actionButton.hide(listioner);
            }

        } else {
            if (dy > 0) //check for scroll down
            {
                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();

                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - VISIBLE_THRESHOLD) {
                        loading = true;
                        if (onEndLessScrollListener != null)
                            onEndLessScrollListener.onLoadMore();
                    }
                }
            }
        }
    }

    public LinearLayoutManager getLayoutManager() {
        return linearLayoutManager;
    }

    public boolean isLoading() {
        return loading;
    }

    /**
     *
     * @param loading data provider is on end data
     *
     */
    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setOnEndLessScrollListener(OnEndLessScrollListener onEndLessScrollListener) {
        this.onEndLessScrollListener = onEndLessScrollListener;
    }

    public interface OnEndLessScrollListener {
        void onLoadMore();
    }
}
