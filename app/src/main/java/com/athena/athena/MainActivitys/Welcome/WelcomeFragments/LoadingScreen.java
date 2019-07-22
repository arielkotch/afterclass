package com.athena.athena.MainActivitys.Welcome.WelcomeFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.athena.athena.R;

public class LoadingScreen extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    public static LoadingScreen newInstance(int layoutResId) {
        LoadingScreen sampleSlide = new LoadingScreen();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    private int layoutResId;

    public LoadingScreen() {}

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logo_loading_screen, container, false);
        return view;
    }
}