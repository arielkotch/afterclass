package com.athena.athena.MainActivitys.Welcome.WelcomeFragments;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.athena.athena.R;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private boolean[] selected = new boolean[profileData.length];

    public static ProfileData[]profileData={
            new ProfileData(R.drawable.profileone,"profile1"),
            new ProfileData(R.drawable.profiletwo,"profile2"),
            new ProfileData(R.drawable.profilethree,"profile3"),
            new ProfileData(R.drawable.profilefour,"profile4"),
            new ProfileData(R.drawable.profilefive,"profile5"),
    };
    public void setSelected(int position, boolean sel) {
        selected[position] = sel;
        notifyDataSetChanged();
    }
    public ImageAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return profileData.length;
    }

    @Override
    public Object getItem(int position) {
        return profileData[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView=new ImageView(context);
        imageView.setImageResource(profileData[position].getDrawable());
        if (selected[position]) {
            imageView.setColorFilter(Color.argb(85,105,105,105));
        } else {
            imageView.setColorFilter(null);
        }
        return imageView;
    }
}