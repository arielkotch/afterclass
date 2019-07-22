package com.athena.athena.NavigationDrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.athena.athena.Constants.Keys;
import com.athena.athena.MainActivitys.ImageLoading;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.Welcome.WelcomeFragments.ImageAdapter;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NavigationFragment extends Fragment implements PanelAdapter.ClickListener{
    //created a constant that represents the name of the file of shared preferences
    public static final String PREF_FILE_NAME = "testpref";
    //key for savedInstanceState
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private View containerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    //when the drawer is opened, it will store mUserLearnedDrawer inside a shared preferences file
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    //sync state synchronizes the state of the drawer indicator/affordane with the linked drawerlayout
    //keeps the navigation drawer in sync with whatever the user is doing
    private RecyclerView mRecyclerView;
    //created an instance of our adapter
    private PanelAdapter adapter;
    private Handler mHandler;
    private TextView mHome;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private TextView school;
    private TextView mUsername;
    private ImageView studentsImage;
    private static Context context;
    private ImageAdapter imageAdapter;
    private ImageView arrowDrop;
    static final int CHANGE_PROFILE = 1;  // The request code
    private TextView numberOfPoints;



    public NavigationFragment() {
        // Required empty public constructor

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment(inflating for a recyclerview)
        View layout_inflate = inflater.inflate(R.layout.fragment_navigation, container, false);
        //instantiated the Recyclerview=flexible view in a limited window for a large Data set
        mRecyclerView = (RecyclerView) layout_inflate.findViewById(R.id.drawer_recyclerview);
        //now we need a layout manager which is responsible for measuring and position item views within a RecyclerView
        //LinearLayout Manager=needed for a listview (scrolling)
        //added an instance of our adapter and intialized it here
        numberOfPoints=(TextView)layout_inflate.findViewById(R.id.numberOfPoints);
        adapter= new PanelAdapter(getActivity(),getData());
        studentsImage=(ImageView)layout_inflate.findViewById(R.id.userImage);

        //set the adapter on the recycler view here
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setClipToPadding(true);
        context=getContext();
        mRecyclerView.setClickable(true);
        //inflate the fragment_navigation layout, finds the textview and sets it to the username,
        //that the user logged in with
        getStudentImage();

        //TODO Fix display of the textview (mUsername)
        mUsername=(TextView)layout_inflate.findViewById(R.id.username);
        school=(TextView)layout_inflate.findViewById(R.id.school);

        mUsername.setText(Login.getUserName());
        AssetManager am = context.getApplicationContext().getAssets();
        Typeface robotoMed = Typeface.createFromAsset(am, "Roboto/Roboto-Medium.ttf");
        Typeface roboto = Typeface.createFromAsset(am, "Roboto/Roboto-Regular.ttf");
        arrowDrop=(ImageView)layout_inflate.findViewById(R.id.arrowDropDown);
        arrowDrop.getDrawable().setAlpha(100);
        mUsername.setTypeface(robotoMed);
        studentsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeProfile=new Intent(getActivity(), com.athena.athena.NavigationDrawer.changeProfile.class);
                startActivityForResult(changeProfile,CHANGE_PROFILE);
            }
        });
        school.setTypeface(roboto);

        //set layout manager
        //we want a linear one in this case
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setClickListener(this);
        return layout_inflate;
    }
    //getting data for the Drawer recyclerview

    public static List<AthenaPanel> getData()  {
        TextView mTextView;

        //created an object for ur Drawer recyclerview array
        List<AthenaPanel> data= new ArrayList<>();


        //this is where you would add all your icons for the drawer list
        //arrays of icons

        int[] icons={R.drawable.ic_search,R.drawable.ic_chat,R.drawable.ic_forum,R.drawable.ic_star,R.drawable.ic_settings};
        String[] titles = {"Explore","MyQuestions","MyAnswers","Get More Points","Settings"};

        //this is our for loop to cycle through the icons and the titles
        for(int i=0;i<5;i++){
            AthenaPanel current=new AthenaPanel();
            //i%().length allows ups to loop over the array any number of times we want to
            current.iconId=icons[i%icons.length];
            current.title=titles[i%titles.length];
            data.add(current);
        }
        return data;
    }


    public void setUp(int fragmentid, final DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentid);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawopen, R.string.drawopen) {

            @Override
            //called when drawer is completly opened
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //indicates that our drawer has been just viewed
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPrefrences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            //called when drawer is completly closed
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            //called when drawer changes position
            //this is the best option for animation of the drawer, as we want to the drawer to change with respect to the value of the drawer
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //we want to change the alpha value of our toolbar (1-slideOffset), will make it completly disappear, therefore we need to do and if condition to change this
                //therefore our animation will not darken any further once it gets past point 0.6 in our drawer
                //allows for restriction in our slideOffset
                if (slideOffset < 0.6)
                    toolbar.setAlpha(1 - slideOffset);
            }
        };
        //adds a hamburger icon
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }


    //saved to draw bar
    public static void saveToPrefrences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();

    }

    //read from drawbar
    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    @Override
    public void itemClicked(View view, int position) {
            //2 steps creating a fragment, create the fragment class and create the UI for it
            //we dont want it to return our place holder Fragment instead we want to switch bewtween 3
            //create a switch case based on the position applied or the fragment selected
            switch (position){
                case 0:
                    startActivity(new Intent(getActivity(),SearchBarActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(getActivity(),myQuestions.class));
                    break;
                case 2:
                    startActivity(new Intent(getActivity(),myAnswers.class));
                    break;
                case 3:
                    startActivity(new Intent(getActivity(),PaymentStore.class));
                    break;
                case 4:
                    startActivity(new Intent(getActivity(),Settings.class));
                    break;
            }
    }
    public void getStudentImage(){

        String url= Keys.URL_ADDERESS+"/api/Students/"+Login.getUserId()+"?access_token="+Login.getStudentAuth();
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println("The Json object is"+response);
                    String image=response.getString("image");
                    //this gets the students points
                    String points=response.getString("points");
                    numberOfPoints.setText(points);
                    ImageLoading setStudentImage=new ImageLoading(image,studentsImage,"profile");
                    setStudentImage.setImage();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                int result=data.getIntExtra("result",0);
                System.out.println("The Result is "+result);
                if(result== changeProfile.PROFILE_CHOSEN){
                    getStudentImage();
                    System.out.println("Student Image Set");
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
