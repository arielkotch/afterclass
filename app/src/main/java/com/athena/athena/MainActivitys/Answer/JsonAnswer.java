package com.athena.athena.MainActivitys.Answer;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.athena.athena.Adapters.AnswerAdapter;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.Network.VolleySingleton;

import org.json.JSONArray;

import java.util.ArrayList;


public class JsonAnswer {

    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private ArrayList<QuestionData> questionData = new ArrayList<>();
    private AnswerAdapter mAnswerAdapter;
    private String id;
    private ArrayList<QuestionData> data;
    private int adapterPosition;



    public JsonAnswer(AnswerAdapter answerAdapter, String id){
        this.mAnswerAdapter=answerAdapter;
        this.id=id;
    }


    public void JsonRequestMethod() {
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        //TODO Updated the API must update views
        //TODO Update how the timeCreated is displayed in the view
        //Limits Answers by 30 answers, Sorted by time created
        final String URL_TEST_ANSWER=Keys.URL_ADDERESS+"/api/Questions/"+id+"/answers?filter=%7B%22include%22%3A%20%5B%22student%22%5D%2C%20%22order%22%3A%20%22created%20DESC%22%2C%20%22limit%22%3A%20%2230%22%7D&access_token="+Login.getStudentAuth();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_TEST_ANSWER, (String) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                questionData.clear();
                AnswerParsing answerParsing=new AnswerParsing();
                questionData = answerParsing.parseJSONResponseQuestion(response);
                mAnswerAdapter.setBloglist(questionData);
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
