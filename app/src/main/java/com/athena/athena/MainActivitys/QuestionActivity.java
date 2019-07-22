package com.athena.athena.MainActivitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.athena.athena.Adapters.AnswerAdapter;
import com.athena.athena.Adapters.ViewpagerAdapter;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.Answer.JsonAnswer;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.LoginActivitys.ModelType;
import com.athena.athena.MainActivitys.ViewPager.MainActivity;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class QuestionActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private VolleySingleton mVolleySingleton;
    public static boolean votedUp=false;
    public static boolean votedDown=false;

    private ArrayList<QuestionData> mListblogs = new ArrayList<>();
    private ArrayList<QuestionData> data;
    private AnswerAdapter mAnswerAdapter;
    private Button mButtonQuestion;
    private EditText mEditTextQuestion;
    private ImageView imageView;
    public static String studentQuestionId;
    private static String URL_SUB_QUESTION;
    private int pos;
    private ImageButton shareReplies;

    public static int position=-2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        mEditTextQuestion = (EditText) findViewById(R.id.QuestionEditText);
        mButtonQuestion = (Button) findViewById(R.id.AnswerPostButton);
        imageView = (ImageView) findViewById(R.id.studentImage);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.Question_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(QuestionActivity.this));
        mAnswerAdapter = new AnswerAdapter(QuestionActivity.this);
        mRecyclerView.setAdapter((mAnswerAdapter));

        System.out.print("Child at 0" + mRecyclerView.getChildAt(0));
        System.out.print("Child at 1" + mRecyclerView.getChildAt(1));
        System.out.print("Child at 2" + mRecyclerView.getChildAt(2));
        System.out.println("Entered Sub Activity");

        Bundle adapterPosition = getIntent().getExtras();

        if(adapterPosition != null){
             pos=adapterPosition.getInt("adapterPosition");
        }

        shareReplies=(ImageButton) findViewById(R.id.shareButton);
        shareReplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharingIsCaring();

            }
        });

        final String votes = ViewpagerAdapter.data.get(pos).getVotes();

        final TextView votesText = (TextView) findViewById(R.id.questionActivityVotes);
        votesText.setText(votes);
        String valBoolean = ViewpagerAdapter.data.get(pos).getVoters();
        final LikeButton mLikeButton = (LikeButton) findViewById(R.id.like_Button_QuestionActvity);
        mLikeButton.setLiked(Boolean.valueOf(valBoolean));

        System.out.println("Value fo the boolean is " + Boolean.valueOf(valBoolean));

        String courseCode = ViewpagerAdapter.data.get(pos).getCourseId();

        TextView mCourseCode = (TextView) findViewById(R.id.courseCodeQuestion);
        mCourseCode.setText(courseCode);


        String dateCreated = ViewpagerAdapter.data.get(pos).getDateCreated();

        TextView mDateCreated = (TextView) findViewById(R.id.TimeCreated);
        mDateCreated.setText(dateCreated);


        String dataString = ViewpagerAdapter.data.get(pos).getMtext();
        TextView mTextview = (TextView) findViewById(R.id.Text_question);
        mTextview.setText(dataString);
        mTextview.setMovementMethod(new ScrollingMovementMethod());
        //studentQuestionId stores the ID of the student that asked the question
        studentQuestionId = ViewpagerAdapter.data.get(pos).getMstudentId();
        String pointsString=ViewpagerAdapter.data.get(pos).getPoints();
        TextView mPointsText = (TextView) findViewById(R.id.pointsText);
        mPointsText.setText(pointsString);

        String imageName=ViewpagerAdapter.data.get(pos).getCourseImage();
        ImageLoading imageLoading=new ImageLoading(imageName,imageView,ModelType.COURSE);
        imageLoading.setImage();

        final  String questionIdString=ViewpagerAdapter.data.get(pos).getId();
        final JsonAnswer jsonHome=new JsonAnswer(mAnswerAdapter,questionIdString);
        jsonHome.JsonRequestMethod();
        System.out.println("Sub Question Entered");
        mLikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                System.out.println("Liked Up");
                System.out.println("Sub Question Entered");
                Voting voting = new Voting(ModelType.QUESTION,ViewpagerAdapter.data,pos, questionIdString, getApplicationContext(), votesText, mLikeButton);
                voting.onUpVote();
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                System.out.println("Liked Down");
                Voting voting = new Voting(ModelType.QUESTION,ViewpagerAdapter.data,pos, questionIdString, getApplicationContext(), votesText, mLikeButton);
                voting.onDownVote();
            }
        });
    }
    public void sharingIsCaring(){
        //once the share button is
        // activated let the user share the question to social media
        System.out.println("Shared to social media");
        Intent share=new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        String text = "Install this cool application: ";
        //TODO put package name here
        String link = "https://play.google.com/store/apps/details?id=" + "com.google.android.apps.plus";
        share.putExtra(Intent.EXTRA_TEXT, text + " " + link);
        startActivity(Intent.createChooser(share,"Share Using"));
    }
    @Override
    public void onBackPressed() {
        finish();
        MainActivity.updatePreviousFragment=true;
    }
    public void QuestionActivityOnClick(View view) {
        //Material for Answer Questions goes here
        //put in ID
        Bundle questiondata = getIntent().getExtras();
        if (questiondata == null) {
            return;
        }
        final String IdString= ViewpagerAdapter.data.get(pos).getId();

        final RequestQueue mrequestQueue = VolleySingleton.getInstance().getRequestQueue();
        //TODO Updated the API must update views
        final String URL_ANSWER = Keys.URL_ADDERESS + "/api/Answers?access_token=" + Login.getStudentAuth();
        System.out.println("Button Clicked!");
        System.out.println(mButtonQuestion);
        String readText = mEditTextQuestion.getText() + " ";
        System.out.print(readText.length());

        if (readText.length() == 1) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please Enter a Question", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            return;
        }


        StringRequest request = new StringRequest(Request.Method.POST, URL_ANSWER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response + "reponse");
                hideKeyboard(mEditTextQuestion);
                refreshJSON(IdString);
                mEditTextQuestion.getText().clear();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("************Answer" + error + "error");
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("text", mEditTextQuestion.getText().toString());
                //SET 192=QuestionId place in questionId=Id
                parameters.put("questionId", IdString);
                parameters.put("studentId", "1");
                parameters.put("points", "100");
                parameters.put("selectedAnswerId", "0");
                parameters.put("courseId", "1");
                System.out.println(parameters + "parameters");
                return parameters;
            }

        };
        System.out.println("request " + request);
        mrequestQueue.add(request);

    }

    public void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void refreshJSON(String id) {
        final JsonAnswer jsonHome=new JsonAnswer(mAnswerAdapter,id);
        jsonHome.JsonRequestMethod();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            MainActivity.updatePreviousFragment=true;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

