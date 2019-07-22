package com.athena.athena.Exceptions;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


public class VolleyErrorJSON extends VolleyError  {


   //if there is no response from the server it is down
    public void checkServerStatus(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            //TODO throw server down exception
        }
    }
    //pass in the volleyError
    //returns an integerValue of the status code
    //returns 0 if there is an error
    public int getStatus(VolleyError volleyError) throws JSONException {
            String volleyErrorMessage;
            int statusCode=0;
            if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                volleyErrorMessage=error.getMessage();
                System.out.println("The Message is "+error.getMessage());
                JSONObject jsonObject=new JSONObject(volleyErrorMessage);
                JSONObject errorJson=new JSONObject(jsonObject.getString("error"));
                String status= errorJson.getString("status");
                statusCode=Integer.parseInt(status);
            }
            return statusCode;
    }
    //if null is returned there was an issue with getMessage()
    public String getMessage(VolleyError volleyError) throws JSONException {
        String volleyErrorMessage=null;
        int statusCode=0;
        if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyErrorMessage=error.getMessage();
            JSONObject jsonObject=new JSONObject(volleyErrorMessage);
            JSONObject errorJson=new JSONObject(jsonObject.getString("error"));
            String message= errorJson.getString("message");
            volleyErrorMessage=message;
        }
        return volleyErrorMessage;
    }
    public String getName(VolleyError volleyError) throws JSONException {
        String volleyErrorMessage=null;
        int statusCode=0;
        if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyErrorMessage=error.getMessage();
            JSONObject jsonObject=new JSONObject(volleyErrorMessage);
            JSONObject errorJson=new JSONObject(jsonObject.getString("error"));
            String message= errorJson.getString("name");
            volleyErrorMessage=message;
        }
        return volleyErrorMessage;
    }


}
