package sweng.campusbirdsguide.presentation.elements;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class TextElementTest {
    private static final int SLIDE_WIDTH = 1920;
    private static final int SLIDE_HEIGHT = 450;
    private static final String SLIDE_TITLE = "TestTitle";
    private static final String FONT = "sans-serif";
    private static final int FONT_SIZE = 18;
    private static final int COLOUR = Color.BLACK;
    private static final long TIME_ON_SCREEN = 5000;
    private static final int WIDTH = 250;
    private static final int HEIGHT = 75;
    private static final int X_COORDINATE = 100;
    private static final int Y_COORDINATE = 200;

    private TextElement textElement;

    private MainActivity birdActivity;

    @Before
    public void setUp() {
        ActivityController<MainActivity> birdActivityActivityController = Robolectric.buildActivity(MainActivity.class).setup();
        birdActivity = birdActivityActivityController.get();

        textElement = new TextElement(FONT, FONT_SIZE, COLOUR, X_COORDINATE, Y_COORDINATE, WIDTH, HEIGHT, TIME_ON_SCREEN);
    }

    @Test
    public void applyViewIsCorrect() {
        // create RelativeLayout and add to main layout
        RelativeLayout relativeLayout = new RelativeLayout(birdActivity);
        ConstraintLayout constraintLayout = birdActivity.findViewById(R.id.main_activity);
        constraintLayout.addView(relativeLayout);

        Slide testSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);

        // create TextView object and add to relative layout
        TextView textView = new TextView(relativeLayout.getContext());
        relativeLayout.addView(textView);
        textView.setId(3000);

        // call the method under test and get the returned View
        textElement.setContent("Chris Bumstead is huge");
        View view = textElement.applyView(relativeLayout, (ViewGroup) relativeLayout.getParent(), testSlide, 3000);

        // check for null and correct View
        assertNotNull(view);
        assertTrue(view instanceof TextView);

        // verify correct text, font size and colour
        TextView resultTextView = (TextView) view;
        assertEquals("Chris Bumstead is huge", resultTextView.getText().toString());
        assertEquals(18, resultTextView.getTextSize(), 0.01);
        assertEquals(Color.BLACK, resultTextView.getCurrentTextColor());

        // perform LayoutParams checks
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) resultTextView.getLayoutParams();
        assertEquals(Math.round(WIDTH * testSlide.getCalculatedWidth() / (float) testSlide.getWidth()), layoutParams.width);
        assertEquals(textElement.dpToPx(HEIGHT), layoutParams.height);
        assertEquals(Math.round(X_COORDINATE * testSlide.getCalculatedWidth() / (float) testSlide.getWidth()), layoutParams.leftMargin);
        assertEquals(textElement.dpToPx(Y_COORDINATE), layoutParams.topMargin);

        // mock TextView and Typeface
        TextView fontView = mock(TextView.class);
        Typeface typeface = mock(Typeface.class);

        // set-up mock objects
        when(fontView.getTypeface()).thenReturn(typeface);
        when(typeface.toString()).thenReturn("sans-serif");

        // get font name from the mock and assert
        String fontName = fontView.getTypeface().toString();
        assertEquals(textElement.getFont(), fontName);

        // verify TextView disappears after 5 seconds
        resultTextView.postDelayed(() -> assertEquals(View.INVISIBLE, resultTextView.getVisibility()), 5000);
    }

    @Test
    public void getViewTypeIsCorrect() {
        assertEquals("text", textElement.getViewType());
    }

    @Test
    public void getSearchableContentIsCorrect() {
        textElement.setContent("i WaNt PaNcAkEs");
        assertEquals("i want pancakes", textElement.getSearchableContent());
    }

}
