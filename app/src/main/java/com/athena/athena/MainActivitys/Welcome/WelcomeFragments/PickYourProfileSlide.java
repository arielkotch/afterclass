package com.athena.athena.MainActivitys.Welcome.WelcomeFragments;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.athena.athena.R;

import java.util.ArrayList;


public class PickYourProfileSlide extends Fragment {
    private ArrayList<ProfileData> profiles=new ArrayList<>();
    private ImageAdapter adapter;
    public static final String TAG_PROFILE="profileChooser";

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    public static PickYourProfileSlide newInstance(int layoutResId) {
        PickYourProfileSlide sampleSlide = new PickYourProfileSlide();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    private int layoutResId;

    public PickYourProfileSlide() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pick_your_profile,container,false);
        final GridView gridView=(GridView)view.findViewById(R.id.profileGrid);
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(8);
        gridView.setBackground(shape);
        AssetManager am = getContext().getApplicationContext().getAssets();
        Typeface roboto = Typeface.createFromAsset(am, "Roboto/Roboto-Bold.ttf");
        TextView textView=(TextView)view.findViewById(R.id.pickProfile);
        textView.setTypeface(roboto);
        adapter=new ImageAdapter(getContext());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                StudentImage studentImage =new StudentImage();
                studentImage.setStudentImage(ImageAdapter.profileData[position].getProfile());
                System.out.println("Profile chosen is "+ImageAdapter.profileData[position].getProfile());
                for(int i=0;i<adapter.getCount();i++){
                    adapter.setSelected(i,false);
                }
                adapter.setSelected(position, true);
            }
        });
        return view;

    }

}

