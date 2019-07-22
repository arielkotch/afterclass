package com.athena.athena.NavigationDrawer;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.athena.athena.Adapters.AdapterCourses;
import com.athena.athena.Adapters.ViewpagerAdapter;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.ViewPager.Fragments.SavedCourses;
import com.athena.athena.R;

import java.util.ArrayList;


public class Settings extends AppCompatActivity  {
    private static final int END_ALL_ACTIVITY=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Toolbar mToolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items 2to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    /*
    Proper Procedure for logging out of an android application
    *
     */
    public void logOut(View view) {
        Intent logout=new Intent(getBaseContext(),Login.class);
        //clear viewpager adapter data before exiting
        TaskStackBuilder.create(this).addNextIntentWithParentStack(logout).startActivities();
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        System.exit(END_ALL_ACTIVITY);
        startActivity(logout);
        finish();
    }
}
