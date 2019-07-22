package com.athena.athena.MainActivitys.ViewPager.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.athena.athena.Adapters.AdapterCourses;
import com.athena.athena.Constants.Keys;
import com.athena.athena.Information.Search;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.ViewPager.MainActivity;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;
import com.athena.athena.Testing.WrapContentLinearLayoutManager;
import com.yalantis.phoenix.PullToRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SavedCourses extends Fragment implements MainActivity.updateViewpager {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private CheckBox checkBox;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private static final String TAG="SavedCourses";
    private RecyclerView mRecyclerView;
    private PullToRefreshView mPullToRefreshView;
    public static final int REFRESH_DELAY=500;
    public static ArrayList<Search> ListSearch = new ArrayList<>();
    private AdapterCourses mAdapterDashBoard;
    //{"where":{"key":"JWST430"}}
    final String URL_SAVED_COURSES = Keys.URL_ADDERESS+"/api/Students/"+ Login.getUserId()+"/courses?access_token="+Login.getStudentAuth();
    public static SavedCourses newInstance(String param1, String param2) {
        SavedCourses fragment = new SavedCourses();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SavedCourses() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        JsonRequestMethod();
    }

    private void JsonRequestMethod() {
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL_SAVED_COURSES, (String) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ListSearch.clear();
                ListSearch = parseJSONResponseQuestion(response);
                mAdapterDashBoard.setTagsList(ListSearch);
                System.out.println(response);
                if(response.length()<1) {
                    Log.d(TAG,"The Array is Empty");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);

            }
        });
        mRequestQueue.add(request);
    }

    private ArrayList<Search> parseJSONResponseQuestion(JSONArray response) {
        if (!response.equals("")) {
            ArrayList<Search> SearchArrayList = new ArrayList<>();
            try {
                StringBuilder data = new StringBuilder();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject currentQuestions = response.getJSONObject(i);
                    String name = currentQuestions.getString("name");
                    String code = currentQuestions.getString("code");
                    String department = currentQuestions.getString("department");
                    String instructor = currentQuestions.getString("instructor");
                    String image = currentQuestions.getString("image");

                    data.append(name + "\n"+code + "\n"+department + "\n"+ "\n");
                    System.out.println(data);
                    Search search = new Search();
                    search.setName(name);
                    search.setCode(code);
                    search.setImage(image);
                    search.setDepartment(department);
                    search.setInstructor(instructor);

                    ListSearch.add(search);
                }
                System.out.println(data.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ListSearch;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_dashboard_recycler);
        mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
        mAdapterDashBoard = new AdapterCourses(getActivity());
        mRecyclerView.setAdapter(mAdapterDashBoard);

        mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        JsonRequestMethod();
                        ListSearch.clear();
                    }
                }, REFRESH_DELAY);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        JsonRequestMethod();
    }
    @Override
    public void update() {
        //TODO Implement update in Tags
    }
}
