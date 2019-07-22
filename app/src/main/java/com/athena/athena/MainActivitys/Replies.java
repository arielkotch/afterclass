package com.athena.athena.MainActivitys;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.Answer.AnswerParsing;
import com.athena.athena.MainActivitys.Answer.JsonAnswer;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.Network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Replies {
    private String questionId;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private QuestionData data;
    String replies;
    private String url;

    //Predondition:Place the id of the questions into the constructor
    //as well as the url
    //Postcondition: counts the number of replies
    public Replies(String questionId,String url,QuestionData data) {
        this.questionId = questionId;
        this.url=url;
        this.data=data;

    }
    public interface VolleyCallback{
        String onSuccess(String result);

    }
    public void getRepliesJsonArrayRequest() {
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url, (String) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //get the number of replies from the length of the response
                replies=""+response.length();
                //this is the callback interface, if employed
                //it gets the response out of the onResponse
                data.setReplies(replies);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);

            }
        });
        mRequestQueue.add(request);

    }
}