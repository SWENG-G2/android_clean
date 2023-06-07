package com.penelope.faunafinder.presentation.elements;

import static com.penelope.faunafinder.presentation.elements.PresentationElement.IMAGE_ELEMENT;
import static com.penelope.faunafinder.presentation.elements.PresentationElement.displayMetrics;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.test.core.app.ApplicationProvider;

import com.penelope.faunafinder.xml.slide.BasicSlide;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@RunWith(RobolectricTestRunner.class)
public class ImageElementTest {

    private static final int ELEMENT_ID = 8008135;
    private static final String URL = "https://raw.githubusercontent.com/bumptech/glide/master/static/glide_logo.png";
    private RelativeLayout parent;
    private BasicSlide basicSlide;

    @Before
    public void setup(){
        Context appContext = ApplicationProvider.getApplicationContext();
        parent = new RelativeLayout(appContext);

        /*
            This is a depreciated function but its the only way that i could get an activity to
            properly run for the contentView.

            Without this imageView.getHandler() will return null, making the tests that make use
            of .postDelayed() fail as they are posted to a null Handler
         */
        Activity activity = Robolectric.setupActivity(Activity.class);
        activity.setContentView(parent);

        // Create basic container slide
        basicSlide = new BasicSlide(1920, 500, "Test slide");

        // Item must be already existing on screen for view re-usability in our project
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setId(ELEMENT_ID);
        parent.addView(imageView);
    }

    @Test
    public void getSearchableContentTest(){
        ImageElement ie = new ImageElement(URL,
                100,
                100,
                0, 0,
                0,
                0,
                0);

        assertNull(ie.getSearchableContent());
    }

    @Test
    public void getViewType() {
        ImageElement ie = new ImageElement(URL,
                100,
                100,
                0, 0,
                0,
                0,
                0);

        assertEquals(IMAGE_ELEMENT, ie.getViewType());
    }


    @Test
    public void applyViewHeightTest(){
        int width = 100;
        int x = 0;
        int y = 0;

        ArrayList<Integer> testHeights= new ArrayList<>();
        testHeights.add(1000);
        testHeights.add(-1);

        ArrayList<Integer> expectedHeights= new ArrayList<>();
        expectedHeights.add(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1000, displayMetrics)));
        expectedHeights.add(Math.round((width * basicSlide.getCalculatedWidth()) / (float) basicSlide.getWidth()));

        for(int i = 0; i < 2; i++){
            ImageElement ie = new ImageElement(URL, width, testHeights.get(i), x, y, 0, 0, 0);
            View testView = ie.applyView(parent, (ViewGroup) parent, basicSlide, ELEMENT_ID);
            int actualHeight = testView.getLayoutParams().height;
            int expected = expectedHeights.get(i);
            assertEquals(expected, actualHeight);
        }
    }

    @Test
    public void applyViewPositionTest(){
        int width = 500;
        int height = 400;
        int x = 100;
        int y = 100;

        ImageElement ie = new ImageElement(URL,
                width,
                height,
                x, y,
                0,
                0,
                0);

        View testView = ie.applyView(parent, (ViewGroup) parent, basicSlide, ELEMENT_ID);

        int expectedLeft = Math.round((x * basicSlide.getCalculatedWidth()) / (float) basicSlide.getWidth());
        int expectedTop = ie.dpToPx(y);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) testView.getLayoutParams();

        assertEquals(expectedLeft, params.leftMargin);
        assertEquals(expectedTop, params.topMargin);
    }

    @Test
    public void applyViewRotationTest(){
        int width = 500;
        int height = 400;
        int x = 100;
        int y = 100;
        int rot = 57;

        ImageElement ie = new ImageElement(URL,
                width,
                height,
                x, y,
                rot,
                0,
                0);

        View testView = ie.applyView(parent, (ViewGroup) parent, basicSlide, ELEMENT_ID);
        assertEquals(rot, (int)testView.getRotation());
    }

    @Test
    public void applyViewWidthTest(){
        int height= 100;
        ArrayList<Integer> testWidths= new ArrayList<>();
        testWidths.add(100);
        testWidths.add(300);
        testWidths.add(1000);

        ArrayList<Integer> expectedWidths= new ArrayList<>();
        expectedWidths.add(Math.round(100* basicSlide.getCalculatedWidth()/(float) basicSlide.getWidth()));
        expectedWidths.add(Math.round(300* basicSlide.getCalculatedWidth()/(float) basicSlide.getWidth()));
        expectedWidths.add(Math.round(1000* basicSlide.getCalculatedWidth()/(float) basicSlide.getWidth()));

        for(int i=0;i<3;i++){
            ImageElement ie = new ImageElement(URL,
                    testWidths.get(i),
                    height,
                    0, 0,
                    0,
                    0,
                    0);
            View testView = ie.applyView(parent,(ViewGroup) parent,basicSlide,ELEMENT_ID);
            int actualWidth= testView.getLayoutParams().width;
            int expected = expectedWidths.get(i);
            assertEquals(expected, actualWidth);
        }
    }

    @Test
    public void applyViewVisibilityTimerTest(){
        long tos = 100;
        long del = 300;

        ImageElement ie = new ImageElement(URL,
                100,
                100,
                0, 0,
                0,
                del,
                tos);

        View testView = ie.applyView(parent, (ViewGroup) parent, basicSlide, ELEMENT_ID);
        assertEquals(View.INVISIBLE, testView.getVisibility());
        Robolectric.getForegroundThreadScheduler().advanceBy(del, TimeUnit.MILLISECONDS);
        assertEquals(View.VISIBLE, testView.getVisibility());
        Robolectric.getForegroundThreadScheduler().advanceBy(tos, TimeUnit.MILLISECONDS);
        assertEquals(View.INVISIBLE, testView.getVisibility());
    }

    @Test @Config(qualifiers = "w1440dp-h3120dp") //resolution of portrait Google Pixel 6
    public void applyViewExampleDataTest(){
        ImageElement ie = new ImageElement(URL, 1700, 360, -2, 115, 45, 1000, 5000);
        View testView = ie.applyView(parent, (ViewGroup) parent, basicSlide, ELEMENT_ID);
        System.out.println(basicSlide.getCalculatedWidth());

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) testView.getLayoutParams();

        assertEquals(115, params.topMargin);
        assertEquals(1275, params.width);
        assertEquals(360, params.height);

        assertEquals(View.INVISIBLE, testView.getVisibility());
        Robolectric.getForegroundThreadScheduler().advanceBy(1000, TimeUnit.MILLISECONDS);
        assertEquals(View.VISIBLE, testView.getVisibility());
        Robolectric.getForegroundThreadScheduler().advanceBy(5000, TimeUnit.MILLISECONDS);
        assertEquals(View.INVISIBLE, testView.getVisibility());
    }

    @Test
    public void applyViewRuleTest(){
        int y = 0, rot = 0, del = 0, timeOnScreen = 0;
        int width = 100, height = 100;

        ArrayList<Integer> inputX = new ArrayList<>();
        inputX.add(0);
        inputX.add(-3);
        inputX.add(-2);

        ArrayList<Integer> expectedRule = new ArrayList<>();
        expectedRule.add(0);
        expectedRule.add(RelativeLayout.ALIGN_PARENT_RIGHT);
        expectedRule.add(RelativeLayout.CENTER_HORIZONTAL);

        for(int i = 0; i < inputX.size()-1; i++){
            ImageElement ie = new ImageElement(URL, width, height, inputX.get(i), y, rot, del, timeOnScreen);
            View testView = ie.applyView(parent, (ViewGroup) parent, basicSlide, ELEMENT_ID);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) testView.getLayoutParams();

            for(int r = 0; r < expectedRule.size()-1; r++){
                //RelativeLayout.TRUE when not first case and r == i
                int expected = i == r && i != 0 ? RelativeLayout.TRUE : 0;
                assertEquals(expected, params.getRule(expectedRule.get(r)));
            }
        }
    }
}