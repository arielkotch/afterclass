package com.athena.athena.MainActivitys.Welcome.WelcomeFragments;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.athena.athena.R;


public class AnonymousSlide extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    public static AnonymousSlide newInstance(int layoutResId) {
        AnonymousSlide sampleSlide = new AnonymousSlide();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    private int layoutResId;

    public AnonymousSlide() {

    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.anonymous,container,false);
        AssetManager am = getContext().getApplicationContext().getAssets();
        TextView header=(TextView)view.findViewById(R.id.headerTextThird);
        TextView sub=(TextView)view.findViewById(R.id.subHeaderThird);
        Typeface avenirHeader = Typeface.createFromAsset(am, "avenir-lt-std/AvenirLTStd-Heavy.otf");
        Typeface avenirSubHeader = Typeface.createFromAsset(am, "avenir-lt-std/AvenirLTStd-Heavy.otf");
        header.setTypeface(avenirHeader);
        sub.setTypeface(avenirSubHeader);
        return view;

    }

}