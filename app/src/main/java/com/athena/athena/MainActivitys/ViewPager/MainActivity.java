package com.athena.athena.MainActivitys.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.athena.athena.Adapters.ViewpagerAdapter;
import com.athena.athena.MainActivitys.PostQuestion;
import com.athena.athena.MainActivitys.ViewPager.Fragments.Home;
import com.athena.athena.MainActivitys.ViewPager.Fragments.Points;
import com.athena.athena.MainActivitys.ViewPager.Fragments.Recent;
import com.athena.athena.MainActivitys.ViewPager.Fragments.SavedCourses;
import com.athena.athena.NavigationDrawer.NavigationFragment;
import com.athena.athena.Network.VolleySingleton;
import com.athena.athena.R;


public class MainActivity extends AppCompatActivity  {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Toolbar mToolbar;
    public static final int HOME = 0;

    public static final int TAGS = 3;
    public static final int MY_POINTS = 1;
    public static final int MY_RECENT = 2;
    public static boolean updatePreviousFragment=false;
    private ViewpagerAdapter viewpagerAdapter;
    public TextView mTextView;

    int size = 4;
    private ViewPager mViewPager;
    public static final int REFRESH_DELAY = 500;


    public interface updateViewpager{
        void update();
    }

    @Override
    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationFragment drawerlayout = (NavigationFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation);
        drawerlayout.setUp(R.id.fragment_navigation, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(size);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        System.out.println("About to Cycle Through Array");
        int y= tabLayout.getTabCount();
        System.out.println(y+"TAB COUNT");
        for (int i = 0; i < (tabLayout.getTabCount()); i++) {
            System.out.println(i+"COUNTER");
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mSectionsPagerAdapter.getTabView(i));
            }else{
                System.out.println("ERROR---TAB IS NULL---ERROR");
            }

        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, PostQuestion.class);
                    startActivity(i);
                }
            });
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateViewpager fragment = (updateViewpager) mSectionsPagerAdapter.instantiateItem(mViewPager, position);
                if (fragment != null) {
                    fragment.update();
                    System.out.println("The position in the fragment is "+position);
                }else{
                    System.out.println("Fragment is null");
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) layout.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
            StringRequest request = new StringRequest(Request.Method.GET, "http://www.php.net/", new Response.Listener<String>() {
                //once the data is downloaded we will get the response inside of here
                //onResponse if a callback interface for delievering parsed responses
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity(), "RESPONSE" + response, Toast.LENGTH_LONG).show();

                }
                //if an error occurs the errorlistener will be triggered
                //callback interface for delievering errors
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Error" + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

            requestQueue.add(request);
            return layout;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    //Viewpager Adapter Class
//fragment state pager adapter is when we want the fragment state saved across swiped
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        final int PAGE_COUNT = 4;
        private int[] icons = { R.drawable.ic_person, R.drawable.ic_grade,R.drawable.ic_access_time,R.drawable.ic_bookmark};

        public View getTabView(int position) {
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab_view, null);
            ImageView img = (ImageView) v.findViewById(R.id.tabImage);
            img.setImageResource(icons[position]);
            return v;
        }


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //2 steps creating a fragment, create the fragment class and create the UI for it
            Fragment fragment = null;
            //we dont want it to return our place holder Fragment instead we want to switch bewtween 3
            //create a switch case based on the position applied or the fragment selected
            switch (position) {
                case HOME:
                    fragment = Home.newInstance("", "");
                    break;
                case TAGS:
                    fragment = SavedCourses.newInstance("", "");
                    break;
                case MY_RECENT:
                    fragment = Recent.newInstance("", "");
                    break;
                case MY_POINTS:
                    fragment = Points.newInstance("", "");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            // return tabTitles[position];

            // getDrawable(int i) is deprecated, use getDrawable(int i, Theme theme) for min SDK >=21
            // or ContextCompat.getDrawable(Context context, int id) if you want support for older versions.
            // Drawable image = context.getResources().getDrawable(iconIds[position], context.getTheme());
            // Drawable image = context.getResources().getDrawable(imageResId[position]);

            Drawable image = ContextCompat.getDrawable(MainActivity.this,icons[position]);
            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            ImageSpan imageSpan = new ImageSpan(image);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("The Current item is "+mViewPager.getCurrentItem());
        if(updatePreviousFragment) {
            updateViewpager fragment = (updateViewpager) mSectionsPagerAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
            if (fragment != null) {
                fragment.update();
                updatePreviousFragment = false;
            }
        }
    }
}

