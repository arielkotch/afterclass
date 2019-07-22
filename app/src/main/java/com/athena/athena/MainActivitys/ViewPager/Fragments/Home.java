package com.athena.athena.MainActivitys.ViewPager.Fragments;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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


public class Home extends Fragment implements MainActivity.updateViewpager {
    //refresh Delay is set to 100 as a default
    private long refreshDelay;
    private static final long MIN_REFRESH_TIME = 500;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView noClasses;

    //URL reflects ordered list on server filter terms (old filter) {"include": {"relation": "questions", "scope": {"order" : "points DESC"}}})
   // base filter {"include": {"relation": "questions", "scope": {"include":"answers"}}}
    //filter that includes filtering questions based on time created  {"include": {"relation": "questions","scope":{"order":"created DESC","include":"answers"}}}

  //  private static final String URL_HOME = Keys.URL_ADDERESS + "/api/Students/" + Login.getUserId() + "/courses?filter=%20%7B%22include%22%3A%20%7B%22relation%22%3A%20%22questions%22%2C%22scope%22%3A%7B%22order%22%3A%22created%20DESC%22%2C%22include%22%3A%22answers%22%7D%7D%7D&access_token=" + Login.getStudentAuth();
    private static final String URL_HOME_ORDERED=Keys.URL_ADDERESS+"/api/Questions/ordered?access_token="+Login.getStudentAuth();
    private String mParam1;
    private String mParam2;
    private ImageView mImageView;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private ArrayList<QuestionData> mListblogs = new ArrayList<>();
    private ArrayList<QuestionData> mQuestionDataArrayList = new ArrayList<QuestionData>();
    private PullToRefreshView mPullToRefreshView;
    private ViewpagerAdapter mViewpagerAdapter;
    private static final String TAG = "Home Fragment";
    private TextView courseNotFound;
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Home() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //If you wish to handle you event source in the activity main thread, you’ll need to add the following instructions at the beginning of the onCreate() android callback:
        System.out.println("userId" + Login.getUserId());
        System.out.println("username" + Login.getUserName());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
         final View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_dashboard_recycler);
        mRecyclerView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
        mViewpagerAdapter = new ViewpagerAdapter(getActivity());
        mRecyclerView.setAdapter((mViewpagerAdapter));
        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        update();
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        update();
                        refreshDelay = JsonRequests.totalTime;
                        if (refreshDelay < MIN_REFRESH_TIME) {
                            refreshDelay = MIN_REFRESH_TIME;
                        }
                    }
                }, refreshDelay);
            }
        });
        return view;
    }

    @Override
    public void update() {
        System.out.println("Updating Home");
        JsonRequests jsonHome = new JsonRequests(mViewpagerAdapter, URL_HOME_ORDERED, Keys.HOME_KEY);
        jsonHome.JsonObjectRequest();

    }
}



