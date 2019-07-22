package com.athena.athena.MainActivitys;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.Search;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.Network.VolleySingleton;

import java.util.ArrayList;


public class    Check {
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private RecyclerView.Adapter adapter;
    private int adapterPosition;
    private ArrayList<Search> data;
    public Check(RecyclerView.Adapter adapter, int position, ArrayList<Search> data){
        this.adapter=adapter;
        this.adapterPosition=position;
        this.data=data;

    }
    public void unchecked(View view){
    final CheckBox checkBox = (CheckBox)view;
    if (checkBox.isChecked()) {
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        CharSequence ID_PUT_COURSES = ((TextView) ((RelativeLayout) view.getParent()).getChildAt(3)).getText();
        System.out.println("The Id for put courses is "+ ID_PUT_COURSES);
        String UR_DELETE_COURSE = Keys.URL_ADDERESS+"/api/Students/"+ Login.getUserId()+"/courses/rel/"+ID_PUT_COURSES+"_1?access_token="+Login.getStudentAuth();
       System.out.println("The Url is "+UR_DELETE_COURSE);
        StringRequest UrlDeleteCourses = new StringRequest(Request.Method.DELETE, UR_DELETE_COURSE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Delete course success");
                data.remove(adapterPosition);
                adapter.notifyItemRemoved(adapterPosition);
                adapter.notifyItemRangeChanged(adapterPosition,data.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Delete course error");

                error.printStackTrace();
            }
        });
        mRequestQueue.add(UrlDeleteCourses);
        System.out.println("Course Deleted");
    }else {
        System.out.println("SET TO UNCHECKED");
    }
    }
}
