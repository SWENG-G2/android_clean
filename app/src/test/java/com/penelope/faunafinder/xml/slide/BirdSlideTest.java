package com.penelope.faunafinder.xml.slide;

import static com.penelope.faunafinder.xml.slide.SlideConstants.SCREEN_WIDTH;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_HEIGHT;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_TITLE;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_WIDTH;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.test.core.app.ApplicationProvider;

import com.penelope.faunafinder.MainActivity;
import com.penelope.faunafinder.R;
import com.penelope.faunafinder.xml.slide.BirdSlide;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class BirdSlideTest {
    private RelativeLayout parent;

    @Before
    public void setUp() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            // Dummy activity
            MainActivity mainActivity = controller.get();

            // The upper view group
            ConstraintLayout constraintLayout = mainActivity.findViewById(R.id.main_activity);

            // The "slide"
            parent = new RelativeLayout(mainActivity);

            constraintLayout.addView(parent);
        }
    }

    @Test
    public void widthIsAdjusted() {
        BirdSlide classUnderTest = new BirdSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);

        // All mdpi so 10dp (HORIZONTAL_MARGIN) is 10 pixels
        assertEquals(SCREEN_WIDTH - BirdSlide.HORIZONTAL_MARGIN, classUnderTest.getCalculatedWidth());
    }

    @Test
    public void slideSpecificsAreApplied() {
        BirdSlide classUnderTest = new BirdSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);
        Context context = ApplicationProvider.getApplicationContext();

        // Apply slide specifics, changes parent background
        classUnderTest.slideSpecifics(parent, context);

        // Get res id of used background
        int actualResourceId = Shadows.shadowOf(parent.getBackground()).getCreatedFromResId();

        // Bg should be R.drawable.slide_bg
        assertEquals(R.drawable.slide_bg, actualResourceId);
    }
}
