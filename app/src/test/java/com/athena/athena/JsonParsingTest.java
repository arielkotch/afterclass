package com.athena.athena;

import android.app.Application;
import android.content.Context;

import com.athena.athena.Adapters.ViewpagerAdapter;
import com.athena.athena.Constants.Keys;
import com.athena.athena.MainActivitys.LoginActivitys.Login;
import com.athena.athena.MainActivitys.ViewPager.JsonRequests;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class JsonParsingTest {
    private static final String URL_HOME_ORDERED=Keys.URL_ADDERESS+"/api/Questions/ordered?access_token="+ Login.getStudentAuth();

    @Test
    public void testJsonParse()  {

        Context context;
        //Parameters(ViewpagerAdapter viewpagerAdapter, String url, String key)
        ViewpagerAdapter viewPager=null;

        JsonRequests jsonRequests=new JsonRequests(viewPager, URL_HOME_ORDERED,Keys.HOME_KEY);

    }

}