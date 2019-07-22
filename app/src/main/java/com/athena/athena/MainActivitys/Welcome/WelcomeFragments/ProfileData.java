package com.athena.athena.MainActivitys.Welcome.WelcomeFragments;


public class ProfileData {
    String profile;
    int drawable;


     public ProfileData(int drawable,String profile){
     this.profile=profile;
        this.drawable=drawable;
    }
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }



}
