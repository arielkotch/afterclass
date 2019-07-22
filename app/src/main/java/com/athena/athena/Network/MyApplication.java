package com.athena.athena.Network;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application{
    //created an object that extends application
    //it inherits all of applications methods
    private static MyApplication appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext=this;
    }
    public static Context getAppContext(){
        return appContext.getApplicationContext();

    }
}
