package com.athena.athena.MainActivitys;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.widget.Adapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.athena.athena.Adapters.AnswerAdapter;
import com.athena.athena.Constants.ErrorCodes;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.LoginActivitys.ModelType;
import com.athena.athena.Network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Select {

    private String modelType;
    private String answerId;
    private Context mContext;

    private RelativeLayout layout;
    private int currentPostion;
    private int previousPosition;
    private AnswerAdapter answerAdapter;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private  RecyclerView.Adapter mAnswerAdapter;
    private String id;
    private int adapterPosition;
    private static final String TAG="Select";

    private ArrayList<QuestionData> mListblogs = new ArrayList<>();

    private ArrayList<QuestionData> data;

    public Select(String modelType,String answerId,Context context,RelativeLayout layout,int adapterPosition, ArrayList<QuestionData> data,AnswerAdapter answerAdapter,String questionId){
        //ModelType Represents if the Key Passed in is Selected Or Unselected
        this.modelType=modelType;
        this.answerId=answerId;
        this.mContext=context;
        this.layout=layout;
        this.data=data;
        this.adapterPosition=adapterPosition;
        this.answerAdapter=answerAdapter;
        this.id=questionId;
    }

    public void modelSelect(){
        //http://52.86.96.132:3001/api/Answers/1/unselect
        final RequestQueue mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        final String PUT_SELECTED = Keys.URL_ADDERESS+"/api/Answers/"+answerId+"/"+modelType+"?access_token="+ Login.getStudentAuth();
        System.out.println("The Url is "+PUT_SELECTED);
        System.out.println("The Url For The Selected Question is "+PUT_SELECTED);

        StringRequest selected = new StringRequest(Request.Method.PUT, PUT_SELECTED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("The response is selected"+response);
                try {
                    setAllToFalse();
                    JSONObject jsonObject = new JSONObject(response);
                    String isSelected=jsonObject.getString("selected");
                    data.get(adapterPosition).setSelected(isSelected);
                    answerAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("There was an error in the Selection"+getErrorCode(error));
                if(getErrorCode(error).equals(ErrorCodes.ALREADY_SELECTED)){
                    alreadySelectedNotification();

                }
            }
        });
        mRequestQueue.add(selected);
    }

    public String getErrorCode(VolleyError error){
        //TODO likeButton.setEnabled(false);
        String responseBody = "";
        try {
            responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONObject errorResponse = jsonObject.getJSONObject("error");
            String errorCode = errorResponse.getString("reason");
            responseBody=errorCode;
        } catch (UnsupportedEncodingException | JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
    public void alreadySelectedNotification(){
        Toast toast = Toast.makeText((mContext), "Already Selected", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
    
    //TODO Updated the API must update views
    //TODO Update how the timeCreated is displayed in the view
    public void setAllToFalse(){
        for(int i=0;i<answerAdapter.getItemCount();i++){
           data.get(i).setSelected("false");
        }
    }
}
