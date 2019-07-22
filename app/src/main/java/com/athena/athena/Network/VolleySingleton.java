package com.athena.athena.Network;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton sSingleton = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;

    private VolleySingleton() {
        //intialize our requestqueue
        //parameters()
            mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext().getApplicationContext());
            //ImageLoader- helper that handles loading and caching images from remote URLS. the simple way to use this class is to call get(String,ImageListener) and to pass in the default image listener provided by getImageListener
            //ImageLoader(RequestQueue queue, object of instance cachce)
            //imagecache- is a simple cache adapter interface, if provided to the imageloader it will be used as an L1 cache before dispatch to volley
            //added the request to the requestqueue
              mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            //LRuCache Class-holds strong references to a limited number of values, each time it is accessed it is moved to the head of the queue, when full the value at the end is evicted and may become eligible for garbage collection
            //specifiy size via java max memory constraint(max memory is total memory)
            //maxmemory is in bytes divide by 1024 to get kilobytes
            //divide by 8 to get sizeof cache
            private LruCache<String, Bitmap> cache = new LruCache<>((int) Runtime.getRuntime().maxMemory() / 1024 / 8);

            @Override
            public Bitmap getBitmap(String url) {

                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);

            }
        });

    }

    //if our object is equal to null we are going to want to create a new instance of it
    public static VolleySingleton getInstance() {
        if (sSingleton == null) {
            sSingleton = new VolleySingleton();
        }
        return sSingleton;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}

