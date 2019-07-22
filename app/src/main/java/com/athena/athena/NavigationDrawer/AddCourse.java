package com.athena.athena.NavigationDrawer;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.Search;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.ViewPager.Fragments.SavedCourses;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddCourse extends AppCompatActivity {
    private String mUrl = Keys.URL_ADDERESS + "/api/Courses?access_token="+Login.getStudentAuth();
    private static final String SCHOOL_ID = "_1";
    private Button postCourse;
    private AutoCompleteTextView courseCode;
    private EditText name;
    private EditText instructor;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private ViewPager viewPager;
    private String subjectSelected;
    public static ArrayList<Search>addCourseArray=new ArrayList<>();
    public static ArrayList<String>courseData=new ArrayList<>();
    public static ArrayList<String>autoCourseList=new ArrayList<>();


    //TODO Decide if color will be local or server Data
    private ImageData[]imageData={
            new ImageData("Biology", Color.GREEN),
            new ImageData("Math",Color.RED),
            new ImageData("Astronomy",Color.RED),
            new ImageData("Chemistry",Color.BLUE),
            new ImageData("Computer",Color.GREEN),
            new ImageData("Criminal",Color.YELLOW),
            new ImageData("Geology",Color.YELLOW),
            new ImageData("Medical",Color.GREEN),
            new ImageData("Music",Color.YELLOW),
            new ImageData("Mythology",Color.BLUE),
            new ImageData("Physics",Color.GREEN),
            new ImageData("Writing",Color.GREEN)
    };
    /*
       ********** TODO START-CONSTRUCTION********
     */
    public void autoCompleteCourseSearchRequest(){
        //set the id of the course to the autocomplete view
        courseCode = (AutoCompleteTextView) findViewById(R.id.autoCourse);
        //cycle through an arraylist
        //TODO replace savedCourses with the correct arraylist
        for(int i = 0; i< SavedCourses.ListSearch.size(); i++){
            //add the string to the array that is needed
            autoCourseList.add(SavedCourses.ListSearch.get(i).getCode());
        }
        //create an arraylist adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,autoCourseList);

        //set the array adapter to the coursecode
        courseCode.setAdapter(adapter);
        courseCode.setThreshold(0);
        //allows an input of only capital letters
        courseCode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);


    }
     /*
       ********** TODO END-CONSTRUCTION********
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course);
        viewPager=(ViewPager)findViewById(R.id.viewPagerPicture);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        System.out.println("The Current Item in the ViewPager is "+viewPager.getCurrentItem());
        //if the onPageSelected is not entered we need to give subject a default value
        //which should be the position at 0, which is the biology icon
        subjectSelected=imageData[0].getSubject();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //TODO If the First position 0 for Biology is Selected it Returns NULL, Fix
                System.out.println("The position in the Pageselected is "+position);
                System.out.println("The image at "+position+" is "+imageData[position].getSubject());
                subjectSelected=imageData[position].getSubject();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        JSONSearchInactiveClasses();



        //courseCode.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        name = (EditText) findViewById(R.id.autoCourse);
        //TODO Figures Out Way to Enforce that the first letter of every word is capatalized
        name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        instructor = (EditText) findViewById(R.id.instructor);
        postCourse = (Button) findViewById(R.id.postCourse);
        postCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
                StringRequest request = new StringRequest(Request.Method.POST, mUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.print("Succesful Post"+response);

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        System.out.print("Error Message PostQuestion" + error.getMessage());
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("id", courseCode.getText().toString() + SCHOOL_ID);
                        parameters.put("name", name.getText().toString());
                        parameters.put("department", subjectSelected);
                        //when a student posts a course activate that course
                        parameters.put("active", "true");
                        parameters.put("instructor", instructor.getText().toString());
                        parameters.put("image", subjectSelected+".png");
                        System.out.print("The Subject Selected is "+subjectSelected);
                        parameters.put("code", courseCode.getText().toString());
                        //TODO Switch School ID from HardWired Version
                        parameters.put("schoolId", "1");
                        System.out.println(parameters + "parameters");
                        return parameters;
                    }
                };
                System.out.println("request " + request);
                requestQueue.add(request);
                finish();
                //After this Activity Finishes, we want to reload the SearchBarActivity,
                //This is done by creating a direct intent to it
            }
        });
    }

    private class CustomAdapter extends FragmentPagerAdapter {

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return new AddCourseFragment(imageData[position].getSubject(),imageData[position].getColor());
        }

        @Override
        public int getCount() {
            return imageData.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return imageData[position].getSubject();
        }
    }
    public void setAutoCompleteText(ArrayList<Search>listOfCodes){
        for(int i=0;i<listOfCodes.size();i++){
            courseData.add(listOfCodes.get(i).getCode());
            System.out.println("Adding at "+i+" the data "+listOfCodes.get(i).getCode());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,courseData);
        courseCode = (AutoCompleteTextView) findViewById(R.id.code);
        courseCode.setAdapter(adapter);
        courseCode.setThreshold(0);
    }
    //TODO Change Over to requesting Course list from Server
    public void JSONSearchInactiveClasses() {
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        //{"where": {"active": "false"}}
        final String ALL_INACTIVE_COURSES=Keys.URL_ADDERESS+"/api/Courses?filter=%7B%22where%22%3A%20%7B%22active%22%3A%20%22false%22%7D%7D&access_token="+Login.getStudentAuth();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ALL_INACTIVE_COURSES, (String) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Search> listOfCodes=parseJSONResponseQuestion(response);
                setAutoCompleteText(listOfCodes);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);

            }
        });
        mRequestQueue.add(request);
    }
    public ArrayList<Search> parseJSONResponseQuestion(JSONArray response) {
        if (!response.equals("")) {
            try {
                StringBuilder data = new StringBuilder();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject currentQuestions = response.getJSONObject(i);
                    String name = currentQuestions.getString("name");
                    String code = currentQuestions.getString("code");
                    String instructor = currentQuestions.getString("instructor");
                    Search addCourseData=new Search();
                    addCourseData.setCode(code);
                    addCourseData.setName(name);
                    addCourseData.setInstructor(instructor);

                    data.append("The Response from Server is "+code+": "+name+": "+instructor);
                    addCourseArray.add(addCourseData);
                }
                System.out.println(data.toString());
                System.out.println("********END SERVER RESPONSE*********");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return addCourseArray;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
