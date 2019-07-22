package com.athena.athena.Tests;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ToastTest {
    public static void m(String message){
        Log.d("Nice Job", "" + message);}
    public static void t(Context context,String message){
        //Toast.makeText(context,message+"",Toast.LENGTH_SHORT).show();
    }
}
