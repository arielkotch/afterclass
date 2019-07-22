package com.athena.athena.Testing;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/*A java.lang.indexOutOfBoundsException Occured in the main thread
 /  in response we will wrap the layout manager in a wrapper class,
 / and if the indexOutOfBounds exception occurs we will catch this exceptio
 */
public class WrapContentLinearLayoutManager extends LinearLayoutManager {
    public WrapContentLinearLayoutManager(Context context) {
        super(context);
    }
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("****Index Out Of Bounds Exception Occured****");
            Log.e("probe", "meet a IOOBE in RecyclerView");
            System.out.println(e.getMessage());
        }
    }
}
