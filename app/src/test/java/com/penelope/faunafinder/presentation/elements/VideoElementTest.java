package com.penelope.faunafinder.presentation.elements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static com.penelope.faunafinder.presentation.elements.PresentationElement.VIDEO_ELEMENT;

import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.exoplayer2.ui.StyledPlayerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import com.penelope.faunafinder.MainActivity;
import com.penelope.faunafinder.presentation.elements.VideoElement;
import com.penelope.faunafinder.xml.slide.BasicSlide;

@RunWith(RobolectricTestRunner.class)
public class VideoElementTest {
    private static final String VIDEO_URL = "Test url";
    private static final int WIDTH = 100;
    private static final int HEIGHT = 20;
    private static final int X = 20;
    private static final int Y = 200;
    private static final int VIEW_ID = 69420;
    private static final int SLIDE_WIDTH = 320;
    private static final int SLIDE_HEIGHT = 500;
    private static final String SLIDE_TITLE = "Test slide title";

    private ConstraintLayout constraintLayout;
    private RelativeLayout parent;
    private BasicSlide slide;
    private StyledPlayerView styledPlayerView;

    @Before
    public void setUp() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            // Dummy activity
            MainActivity mainActivity = controller.get();

            // Create StyledPlayerView used by VideoElement
            LayoutInflater layoutInflater = LayoutInflater.from(mainActivity);
            styledPlayerView = (StyledPlayerView) layoutInflater.inflate(R.layout.player_view, null);

            // Dummy slide
            slide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);

            // The upper view group
            constraintLayout = mainActivity.findViewById(R.id.main_activity);

            // The "slide"
            parent = new RelativeLayout(mainActivity);

            constraintLayout.addView(parent);
            parent.addView(styledPlayerView);

            // Set StyledPlayerView ID so VideoElement can grab it
            styledPlayerView.setId(VIEW_ID);
        }
    }

    @Test
    public void elementIsLaidOut() {
        VideoElement classUnderTest = new VideoElement(VIDEO_URL, WIDTH, HEIGHT, X, Y, false);

        // This would be called when slides are being laid out
        classUnderTest.applyView(parent, constraintLayout, slide, VIEW_ID);

        // The VideoElement View's is the StyledPlayerView so we want to assert that
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) styledPlayerView.getLayoutParams();

        // Width should be as provided. Robolectric default device is mdpi with 320px width.
        assertEquals(WIDTH, layoutParams.width);
        // Being mdpi, height should be as provided.
        assertEquals(HEIGHT, layoutParams.height);
    }

    @Test
    public void getViewTypeIsCorrect() {
        VideoElement classUnderTest = new VideoElement(VIDEO_URL, WIDTH, HEIGHT, X, Y, false);

        assertEquals(VIDEO_ELEMENT, classUnderTest.getViewType());
    }

    @Test
    public void getSearchableContentIsCorrect() {
        VideoElement classUnderTest = new VideoElement(VIDEO_URL, WIDTH, HEIGHT, X, Y, false);

        assertNull(classUnderTest.getSearchableContent());
    }
}
