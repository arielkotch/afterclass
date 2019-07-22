package com.athena.athena.NavigationDrawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.athena.athena.Adapters.ViewpagerAdapter;
import com.athena.athena.Adapters.myAnswerAdapter;
import com.athena.athena.Information.AnswerInformation;
import com.athena.athena.Constants.Keys;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.ViewPager.JsonRequests;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;

import java.util.ArrayList;

public class myAnswers extends AppCompatActivity {
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private RecyclerView AnswerRecyclerView;
    private ArrayList<AnswerInformation> AnswerList = new ArrayList<>();
    private myAnswerAdapter mAdapterQuestion;
    //Filter used for myAnswers {"include":{"question": "course"}}
    final String URL_GET_QUESTION_TO_STUDENT_ANSWER = Keys.URL_ADDERESS+"/api/Students/"+ Login.getUserId()+"/answers?filter=%7B%22include%22%3A%7B%22question%22%3A%20%22course%22%7D%7D&access_token="+Login.getStudentAuth();
    private ImageButton mVoteUp;
    private ViewpagerAdapter viewpagerAdapter;
    private ImageButton mVoteDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AnswerRecyclerView = (RecyclerView) findViewById(R.id.AnswerRecyclerView);
        AnswerRecyclerView.setLayoutManager(new LinearLayoutManager(myAnswers.this));
        viewpagerAdapter = new ViewpagerAdapter(myAnswers.this);
        AnswerRecyclerView.setAdapter(viewpagerAdapter);
        final JsonRequests jsonHome = new JsonRequests(viewpagerAdapter,URL_GET_QUESTION_TO_STUDENT_ANSWER, Keys.MY_ANSWERS);
        jsonHome.JsonArrayRequest();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        if(id==android.R.id.home){
           finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
