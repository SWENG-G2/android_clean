package com.penelope.faunafinder.xml.slide;

import static com.penelope.faunafinder.xml.slide.SlideConstants.SCREEN_WIDTH;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_HEIGHT;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_TITLE;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_WIDTH;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.test.core.app.ApplicationProvider;

import com.penelope.faunafinder.MainActivity;
import com.penelope.faunafinder.xml.slide.DetailSlide;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class DetailSlideTest {
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
        DetailSlide classUnderTest = new DetailSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);

        // All mdpi so 30dp (HORIZONTAL_MARGIN + HORIZONTAL_PADDING) is 30 pixels
        int offset = DetailSlide.HORIZONTAL_MARGIN + DetailSlide.HORIZONTAL_PADDING;
        assertEquals(SCREEN_WIDTH - offset, classUnderTest.getCalculatedWidth());
    }

    @Test
    public void slideSpecificsAreApplied() {
        DetailSlide classUnderTest = new DetailSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);
        Context context = ApplicationProvider.getApplicationContext();

        // Inflate detail slide layout
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.expandable_slide, parent);

        // Apply slide specifics
        classUnderTest.slideSpecifics(parent, context);

        // Grab title TextView
        TextView titleTextView = parent.findViewById(R.id.title);
        // Grab slide content itself
        RelativeLayout slide = parent.findViewById(R.id.slide);
        // Get res id of used background
        int actualBGResourceId = Shadows.shadowOf(parent.getBackground()).getCreatedFromResId();

        assertEquals(R.drawable.slide_bg, actualBGResourceId);
        assertEquals(SLIDE_TITLE, titleTextView.getText());
        assertEquals(View.GONE, slide.getVisibility());
    }

    @Test
    public void slideVisibilityCanBeToggled() {
        DetailSlide classUnderTest = new DetailSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);
        Context context = ApplicationProvider.getApplicationContext();

        // Inflate detail slide layout
        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.expandable_slide, parent);

        // Apply slide specifics
        classUnderTest.slideSpecifics(parent, context);

        // Grab clickable title
        RelativeLayout titleLayout = parent.findViewById(R.id.title_layout);
        // Grab slide content itself
        RelativeLayout slide = parent.findViewById(R.id.slide);

        // Slide should start as gone
        assertEquals(View.GONE, slide.getVisibility());

        // Slide should appear after click
        titleLayout.performClick();
        assertEquals(View.VISIBLE, slide.getVisibility());

        // Slide should disappear after second click
        titleLayout.performClick();
        assertEquals(View.GONE, slide.getVisibility());
    }
}
