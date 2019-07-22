package com.athena.athena.MainActivitys;

import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.athena.athena.Constants.Keys;
import com.athena.athena.MainActivitys.LoginActivitys.ModelType;
import com.athena.athena.Network.VolleySingleton;


public class ImageLoading {
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    private ImageLoader imageLoader;
    private String imageName;
    private ImageView imageView;
    private String modelType;

    public ImageLoading(String imageName, ImageView imageView, String modelType){
        this.imageName=imageName;
        this.modelType=modelType;
        this.imageView=imageView;
    }
    public void setImage(){
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        String urlThumbnail= Keys.URL_ADDERESS+"/api/Containers/"+modelType+"/download/"+imageName;
        System.out.println("THe image thumbnail is "+urlThumbnail);
        imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
            @Override

            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
            //TODO In the onResponse, Set the the imageview to the image recieved
                imageView.setImageBitmap(response.getBitmap());
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                //Set a default image, in case an image cannot be loaded
                System.out.println("On Error Called");
                System.out.println("Image Error "+error.getMessage());
            }
        });
    }



}
