package com.athena.athena.MainActivitys.ViewPager.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.athena.athena.Adapters.ViewpagerAdapter;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.ViewPager.JsonRequests;
import com.athena.athena.MainActivitys.ViewPager.MainActivity;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;
import com.athena.athena.Testing.WrapContentLinearLayoutManager;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;


public class Points extends Fragment implements MainActivity.updateViewpager {
    private long refreshDelay;
    private static final long MIN_REFRESH_TIME=500;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
   // private static final String URL_ORDERED_POINTS = "http://52.86.96.132:3001/api/Questions?filter=%7B%20%22order%22%3A%20%22points%20DESC%22%2C%20%22limit%22%3A%20%2220%22%20%7D&access_token="+Login.getStudentAuth();
    private static final String URL_ORDERED_POINTS= Keys.URL_ADDERESS+"/api/Questions?filter=%7B%22include%22%3A%20%5B%22student%22%2C%20%20%7B%22course%22%3A%20%22school%22%7D%20%2C%20%20%7B%22comments%22%3A%20%22student%22%7D%20%2C%20%20%7B%22answers%22%3A%20%22student%22%7D%20%5D%2C%20%22order%22%3A%20%22points%20DESC%22%2C%20%22limit%22%3A%20%2220%22%7D&access_token="+ Login.getStudentAuth();
    private String mParam1;
    private static final String TAG="Points Fragment";

    private String mParam2;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private PullToRefreshView mPullToRefreshView;
    private ArrayList<QuestionData> mListblogs = new ArrayList<>();
    private ViewpagerAdapter mViewpagerAdapter;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static Points newInstance(String param1, String param2) {
        Points fragment = new Points();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Points() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.fragment_dashboard_recycler);
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
        mViewpagerAdapter =new ViewpagerAdapter(getActivity());
        mRecyclerView.setAdapter((mViewpagerAdapter));
        mPullToRefreshView = (PullToRefreshView)view.findViewById(R.id.pull_to_refresh);
       update();
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        update();
                        refreshDelay= JsonRequests.totalTime;
                        if(refreshDelay<MIN_REFRESH_TIME){
                            refreshDelay=MIN_REFRESH_TIME;
                        }
                    }
                }, refreshDelay);
            }
        });
        return view;
    }

    @Override
    public void update() {
        System.out.println("updating points");
             JsonRequests jsonPoints=new JsonRequests(mViewpagerAdapter,URL_ORDERED_POINTS,"Points");
            jsonPoints.JsonArrayRequest();
    }

}