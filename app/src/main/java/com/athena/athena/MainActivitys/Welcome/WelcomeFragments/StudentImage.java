package com.athena.athena.MainActivitys.Welcome.WelcomeFragments;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.athena.athena.Constants.Keys;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.Network.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class StudentImage {
    private final String studentImageUrl = Keys.URL_ADDERESS+"/api/Students/"+ Login.getUserId()+"?access_token="+Login.getStudentAuth();
    private final RequestQueue mRequestQueue = VolleySingleton.getInstance().getRequestQueue();

    public void setStudentImage(final String profileChosen){
        System.out.println("Putting Student Image "+studentImageUrl);
        StringRequest request = new StringRequest(Request.Method.PUT, studentImageUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject image = new JSONObject(response);
                    String getImageStudent = image.getString("image");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                //all the user needs to supply is their username and password
                parameters.put("image",profileChosen+".png");
                return parameters;
            }
        };
        mRequestQueue.add(request);
    }
}
