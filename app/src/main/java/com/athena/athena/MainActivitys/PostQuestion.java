package com.athena.athena.MainActivitys;

        import android.os.Bundle;
        import android.support.v4.app.NavUtils;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.text.InputType;
        import android.view.Gravity;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import com.android.volley.AuthFailureError;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.athena.athena.Constants.Keys;
        import com.athena.athena.MainActivitys.LoginActivitys.Login;
        import com.athena.athena.MainActivitys.ViewPager.Fragments.SavedCourses;
        import com.athena.athena.Network.VolleySingleton;
        import com.athena.athena.R;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

public class PostQuestion extends AppCompatActivity {

        private EditText mEditText;
        private EditText mPointsText;
        private EditText mPostTitle;
        private EditText mCoursePost;
        private VolleySingleton mVolleySingleton;
        private RequestQueue mRequestQueue;

        private AutoCompleteTextView mEditTextCourseCodes;
        private static ArrayList<String>studentCourseList=new ArrayList<>();
        private final String URL_SAVED_COURSES = Keys.URL_ADDERESS+"/api/Students/"+ Login.getUserId()+"/courses?access_token="+Login.getStudentAuth();

        private static final String SCHOOL_ID="_1";
        private Button mButton;
        private int id=1;
        final String insertUrl = Keys.URL_ADDERESS+"/api/Questions?access_token="+ Login.getStudentAuth();
        ArrayList<String>array=new ArrayList<String>(20);
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search);
            PostQuestion.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mEditText = (EditText) findViewById(R.id.body);
            mButton = (Button) findViewById(R.id.Postbutton);
            mPointsText=(EditText)findViewById(R.id.points);
            System.out.println("OnCreate Called");

            for(int i=0;i<SavedCourses.ListSearch.size();i++){
                System.out.println("The Code is for the saved Courses"+SavedCourses.ListSearch.get(i).getCode());
                studentCourseList.add(SavedCourses.ListSearch.get(i).getCode());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line,studentCourseList);
            mEditTextCourseCodes = (AutoCompleteTextView) findViewById(R.id.courseCodes);
            mEditTextCourseCodes.setAdapter(adapter);
            mEditTextCourseCodes.setThreshold(0);
            //mEditTextCourseCodes =(EditText) findViewById(R.id.courseCodes);
            mEditTextCourseCodes.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

            mPostTitle=(EditText)findViewById(R.id.postTitle);

            final RequestQueue mrequestQueue = VolleySingleton.getInstance().getRequestQueue();
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(mButton);
                    String readText = mEditText.getText() + " ";
                    String pointsText=mPointsText.getText()+ " ";
                    String courseCode= mEditTextCourseCodes.getText()+" ";
                    String postTitle=mPostTitle.getText()+" ";
                    System.out.println(pointsText);

                    System.out.println(pointsText.length());
                    if (readText.length() == 1)

                    if(pointsText.length()==1){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Please Enter Your Points", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        return;

                    }
                    //in this case if the Coures Code is not 7 symbols in length a toast will show

                    if(courseCode.length()==1){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Please Enter a Valid Course Id", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        return;
                    }
                    if(postTitle.length()==1){
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Please Enter a Title", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        return;
                    }


                    StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            System.out.print("Error Message PostQuestion"+error.getMessage());

                        }
                    })

                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("text", mEditText.getText().toString());
                            parameters.put("id", "0");
                            parameters.put("title",mPostTitle.getText().toString());
                            parameters.put("studentId", "1");
                            parameters.put("points", mPointsText.getText().toString());
                            parameters.put("selectedAnswerId", "0");
                            //TODO Change _1 to the schoolID that is attached to the user's geolocation
                            parameters.put("courseId", mEditTextCourseCodes.getText().toString()+SCHOOL_ID);
                            System.out.println(parameters + "parameters");
                            return parameters;
                        }
                    };
                    System.out.println("request " + request);
                    mrequestQueue.add(request);
                    finish();
                }

            });


        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id=item.getItemId();
            if(id==R.id.action_settings){
                return true;
            }
            if(id==android.R.id.home){
                NavUtils.navigateUpFromSameTask(this);
            }
            return super.onOptionsItemSelected(item);
        }
    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause Called");
        studentCourseList.clear();
    }

}