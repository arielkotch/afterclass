package com.athena.athena.MainActivitys.LoginActivitys;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.athena.athena.Constants.Keys;
import com.athena.athena.MainActivitys.ViewPager.MainActivity;
import com.athena.athena.MainActivitys.Welcome.WelcomeSlides;
import com.athena.athena.Network.NetworkConnection;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity implements NetworkConnection {
    private Button mButton;
    private static EditText mUsername;
    private static EditText mPassword;
    private final String loginUrl = Keys.URL_ADDERESS+"/api/Students/login\n";
    private final RequestQueue mrequestQueue = VolleySingleton.getInstance().getRequestQueue();
    private static String key;
    private TextView loginHeader;
    private TextView orText;
    private static String userId;
    static final int USER_REGISTERED = 1;
    private static boolean isNewInstance=false;
    public static String studentsImageLogin;
    private RequestQueue mRequestQueue;
   private VolleySingleton mVolleySingleton;
    public static String studentImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mButton=(Button)findViewById(R.id.loginButton);
        mUsername=(EditText)findViewById(R.id.username);
        mPassword=(EditText)findViewById(R.id.password);
        mPassword.setTransformationMethod(new PasswordTransformationMethod());

        AssetManager am = getApplicationContext().getAssets();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postMethodLogin();
            }
        });

    }

    public void register(View view) {
        Intent register=new Intent(Login.this, Register.class);
        startActivityForResult(register,USER_REGISTERED);

    }
    public void postMethodLogin() {
        System.out.println("Url is "+loginUrl);


        StringRequest request = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response for Login=" + response);
                try {
                    //this gets the Students Key
                        JSONObject studentsKey=new JSONObject(response);
                        String studentsKeyString = studentsKey.getString("id");
                        String studentsUserId = studentsKey.getString("userId");

                        //if the user was just created then isNewInstance is true
                    if(isNewInstance){
                        //if isNewInstance is true, direct the user to the welcome screen
                        onWelcome(studentsKeyString,studentsUserId);
                    }else{
                        //if isNewInstance is false then the user has previously been created
                        onUserRemembered(studentsKeyString,studentsUserId);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(!(isNetworkAvailable())){
                    //this is the case in which their is no internet
                    networkConnectError();
                }else{
                    //this is the case in which the login credentials fail
                    userLoginFailed();

                }
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                //all the user needs to supply is their username and password
                parameters.put("username",mUsername.getText().toString());
                parameters.put("password",mPassword.getText().toString());
                return parameters;
            }
        };
        mrequestQueue.add(request);
    }
    /*PreCondition:Pass in the students authorization key into this method
     *PostCondition:Starts Main Activity, and put the autorization key as an Extra
     */


    public void onUserRemembered(String studentsKey,final String user){
        key=studentsKey;
        userId=user;
        Intent login = new Intent(Login.this, MainActivity.class);
        startActivity(login);
    }

    public void onWelcome(String studentsKey,final String user){
        //after the user logs in to the welcome screen
        //Set isNewInstance to false so that this only occurs once
        isNewInstance=false;
        key=studentsKey;
        userId=user;
        Intent login = new Intent(Login.this, WelcomeSlides.class);
        startActivity(login);
    }
    public void onTest(){
        Intent login = new Intent(Login.this, WelcomeSlides.class);
        startActivity(login);
    }
    public void userLoginFailed() {
        Toast toast = Toast.makeText(getApplicationContext(), "Please Check Your Username or Password", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
    //check if the user is connected to the internet on the phone
    public void networkConnectError(){
        Toast toast = Toast.makeText(getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
    //this saves the students authorization key
    public static String getStudentAuth(){
        return key;
    }
    //TODO Pass in userId to all Relevant classes
    public static String getUserId(){
        return userId;
    }
    public static String getUserName(){
        return mUsername.getText().toString();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == USER_REGISTERED) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                 isNewInstance=data.getBooleanExtra("isNewInstance",false);
                System.out.println("The Result is "+isNewInstance);
            }else if(resultCode==RESULT_CANCELED){
                System.out.println("The Result is "+isNewInstance);

            }
        }
    }

    @Override
    public boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
