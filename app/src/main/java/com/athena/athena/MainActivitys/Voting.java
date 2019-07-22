package com.athena.athena.MainActivitys;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.athena.athena.Constants.ErrorCodes;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.LoginActivitys.ModelType;
import com.athena.athena.Network.VolleySingleton;
import com.like.LikeButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class Voting {

    private int adapterPosition;
    private RecyclerView.Adapter adapter;
    private String stringId;
    private ArrayList<QuestionData> data;
    private TextView studentVoted;
    private String type;
    private Context mContext;
    private TextView mVotes;
    private LikeButton likeButton;
    private String questionActivityVotes;
    private TextView questionActivityVoteView;



    public Voting(String modelType,ArrayList<QuestionData> data,int adapterPosition, String stringId,Context context,TextView mVotes,LikeButton likeButton) {
        this.data=data;
        this.type=modelType;
        this.stringId = stringId;
        this.adapterPosition = adapterPosition;
        this.mContext=context;
        this.mVotes=mVotes;
        this.likeButton=likeButton;


    }
    //this is for the answerVoting
    //http://52.86.96.132:3001/api/ Answers / answerId /upvote?access_token="+Login.getStudentAuth()
    public void onUpVote() {

        final RequestQueue mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
        //TODO make a generic case for both Answer and Questions
        //TODO Delete Token(inserted for student 3 for testing purposes)
        final String PUT_VOTE_UP = Keys.URL_ADDERESS+"/api/"+type+"/" + stringId + "/upvote?access_token="+ Login.getStudentAuth();
        StringRequest postVoteUp = new StringRequest(Request.Method.PUT, PUT_VOTE_UP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Succesful Upvote The Students Value is false
                //TODO FIX THIS
                    realTimeUpVoting(mVotes);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              if(getErrorCode(error).equals(ErrorCodes.SELF_VOTING)){
                  errorToast();
                  likeButton.setLiked(false);
              }
            }
        });
        mRequestQueue.add(postVoteUp);
    }


    public void onDownVote() {
        final RequestQueue mrequestQueue = VolleySingleton.getInstance().getRequestQueue();
        //TODO Delete Token(inserted for student 3 for testing purposes)
        final String PUT_VOTE_DOWN = Keys.URL_ADDERESS+"/api/"+type+"/" + stringId + "/downvote?access_token="+Login.getStudentAuth();
        StringRequest postVoteDown = new StringRequest(Request.Method.PUT, PUT_VOTE_DOWN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO OnResponse, must setLiked(False)
                //Succesful downVote The Students Value is true
                //TODO FIX THIS
               realTimeDownVoting(mVotes);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(getErrorCode(error).equals(ErrorCodes.SELF_VOTING)){
                    errorToast();
                    //The Error Code of Self Voting was received
                }
            }
        });
        mrequestQueue.add(postVoteDown);
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
    public void errorToast(){
            Toast toast = Toast.makeText((mContext), "You Can't Vote Your Own Post", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

    }
    public void realTimeUpVoting(TextView votes){
        String voteString= votes.getText().toString();
        int voteNumber=Integer.parseInt(voteString)+1;
            System.out.println("data is not null up");

            data.get(adapterPosition).setVotes("" + voteNumber);
            data.get(adapterPosition).setVoters("true");
            //TODO Set as Boolean Value
            //TODO Change to boolean value
             data.get(adapterPosition).setVoters("true");
            votes.setText("" + voteNumber);

    }
    public void realTimeDownVoting(TextView votes){
        String voteString= votes.getText().toString();
        int voteNumber=Integer.parseInt(voteString)-1;
            System.out.println("data is not null down");
            data.get(adapterPosition).setVotes("" + voteNumber);
            data.get(adapterPosition).setVoters("false");
            //TODO Set as Boolean Value
             data.get(adapterPosition).setVoters("false");
            votes.setText("" + voteNumber);
    }
    public static String checkIfVoted(JSONArray jsonArray ) {
        /*pass in a json Array, copy the array into ints, and if
          the students Id is contained in the array return the string true
         */
        int[] voteIds = new int[jsonArray.length()];
        for(int i=0;i<voteIds.length;i++){
            voteIds[i] = jsonArray.optInt(i);
        }
        for(int i=0;i<voteIds.length;i++){
            if(voteIds[i]== Integer.parseInt(Login.getUserId())){
                //TODO String was only used for Testing purposes, Convert to Boolean later
                return "true";
            }
        }
        return "false";
    }
}