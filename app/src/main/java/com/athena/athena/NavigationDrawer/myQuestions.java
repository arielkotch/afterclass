package com.athena.athena.NavigationDrawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.athena.athena.Adapters.ViewpagerAdapter;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.QuestionData;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.ViewPager.JsonRequests;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;

public class myQuestions extends AppCompatActivity {
    private static final String URL_HOME = Keys.URL_ADDERESS + "/api/Students/" + Login.getUserId() + "/courses?filter=%7B%22include%22%3A%20%7B%22relation%22%3A%20%22questions%22%2C%20%22scope%22%3A%20%7B%22order%22%20%3A%20%22id%20ASC%22%7D%7D%7D&access_token=" + Login.getStudentAuth();
    //private static final String URL_HOME_TEST=Keys.URL_ADDERESS+"/api/Students/"+Login.getUserId()+"/courses?filter={include:{relation:questions,scope:{order:id ASC}}}&access_token="+Login.getStudentAuth();
    private ImageView mImageView;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private ArrayList<QuestionData> mListblogs = new ArrayList<>();
    private ArrayList<QuestionData> mQuestionDataArrayList = new ArrayList<QuestionData>();
    private PullToRefreshView mPullToRefreshView;
    private ViewpagerAdapter mViewpagerAdapter;
    //filter-{"include": ["student", {"course": "school"} ,  {"answers": "student"} ]}
    private final String URL_QUESTIONS=Keys.URL_ADDERESS+"/api/Students/"+Login.getUserId()+"/questions?filter=%7B%22include%22%3A%20%5B%22student%22%2C%20%7B%22course%22%3A%20%22school%22%7D%20%2C%20%20%7B%22answers%22%3A%20%22student%22%7D%20%5D%7D&access_token="+Login.getStudentAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView)findViewById(R.id.StudentQuestionRecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mViewpagerAdapter = new ViewpagerAdapter(this);
        mRecyclerView.setAdapter((mViewpagerAdapter));
        final JsonRequests jsonHome = new JsonRequests(mViewpagerAdapter,URL_QUESTIONS, Keys.MY_QUESTIONS);
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
