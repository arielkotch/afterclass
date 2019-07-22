package com.athena.athena.MainActivitys.Welcome;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.athena.athena.MainActivitys.ViewPager.MainActivity;
import com.athena.athena.MainActivitys.Welcome.WelcomeFragments.LoadingScreen;
import com.athena.athena.MainActivitys.Welcome.WelcomeFragments.PickYourProfileSlide;
import com.athena.athena.MainActivitys.Welcome.WelcomeFragments.ChooseClassSlide;
import com.athena.athena.MainActivitys.Welcome.WelcomeFragments.AnonymousSlide;
import com.athena.athena.MainActivitys.Welcome.WelcomeFragments.StudentRun;
import com.athena.athena.R;
import com.github.paolorotolo.appintro.AppIntro;


public class WelcomeSlides extends AppIntro {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO Re-assign loading screen to end of addSlide()
        //addSlide(LoadingScreen.newInstance(R.layout.logo_loading_screen));
        addSlide(ChooseClassSlide.newInstance(R.layout.choose_your_classes));
        addSlide(AnonymousSlide.newInstance(R.layout.anonymous));
        addSlide(StudentRun.newInstance(R.layout.student_run));
        addSlide(PickYourProfileSlide.newInstance(R.layout.pick_your_profile));
        showSkipButton(false);
    }
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(WelcomeSlides.this, MainActivity.class));
    }
}
