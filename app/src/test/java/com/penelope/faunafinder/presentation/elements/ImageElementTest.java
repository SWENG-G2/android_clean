package sweng.campusbirdsguide.presentation.elements;

import static org.junit.Assert.*;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.RequestManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowLooper;

import sweng.campusbirdsguide.MainActivity;
import sweng.campusbirdsguide.R;
import sweng.campusbirdsguide.xml.slide.BasicSlide;
import sweng.campusbirdsguide.xml.slide.Slide;

@RunWith(RobolectricTestRunner.class)
public class ImageElementTest {
    private static final int SLIDE_WIDTH = 1920;
    private static final int SLIDE_HEIGHT = 200;
    private static final String SLIDE_TITLE = "TestTitle";

    private static final String URL = "TestingIsGreat.url";
    private static final int X_COORDINATE = 100;
    private static final int Y_COORDINATE = 200;
    private static final int WIDTH = 1500;
    private static final int HEIGHT = 300;
    private static final int ROTATION = 2;
    private static final long DELAY = 15;
    private static final long TIME_ON_SCREEN = 1696;

    private ImageElement imageElement;

    private MainActivity birdActivity;

    @Before
    public void setUp() {
        ActivityController<MainActivity> birdActivityActivityController = Robolectric.buildActivity(MainActivity.class).setup();
        birdActivity = birdActivityActivityController.get();

        imageElement = new ImageElement(URL, WIDTH, HEIGHT, X_COORDINATE, Y_COORDINATE, ROTATION, DELAY, TIME_ON_SCREEN);
    }

    @Test
    public void applyViewIsCorrect() {
        // create RelativeLayout and add to main layout
        RelativeLayout relativeLayout = new RelativeLayout(birdActivity);
        ConstraintLayout constraintLayout = birdActivity.findViewById(R.id.main_activity);
        constraintLayout.addView(relativeLayout);

        Slide testSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);

        // create imageView object and add to relative layout
        ImageView imageView = new ImageView(relativeLayout.getContext());
        relativeLayout.addView(imageView);
        imageView.setId(3000);

        // call the method under test and get the returned View
        View view = imageElement.applyView(relativeLayout, (ViewGroup) relativeLayout.getParent(), testSlide, 3000);

        // check for null and correct View
        assertNotNull(view);
        assertTrue(view instanceof ImageView);

        // perform visibility checks
        if (DELAY > 0) {
            view.setVisibility(View.INVISIBLE);
            assertEquals(View.INVISIBLE, view.getVisibility());
            ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
            view.setVisibility(View.VISIBLE);
            assertEquals(View.VISIBLE, view.getVisibility());
        }

        // perform LayoutParams checks
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        assertEquals(Math.round(X_COORDINATE * testSlide.getCalculatedWidth() / (float) testSlide.getWidth()), layoutParams.leftMargin);
        assertEquals(imageElement.dpToPx(Y_COORDINATE), layoutParams.topMargin);
        assertEquals(Math.round(WIDTH * testSlide.getCalculatedWidth() / (float) testSlide.getWidth()), layoutParams.width);
        assertEquals(imageElement.dpToPx(HEIGHT), layoutParams.height);

        assertEquals(ROTATION, view.getRotation(), 0.001);

        // mock Glide and verify load was called
        RequestManager glide = Mockito.mock(RequestManager.class);
        String serverURL = relativeLayout.getContext().getResources().getString(R.string.serverURL);
        glide.load(serverURL + URL);
        Mockito.verify(glide);
    }

    @Test
    public void getViewTypeIsCorrect() {
        assertEquals("image", imageElement.getViewType());
    }

}