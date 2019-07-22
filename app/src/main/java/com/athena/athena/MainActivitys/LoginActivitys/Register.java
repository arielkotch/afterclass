package com.athena.athena.MainActivitys.LoginActivitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.athena.athena.Constants.ErrorCodes;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Exceptions.VolleyErrorJSON;
import com.athena.athena.Network.NetworkConnection;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements NetworkConnection {
    public final static int USER_PICKED_IMAGE = 1;
    private boolean newInstance=false;

    //// TODO: 4/19/16 FIX:In the case of an Email formatted as j@.com, it will pass the classes requirements but fail the servers.
    private EditText mUserLogin;
    private EditText mUserEmail;
    private EditText mCreatePassword;
    private EditText mConfirmPassword;
    private final String registerUrl = Keys.URL_ADDERESS+"/api/Students\n";
    private AutoCompleteTextView.Validator validator;
    private String[]profileData={"student3","student3","student3","student3"};
    private final RequestQueue mrequestQueue = VolleySingleton.getInstance().getRequestQueue();
    private String profileSelected;
    private Button chooseProfilePic;
    private Context context;
    private static final int USER_ALREADY_CREATED=422;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        mUserLogin = (MaterialEditText) findViewById(R.id.userNameCreate);
        //TODO Username and email dynamically submitted to the server for review IN PROGRESS
        mUserLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    System.out.println("Submitted Username for review");
                    //once the user leaves the user login text field this command will run
                    //we will send the user name to the server for review
                    //http://52.86.96.132:3001/api/Students/usernameAvailable?username=jozencl&access_token=HHjFjuxCcB9xyXHS9y2AaZV5ZmhFDfr3vRVq2nbMZqFJr7GWEtHQcBpxwgO8iDP5
                    final RequestQueue mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
                    final String checkUsername = Keys.URL_ADDERESS+"/api/Students/usernameAvailable?username="+mUserLogin.getText().toString();
                    //http://52.86.96.132:3001/api/Students/usernameAvailable?username=jozencl&access_token=HHjFjuxCcB9xyXHS9y2AaZV5ZmhFDfr3vRVq2nbMZqFJr7GWEtHQcBpxwgO8iDP5
                    StringRequest postVoteUp = new StringRequest(Request.Method.GET, checkUsername, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject userNameStatus=new JSONObject(response);
                                String userNameStatusIs = userNameStatus.getString("available");
                                boolean isUserNameTaken=Boolean.parseBoolean(userNameStatusIs);
                                if(isUserNameTaken){
                                    //if the response is true
                                    //continue
                                    System.out.println("The Username is taken "+isUserNameTaken);
                                    mUserLogin.setBackgroundColor(Color.TRANSPARENT);

                                }else{
                                    //if the response is false highlight material text box as red notify user
                                    System.out.println("The Username is not taken"+isUserNameTaken);
                                    mUserLogin.setError("Username is Taken");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    mRequestQueue.add(postVoteUp);
                }
            }
        });
        mUserEmail = (MaterialEditText) findViewById(R.id.email);
        mUserEmail.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){

                    System.out.println("Submitted email for review");
                    final RequestQueue mRequestQueue = VolleySingleton.getInstance().getRequestQueue();
                    final String checkUsername = Keys.URL_ADDERESS+"/api/Students/emailAvailable?email="+mUserEmail.getText().toString();
                    StringRequest postVoteUp = new StringRequest(Request.Method.GET, checkUsername, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject emailStatus=new JSONObject(response);
                                String emailStatusIs = emailStatus.getString("available");
                                boolean isEmailTaken=Boolean.parseBoolean(emailStatusIs);
                                if(isEmailTaken){
                                    //if the response is true
                                    //continue
                                    System.out.println("The email is taken "+isEmailTaken);
                                    mUserEmail.setBackgroundColor(Color.TRANSPARENT);

                                }else{
                                    //if the response is false highlight material text box as red notify user
                                    System.out.println("The email is not taken"+isEmailTaken);
                                    mUserEmail.setError("Email is Taken");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    mRequestQueue.add(postVoteUp);









                }
            }
        });
        mCreatePassword = (MaterialEditText) findViewById(R.id.passwordCreate);
        mCreatePassword.setTransformationMethod(new PasswordTransformationMethod());


        mConfirmPassword = (MaterialEditText) findViewById(R.id.passwordConfirm);
        mConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());


        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        profileSelected = profileData[0];
    }
    public boolean checkIfUMDStudent(String readUserEmail){

        if(readUserEmail.contains("umd.edu")&&readUserEmail.contains("@")){
            return true;
        }
        return false;
    }
    public void createUser(View view) {
        String readUserLogin = mUserLogin.getText() + " ";
        String readUserEmail = mUserEmail.getText() + " ";
        String readPasswordCreate = mCreatePassword.getText() + " ";
        String readPasswordConfirm = mConfirmPassword.getText() + " ";
        StringBuffer atSign = new StringBuffer("@");
        StringBuffer dotCom = new StringBuffer(".com");
        System.out.println("User Name length" + readUserLogin.length());
        //if the user types in nothing, the length is 2
        if (readUserLogin.length() < 2) {
            //a toast takes an application context, a text statment and a length as its parameters
            Toast toast = Toast.makeText(getApplicationContext(), "Please Enter a UserName", Toast.LENGTH_LONG);
            //sets the location
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        } else if (!checkIfUMDStudent(readUserEmail)) {
            //if the user does not put in an @ sign or .com we can assume it is not a valid email
            //a toast takes an application context, a text statment and a length as its parameters
            Toast toast = Toast.makeText(getApplicationContext(), "Please Enter a UMD Email", Toast.LENGTH_SHORT);
            //sets the location
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        } else if (readPasswordCreate.length() < 6 && readPasswordCreate.length() < 6) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please Enter a 5 lettered Password", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        } else if (!readPasswordCreate.equals(readPasswordConfirm)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Your Passwords Don't Match", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        } else {
            //if all the requirements for logging in are met the post response to the server proceeds
            //// TODO: 4/18/16 Post Register student Data to the Server

            System.out.println("LoginTag " + readUserLogin);
            System.out.println("LoginTag " + readUserEmail);
            System.out.println("LoginTag " + readPasswordCreate);
            System.out.println("LoginTag " + readPasswordConfirm);
            //if no exceptions are thrown with the Post Register
            //then we will navigate to the Login Screen
            //postMethodRegister() method makes an asynchronous request.
            postMethodRegister();
            //comment
        }
    }
    public void postMethodRegister() {
        StringRequest request = new StringRequest(Request.Method.POST, registerUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject studentsKey = null;
                System.out.println("The Url is "+registerUrl);
                try {
                    studentsKey = new JSONObject(response);
                    String isNewInstance = studentsKey.getString("isNewInstance");
                    onSuccess(isNewInstance);
                } catch (JSONException e) {
                    System.out.println("Error in creating student");
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    if(parseNetworkError(error) ==  USER_ALREADY_CREATED){
                        //case of a server error in which a student
                        //has already been created
                        userAlreadyCreated();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(!(isNetworkAvailable())){
                    //case in which their is no internet Connection
                    networkConnectError();
                    System.out.println("Network Error "+error.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                //the user will begin with 1000 points
                parameters.put("points", "1000");
                parameters.put("email", mUserEmail.getText().toString());
                parameters.put("username", mUserLogin.getText().toString());
                parameters.put("password", mConfirmPassword.getText().toString());
                return parameters;
            }
        };
        mrequestQueue.add(request);
    }

    public void onSuccess(String isNewInstance) {
        Intent login = getIntent();
        newInstance=Boolean.parseBoolean(isNewInstance);
        login.putExtra("isNewInstance",newInstance);
        setResult(Activity.RESULT_OK,login);
        finish();
    }

    public void userAlreadyCreated() {
        Toast toast = Toast.makeText(getApplicationContext(), "Username or Email Already Exists", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
    public void networkConnectError(){
        Toast toast = Toast.makeText(getApplicationContext(), "Please connect to the internet!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        if(id==android.R.id.home){
            Intent login = getIntent();
            setResult(Activity.RESULT_CANCELED,login);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected int parseNetworkError(VolleyError volleyError) throws JSONException {
        VolleyErrorJSON r=new VolleyErrorJSON();
        return r.getStatus(volleyError);
    }


    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();    }
}

