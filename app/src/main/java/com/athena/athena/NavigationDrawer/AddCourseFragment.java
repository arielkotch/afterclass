package com.athena.athena.NavigationDrawer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.athena.athena.MainActivitys.ImageLoading;
import com.athena.athena.MainActivitys.LoginActivitys.ModelType;
import com.athena.athena.R;

@SuppressLint("ValidFragment")
public class AddCourseFragment extends Fragment {
    private ImageView imageView;
    private String imageName;
    private int courseColor;


    @SuppressLint("ValidFragment")
    public AddCourseFragment(String imageName, int courseColor){
        this.imageName=imageName;
        this.courseColor=courseColor;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment1,container,false);
        imageView=(ImageView)view.findViewById(R.id.imageOne);
        System.out.println("The Image that is being created is "+imageName);
        ImageLoading imageLoading=new ImageLoading(imageName+".png",imageView, ModelType.COURSE);
        imageLoading.setImage();
        System.out.println("The Course Color is "+courseColor);
        return view;
    }

}
