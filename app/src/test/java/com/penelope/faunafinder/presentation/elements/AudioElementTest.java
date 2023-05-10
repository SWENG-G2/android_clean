package sweng.campusbirdsguide.presentation.elements;

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

import sweng.campusbirdsguide.MainActivity;
import sweng.campusbirdsguide.R;
import sweng.campusbirdsguide.xml.slide.BasicSlide;
import sweng.campusbirdsguide.xml.slide.Slide;

@RunWith(RobolectricTestRunner.class)
public class AudioElementTest {
    private static final int SLIDE_WIDTH = 1920;
    private static final int SLIDE_HEIGHT = 200;
    private static final String SLIDE_TITLE = "TestTitle";

    private static final int X_COORDINATE = 100;

    private static final int Y_COORDINATE = 200;

    private AudioElement audioElement;

    private MainActivity birdActivity;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Before
    public void setUp() {
        // set up MainActivity instance
        ActivityController<MainActivity> birdActivityActivityController = Robolectric.buildActivity(MainActivity.class).setup();
        birdActivity = birdActivityActivityController.get();

        // set up AudioElement instance
        audioElement = new AudioElement(mediaPlayer, "TestLink.url", true, X_COORDINATE, Y_COORDINATE);
    }

    @Test
    public void applyViewIsCorrect() {
        // create RelativeLayout and add to main layout
        RelativeLayout relativeLayout = new RelativeLayout(birdActivity);
        ConstraintLayout constraintLayout = birdActivity.findViewById(R.id.main_activity);
        constraintLayout.addView(relativeLayout);

        Slide testSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);

        // create ImageButton and add to RelativeLayout
        ImageButton imageButton = new ImageButton(relativeLayout.getContext());
        relativeLayout.addView(imageButton);
        imageButton.setId(3000);

        // call the method under test and get the returned View
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

        // create AudioElement object with the mock
        AudioElement audioElement = new AudioElement(mediaPlayer, "audio.mp3", true, 0, 0);

        // call interaction methods
        audioElement.start();
        audioElement.pause();
        audioElement.seekTo(6969);

        // check that the methods were called correctly
        verify(mediaPlayer, times(1)).start();
        verify(mediaPlayer, times(1)).pause();
        verify(mediaPlayer, times(1)).seekTo(6969);
    }

    @Test
    public void getViewTypeIsCorrect() {
        assertEquals("audio", audioElement.getViewType());
    }

}
