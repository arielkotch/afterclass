package com.athena.athena.MainActivitys.ViewPager.Fragments;


import com.athena.athena.MainActivitys.ViewPager.JsonRequests;
import com.athena.athena.MainActivitys.ViewPager.MainActivity;
import com.yalantis.phoenix.PullToRefreshView;

public class Refresh  {
    private long refreshDelay;
    private PullToRefreshView pullToRefreshView;
    private static final long MIN_REFRESH_TIME = 500;
    private MainActivity.updateViewpager updateInterface;
    //TODO continue refresh class

    //refresh takes a pullToRefresh view and a Delay time and a update interface
    public Refresh(PullToRefreshView pullToRefreshView,MainActivity.updateViewpager updateInterface) {
        this.refreshDelay = refreshDelay;
        this.pullToRefreshView = pullToRefreshView;
        this.updateInterface=updateInterface;
    }

    public void isRefreshing() {
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        pullToRefreshView.setRefreshing(false);
                        updateInterface.update();
                        refreshDelay = JsonRequests.totalTime;
                        if (refreshDelay < MIN_REFRESH_TIME) {
                            refreshDelay = MIN_REFRESH_TIME;
                        }
                    }
                }, refreshDelay);
            }
        });
    }
}
