package com.athena.athena.NavigationDrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.athena.athena.Adapters.AdapterSearch;
import com.athena.athena.Information.Search;
import com.athena.athena.Constants.Keys;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchBarActivity extends AppCompatActivity {
    private SearchView mSearchView;
    private RequestQueue mRequestQueue;
    private VolleySingleton mVolleySingleton;
    private RecyclerView SearchBarRecycler;
    private AdapterSearch mAdapterQuestion;
    private ArrayList<Search> ListSearch = new ArrayList<>();
    final String URL_COURSES = Keys.URL_ADDERESS+"/api/Courses?access_token="+Login.getStudentAuth();
    private static boolean check=false;
    private static final String TAG="SearchBarActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSearchView=(SearchView)findViewById(R.id.search_bar);
        SearchBarRecycler = (RecyclerView)findViewById(R.id.search_bar_recycler);
        SearchBarRecycler.setLayoutManager(new LinearLayoutManager(SearchBarActivity.this));
        mAdapterQuestion=new AdapterSearch(SearchBarActivity.this);
        SearchBarRecycler.setAdapter(mAdapterQuestion);

        final TextView addCourseDefault=(TextView)findViewById(R.id.defaultText);
        addCourseDefault.setVisibility(View.INVISIBLE);


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapterQuestion.setFilter(ListSearch);
                final ArrayList<Search> filteredModelList = filter(ListSearch, newText);
                mAdapterQuestion.setFilter(filteredModelList);
                if(filteredModelList.isEmpty()){
                    addCourseDefault.setVisibility(View.VISIBLE);
                }else{
                    addCourseDefault.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        });
    }

    private ArrayList<Search> parseJSONResponseQuestion(JSONArray response) {
        if (!response.equals("")) {
            try {
                StringBuilder data = new StringBuilder();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject currentQuestions = response.getJSONObject(i);
                    String name = currentQuestions.getString("name");
                    String code = currentQuestions.getString("code");
                    String department = currentQuestions.getString("department");
                    //active represents the status of the class
                    //if active=false then the class has not been activated
                    //if active=true then the class is active
                    String active=currentQuestions.getString("active");

                    //TODO Instructor in the umd.io api is not present(Workaround)
                    //String instructor = currentQuestions.getString("instructor");
                    String image = currentQuestions.getString("image");
                    Search search = new Search();
                    search.setName(name);
                    search.setCode(code);
                    search.setImage(image);
                    search.setDepartment(department);
                    search.setActive(active);
                   // search.sd(instructor);
                    search.setDescription("");
                    ListSearch.add(search);
                }
                System.out.println(data.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ListSearch;
    }
    private ArrayList<Search> filter(List<Search> models, String query) {
        query = query.toLowerCase();

        final ArrayList<Search> filteredModelList = new ArrayList<>();
        for (Search model : models) {
            final String text = model.getName().toLowerCase();
            final String active=model.getActive().toLowerCase();
              boolean isActive= Boolean.parseBoolean(active);

            final String Symbol = model.getCode().toLowerCase();
            //will not search inactive classes
            if(isActive){
                if (text.contains(query)|Symbol.contains(query)) {
                    filteredModelList.add(model);
                }
            }
        }
        return filteredModelList;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            System.out.println("Action Settings Called");
            return true;
        }
        if(id==android.R.id.home){
           finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void CheckBox(View view) {
        final CheckBox checkBox = (CheckBox)view;
        if (checkBox.isChecked()) {

            System.out.println("SET TO CHECKED");
            //Input instance of selected course(CHECKED)
            // TODO: 2/5/16 Need to pass in Persisted ID
            mVolleySingleton = VolleySingleton.getInstance();
            mRequestQueue = mVolleySingleton.getRequestQueue();
            CharSequence ID_PUT_COURSES = ((TextView) ((RelativeLayout) view.getParent()).getChildAt(1)).getText();
            System.out.println("URL_COURSE ID_PUT_COURSES-PUT="+ID_PUT_COURSES);

            String URL_PUT_COURSES = "http://52.86.96.132:3001/api/Students/"+Login.getUserId()+"/courses/rel/"+ID_PUT_COURSES+"_1?access_token="+Login.getStudentAuth();
            System.out.print("This is the put URL"+URL_PUT_COURSES);
            StringRequest UrlPutCourses = new StringRequest(Request.Method.PUT, URL_PUT_COURSES, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response + "reponse");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    System.out.println("************Answer" + error + "error");
                }
            });
            mRequestQueue.add(UrlPutCourses);
            System.out.println("Course Added");
        }

    else{
            System.out.println("SET TO UNCHECKED");
            //delete instance of selected course(UNCHECKED)
            mVolleySingleton = VolleySingleton.getInstance();
            mRequestQueue = mVolleySingleton.getRequestQueue();
            // TODO: 2/5/16 Need to pass in Persisted ID
            System.out.println();
            CharSequence ID_PUT_COURSES = ((TextView) ((RelativeLayout) view.getParent()).getChildAt(1)).getText();
            System.out.println("URL_COURSE ID_PUT_COURSES-Delete="+ID_PUT_COURSES);

            String UR_DELETE_COURSE = "http://52.86.96.132:3001/api/Students/"+ Login.getUserId()+"/courses/rel/"+ID_PUT_COURSES+"_1?access_token="+Login.getStudentAuth();
            StringRequest UrlDeleteCourses = new StringRequest(Request.Method.DELETE, UR_DELETE_COURSE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response + "reponse");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    System.out.println("************Answer" + error + "error");
                }
            });
            mRequestQueue.add(UrlDeleteCourses);
            System.out.println("Course Deleted");
        }

    }
    public void jsonRequest(){
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_COURSES, (String) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ListSearch.clear();

                System.out.println("The response in the Array is " + response.toString());
                ListSearch = parseJSONResponseQuestion(response);
                mAdapterQuestion.setSearch(ListSearch);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("There was an error and it is " + error.getMessage());

            }
        });
        mRequestQueue.add(request);
    }
    public void addCourse(View view) {
        Intent addCourse=new Intent(SearchBarActivity.this, AddCourse.class);
        startActivity(addCourse);
        System.out.println("Course Added Button Clicked");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "On Resume Called");
        jsonRequest();
        Log.d(TAG, "Json Data Created");


    }
}
