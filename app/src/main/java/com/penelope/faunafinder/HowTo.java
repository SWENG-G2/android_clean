package com.penelope.faunafinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;


public class HowTo extends AppIntro {

    private SharedPreferences pref;
    private boolean manualRun = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get SharedPreferences
        pref = getSharedPreferences(getString(R.string.introConfiguration), MODE_PRIVATE);

        manualRun = getIntent().getBooleanExtra(getString(R.string.runIntro), false);

        // Check if we've shown the intro screens before
        if (!pref.getBoolean("firstTime", true) && !manualRun) {
            // If not the first time, skip to the main activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        addSlide(AppIntroFragment.createInstance(
                getString(R.string.welcome),
                "",
                R.drawable.fauna_finder_logo_circular__1_,
                R.color.dark_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                getString(R.string.home_page),
                getString(R.string.main_empty_usage),
                R.drawable.main_border,
                R.color.light_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                getString(R.string.campus_selection),
                getString(R.string.campus_select_usage),
                R.drawable.main_empty_border,
                R.color.dark_orange
        ));

        addSlide(AppIntroFragment.createInstance(
                getString(R.string.pick_fauna),
                getString(R.string.bird_selection_usage),
                R.drawable.campus_border,
                R.color.light_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                getString(R.string.info),
                getString(R.string.info_pages1),
                R.drawable.detail_border,
                R.color.dark_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                getString(R.string.about_me),
                getString(R.string.about_me_info),
                R.drawable.detail1_border,
                R.color.light_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                getString(R.string.diet),
                getString(R.string.diet_info),
                R.drawable.detail2_border,
                R.color.dark_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                getString(R.string.location),
                getString(R.string.location_info),
                R.drawable.detail3_border,
                R.color.light_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                getString(R.string.luck),
                "",
                R.drawable.fauna_finder_logo_circular__1_,
                R.color.dark_orange
        ));

        setTransformer(new AppIntroPageTransformerType.Parallax(
                -1.0,
                -1.0,
                -1.0
        ));
        setColorTransitionsEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        if (manualRun)
            finish();

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        if (manualRun)
            finish();

        pref.edit().putBoolean("firstTime", false).apply();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
