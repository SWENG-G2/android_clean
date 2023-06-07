package com.penelope.faunafinder.presentation.elements;
import static com.penelope.faunafinder.presentation.elements.PresentationElement.MATCH_PARENT;
import static com.penelope.faunafinder.presentation.elements.PresentationElement.WRAP_CONTENT;
import static com.penelope.faunafinder.presentation.elements.PresentationElement.displayMetrics;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.test.platform.app.InstrumentationRegistry;
import com.penelope.faunafinder.R;
import com.penelope.faunafinder.xml.slide.BasicSlide;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Implements;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@RunWith(RobolectricTestRunner.class)
@Implements(ResourcesCompat.class)
public class TextElementUnitTest{

   RelativeLayout parent;
    BasicSlide basicSlide;
   int ELEMENT_ID = 8008135;

    @Before
    public void setUpTest() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        parent= new RelativeLayout(appContext);
        Activity activity = Robolectric.setupActivity(Activity.class);
        activity.setContentView(parent);
        basicSlide = new BasicSlide(1920, 500, "Test slide");
        TextView textView = new TextView(parent.getContext());
        textView.setId(ELEMENT_ID);
        parent.addView(textView);
    }
    @Test
    public void widthIsSetCorrectly(){
       int height= 100;
       ArrayList<Integer> testWidths = new ArrayList<>();
       testWidths.add(100);
       testWidths.add(MATCH_PARENT);
       testWidths.add(WRAP_CONTENT);

        ArrayList<Integer> expectedWidths= new ArrayList<>();
        expectedWidths.add(Math.round(100* basicSlide.getCalculatedWidth()/(float) basicSlide.getWidth()));
        expectedWidths.add(RelativeLayout.LayoutParams.MATCH_PARENT);
        expectedWidths.add(RelativeLayout.LayoutParams.WRAP_CONTENT);

        for(int i=0;i<3;i++){
            TextElement textElement = new TextElement(null, 32, Color.BLACK, 100, 500, (int)testWidths.get(i), height, 5000L);
            textElement.setContent("Test input");
            View testView = textElement.applyView(parent,(ViewGroup) parent,basicSlide,ELEMENT_ID);
            int actualWidth= testView.getLayoutParams().width;
            int expected = expectedWidths.get(i);
            assertEquals(expected, actualWidth);
        }
    }
    @Test
    public void heightIsSetCorrectly(){
        int width= 100;
        ArrayList<Integer> testHeights= new ArrayList<>();
        testHeights.add(100);
        testHeights.add(MATCH_PARENT);
        testHeights.add(WRAP_CONTENT);

        ArrayList<Integer> expectedHeights= new ArrayList<>();
        expectedHeights.add(Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, displayMetrics)));
        expectedHeights.add(RelativeLayout.LayoutParams.MATCH_PARENT);
        expectedHeights.add(RelativeLayout.LayoutParams.WRAP_CONTENT);

        for(int i=0;i<3;i++){
            TextElement textElement = new TextElement(null, 32, Color.BLACK, 100, 500,width, (int)testHeights.get(i), 5000L);
            textElement.setContent("Test input");
            View testView= textElement.applyView(parent,(ViewGroup) parent,basicSlide,ELEMENT_ID);
            int actualHeight= testView.getLayoutParams().height;
            int expected = expectedHeights.get(i);
            assertEquals(expected, actualHeight);
        }
    }
    @Test
    public void fontIsSetCorrectly() {
       ArrayList<String> fonts= new ArrayList<>();
       fonts.add("mono");
       fonts.add("roboto");

       ArrayList<Typeface> typefaces= new ArrayList<>();
       typefaces.add(ResourcesCompat.getFont(parent.getContext(), R.font.chivo_mono_regular));
       typefaces.add(ResourcesCompat.getFont(parent.getContext(),  R.font.roboto_condensed_regular));

       for(int i=0;i< fonts.size();i++){
           TextElement textElement = new TextElement((String)fonts.get(i), 32, Color.BLACK, 100, 500, 1800, 25, 5000L);
           textElement.setContent("Test input");
           View test= textElement.applyView(parent,(ViewGroup) parent,basicSlide,ELEMENT_ID);
           assertEquals(((TextView) test).getTypeface(), typefaces.get(i));
       }
    }
    @Test
    public void fontColorIsCorrect(){
        ArrayList<Integer> expectedColors= new ArrayList<>();
        expectedColors.add(Color.BLACK);
        expectedColors.add(Color.WHITE);
        expectedColors.add(Color.rgb(200,200,200));
        for(int i=0;i<expectedColors.size();i++){
            TextElement textElement = new TextElement(null, 32, (int)expectedColors.get(i), 100, 500, 500, 25, 5000L);
            textElement.setContent("Test input");
            TextView testView= (TextView) textElement.applyView(parent, (ViewGroup) parent, basicSlide, ELEMENT_ID);
            int actualColor= testView.getCurrentTextColor();
            int expected = expectedColors.get(i);
            assertEquals(expected, actualColor);
        }
    }
   @Test
   public void yParamsSetCorrectly(){
        int x= 200;
       ArrayList<Integer> testYPositions= new ArrayList<>();
       testYPositions.add(5); //x
       testYPositions.add(10); //y
       testYPositions.add(300); //x

       for(int i=0;i<testYPositions.size();i++){
           TextElement textElement = new TextElement(null, 32, Color.BLACK, x,(int)testYPositions.get(i), 1000, 25, 5000L);
           textElement.setContent("Test input");
           TextView testView= (TextView) textElement.applyView(parent,(ViewGroup) parent,basicSlide,ELEMENT_ID);
           ViewGroup.MarginLayoutParams mlp= (ViewGroup.MarginLayoutParams) testView.getLayoutParams();
           int actualY= mlp.topMargin;
           int expectedY= Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int)testYPositions.get(i), displayMetrics));
           assertEquals(expectedY, actualY);
       }
   }
    @Test
    public void xParamSetCorrectly(){
        int y= 200;
        ArrayList<Integer> testXPositions= new ArrayList<>();
        testXPositions.add(5); //x
        testXPositions.add(2); //x
        testXPositions.add(100); //x

        for(int i=0;i<testXPositions.size();i++){
            TextElement textElement = new TextElement(null, 32, Color.BLACK, (int)testXPositions.get(i),y, 1000, 25, 5000L);
            textElement.setContent("Test input");
            TextView testView= (TextView) textElement.applyView(parent, (ViewGroup) parent, basicSlide, ELEMENT_ID);
            ViewGroup.MarginLayoutParams mlp= (ViewGroup.MarginLayoutParams) testView.getLayoutParams();
            int actualX= mlp.leftMargin;
            int expectedX=  Math.round((((int)testXPositions.get(i)) * basicSlide.getCalculatedWidth()) / (float) basicSlide.getWidth());
            assertEquals(expectedX, actualX);
        }
    }
    @Test
    public void fontSizeIsSetCorrectly(){
        ArrayList<Integer> testFontSizes= new ArrayList<>();
        testFontSizes.add(1);
        testFontSizes.add(5);
        testFontSizes.add(10);
        for(int i=0;i<testFontSizes.size();i++) {
            TextElement textElement = new TextElement(null, (int) testFontSizes.get(i), Color.BLACK, 100, 100, 1000, 25, 5000L);
            textElement.setContent("Test input");
            TextView testView = (TextView) textElement.applyView(parent, (ViewGroup) parent, basicSlide, ELEMENT_ID);
            int expectedFontSize = View.getDefaultSize((int) testFontSizes.get(i), TypedValue.COMPLEX_UNIT_SP);
            int actualFontSize = Math.round(testView.getTextSize());
            assertEquals(expectedFontSize, actualFontSize);
        }
    }
    @Test
    public void textContentIsSetCorrectly(){
        ArrayList<String> expectedContent= new ArrayList<>();
        expectedContent.add("Test Input");
        expectedContent.add("Greaylag Goose");
        expectedContent.add("1234567890-=,.<>/?'[]{}|+_)(*&^%$£@!~`§±");

        for(int i=0;i<expectedContent.size();i++){
            TextElement textElement = new TextElement(null, 32, Color.BLACK, 100,100, 1000, 25, 5000L);
            textElement.setContent(expectedContent.get(i));
            TextView testView= (TextView) textElement.applyView(parent,(ViewGroup) parent,basicSlide,ELEMENT_ID);
            String actualContent= testView.getText().toString();
            assertEquals(actualContent, expectedContent.get(i));
        }
    }
    @Test
    public void timeOnScreenIsSetCorrectly(){
        ArrayList<Long> testTimes= new ArrayList<>();
        testTimes.add(10L);
        testTimes.add(100L);
        testTimes.add(500L);
        testTimes.add(5000L);

        for(int i=0;i<testTimes.size();i++) {
            TextElement textElement = new TextElement(null, 32,
                    Color.BLACK, 100, 100, 1000, 25, (Long)testTimes.get(i));
            textElement.setContent("Test input");
            View testView= textElement.applyView(parent,(ViewGroup) parent, basicSlide,ELEMENT_ID);
            assertEquals(View.VISIBLE, testView.getVisibility());
            Robolectric.getForegroundThreadScheduler().advanceBy(testTimes.get(i), TimeUnit.MILLISECONDS);
            assertEquals(View.INVISIBLE, testView.getVisibility());
        }
    }
    @Test
    public void getViewTypeReturnsCorrect() {
        TextElement textElement = new TextElement(null, 32,
                Color.BLACK, 100,100, 1000, 25, 500L);
        textElement.setContent("Test input");
        textElement.applyView(parent,(ViewGroup) parent,basicSlide,ELEMENT_ID);
        String actualReturn= textElement.getViewType();
        String expectedReturn= "text";
        assertEquals(expectedReturn,actualReturn);
    }
    @Test
    public void getSearchableContentReturnsCorrect(){
        TextElement textElement = new TextElement(null, 32,
                Color.BLACK, 100,100, 1000, 25, 500L);
        textElement.setContent("TEST INPUT");
        textElement.applyView(parent,(ViewGroup) parent,basicSlide,ELEMENT_ID);
        String actualReturn= textElement.getSearchableContent();
        String expectedReturn= "test input";
        assertEquals(expectedReturn,actualReturn);
    }
}

