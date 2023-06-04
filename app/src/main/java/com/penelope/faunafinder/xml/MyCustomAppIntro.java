package com.penelope.faunafinder.xml;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.penelope.faunafinder.MainActivity;
import com.penelope.faunafinder.R;
import com.github.appintro.AppIntroPageTransformerType;


public class MyCustomAppIntro extends AppIntro {

    SharedPreferences pref;
    // Enable color transitions

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get SharedPreferences
        pref = getSharedPreferences("intro", MODE_PRIVATE);

        // Check if we've shown the intro screens before
        if (!pref.getBoolean("firstTime", true)) {
            // If not the first time, skip to the main activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        addSlide(AppIntroFragment.createInstance(
                "Welcome to Fauna Finder!",
                "",
                R.drawable.fauna_finder_logo_circular__1_,
                R.color.dark_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                "Home Page",
                "Search for the campus using the search bar or selecting from the list",
                R.drawable.main_border,
                R.color.light_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                "Campus selection",
                "Search for the campus using the search bar or selecting from the list",
                R.drawable.main_empty_border,
                R.color.dark_orange
        ));

        addSlide(AppIntroFragment.createInstance(
                "Pick a Fauna",
                "Select the fauna you would like to identify and learn about from either searching using the search bar or selecting from the list",
                R.drawable.campus_border,
                R.color.light_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                "Information",
                "Here you can see the information on the wildlife. click on the icon on in the top right to hear what they sound like. tap the drop downs to see more information",
                R.drawable.detail_border,
                R.color.dark_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                "About me",
                "In the about me section you can see general information as well as a useful video showing the selected wildlife",
                R.drawable.detail1_border,
                R.color.light_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                "Diet",
                "In the diet section you can see a real life image of the wildlife and also learn about its diet",
                R.drawable.detail2_border,
                R.color.dark_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                "Location",
                "In the Location section you can see more images as well as information about where you may find this wildlife on your campus",
                R.drawable.detail3_border,
                R.color.light_orange
        ));
        addSlide(AppIntroFragment.createInstance(
                "Good Luck Finding your Fauna!",
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
        // Decide what to do when the user clicks on "Skip"
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Decide what to do when the user clicks on "Done"
        pref.edit().putBoolean("firstTime", false).apply();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
