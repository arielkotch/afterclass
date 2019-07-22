package com.athena.athena.NavigationDrawer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.athena.athena.R;

public class changeProfile extends AppCompatActivity {
    public static final int PROFILE_CHOSEN=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        if(id==android.R.id.home){
            profileChosen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void profileChosen(){
        Intent returnIntent = getIntent();
        returnIntent.putExtra("result",PROFILE_CHOSEN);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
    public void onBackPressed(){
        //this ocurs when the user presses the back button in the hardware
        profileChosen();
    }
}

