package com.athena.athena.MainActivitys.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.athena.athena.Adapters.ViewpagerAdapter;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.ParsingMethods;
import com.athena.athena.Network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonRequests {
    private VolleySingleton mVolleySingleton;
    private ViewpagerAdapter mViewpagerAdapter;
    private RequestQueue mRequestQueue;
    private ParsingMethods method;

    private String mUrl;
    private String mClassKey;
    long start = 0;
    long finish = 0;
    public static long totalTime;
    public static ArrayList<QuestionData> mQuestionDataArrayList = new ArrayList<QuestionData>();

    public JsonRequests(ViewpagerAdapter viewpagerAdapter, String url, String key) {
        mClassKey = key;
        mViewpagerAdapter = viewpagerAdapter;
        mUrl = url;
    }

    public void JsonArrayRequest() {
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        start = System.currentTimeMillis();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, mUrl, (String) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mQuestionDataArrayList.clear();
                 method = new ParsingMethods();
               if(mClassKey.equals(Keys.MY_ANSWERS)){
                    mQuestionDataArrayList= method.parseAnswers(response);
                }else{
                    mQuestionDataArrayList = method.parseQuestions(response);
                }
                mViewpagerAdapter.setBloglist(mQuestionDataArrayList);
                finish = System.currentTimeMillis();
                totalTime = (finish - start);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);

    }
    public void JsonObjectRequest() {
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        start = System.currentTimeMillis();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //clear the data in the Arraylist, before making a request
                //otherwise the static arraylist, will instantiate data from its past request test
                mQuestionDataArrayList.clear();
                method=new ParsingMethods();
                mQuestionDataArrayList = method.parseResponseHome(response);
                mViewpagerAdapter.setBloglist(mQuestionDataArrayList);
                totalTime = (finish - start);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);

    }

}

