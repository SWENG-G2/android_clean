package com.penelope.faunafinder.presentation.elements;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import com.penelope.faunafinder.MainActivity;
import com.penelope.faunafinder.R;
import com.penelope.faunafinder.xml.slide.BasicSlide;
import com.penelope.faunafinder.xml.slide.Slide;

@RunWith(RobolectricTestRunner.class)
public class AudioElementTest {
    private static final int SLIDE_WIDTH = 1920;
    private static final int SLIDE_HEIGHT = 200;
    private static final String SLIDE_TITLE = "TestTitle";

    private static final int X_COORDINATE = 100;

    private static final int Y_COORDINATE = 200;

    private MainActivity mainActivity;

    @Before
    public void setUp() {
        // set up MainActivity instance
        ActivityController<MainActivity> mainActivityActivityController = Robolectric.buildActivity(MainActivity.class).setup();
        mainActivity = mainActivityActivityController.get();
    }

    @Test
    public void applyViewIsCorrect() {
        // create RelativeLayout and add to main layout
        RelativeLayout relativeLayout = new RelativeLayout(mainActivity);
        ConstraintLayout constraintLayout = mainActivity.findViewById(R.id.main_activity);
        constraintLayout.addView(relativeLayout);

        Slide testSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);

        // create ImageButton and add to RelativeLayout
        ImageButton imageButton = new ImageButton(relativeLayout.getContext());
        relativeLayout.addView(imageButton);
        imageButton.setId(3000);

        // call the method under test and get the returned View
        AudioElement audioElement = new AudioElement(null, "TestLink.url", true, X_COORDINATE, Y_COORDINATE);
        View button = audioElement.applyView(relativeLayout, (ViewGroup) relativeLayout.getParent(), testSlide, 3000);

        // check for null and correct View
        assertNotNull(button);
        assertTrue(button instanceof ImageButton);

        // check the LayoutParams are correct
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
        assertEquals(audioElement.dpToPx(100), layoutParams.width);
        assertEquals(audioElement.dpToPx(100), layoutParams.height);
        assertEquals(Math.round(X_COORDINATE * testSlide.getCalculatedWidth() / (float) testSlide.getWidth()), layoutParams.leftMargin);
        assertEquals(Math.round(audioElement.dpToPx(Y_COORDINATE)), layoutParams.topMargin);

    }

    @Test
    public void mediaPlayerIsCorrect() {
        // mock MediaPlayer
        MediaPlayer mediaPlayer = mock(MediaPlayer.class);

        // create RelativeLayout and add to main layout
        RelativeLayout relativeLayout = new RelativeLayout(mainActivity);
        ConstraintLayout constraintLayout = mainActivity.findViewById(R.id.main_activity);
        constraintLayout.addView(relativeLayout);

        Slide testSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);

        // create ImageButton and add to RelativeLayout
        ImageButton imageButton = new ImageButton(relativeLayout.getContext());
        relativeLayout.addView(imageButton);
        imageButton.setId(3000);

        // create AudioElement object with the mock
        AudioElement audioElement = new AudioElement(mediaPlayer, "audio.mp3", true, 0, 0);
        View button = audioElement.applyView(relativeLayout, (ViewGroup) relativeLayout.getParent(), testSlide, 3000);

        button.performClick();

        // check that the methods were called correctly
        verify(mediaPlayer, times(1)).start();
    }

    @Test
    public void getViewTypeIsCorrect() {
        AudioElement audioElement = new AudioElement(null, "audio.mp3", true, 0, 0);
        assertEquals("audio", audioElement.getViewType());
    }

}
