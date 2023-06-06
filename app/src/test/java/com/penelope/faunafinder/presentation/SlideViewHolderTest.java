package com.penelope.faunafinder.presentation;

import static com.penelope.faunafinder.presentation.elements.PresentationElement.MATCH_PARENT;
import static com.penelope.faunafinder.presentation.elements.PresentationElement.WRAP_CONTENT;

import android.graphics.Color;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.penelope.faunafinder.MainActivity;
import com.penelope.faunafinder.R;
import com.penelope.faunafinder.presentation.elements.LineElement;
import com.penelope.faunafinder.presentation.elements.PresentationElement;
import com.penelope.faunafinder.presentation.elements.TextElement;
import com.penelope.faunafinder.presentation.elements.VideoElement;
import com.penelope.faunafinder.presentation.elements.ViewElement;
import com.penelope.faunafinder.xml.slide.BasicSlide;
import com.penelope.faunafinder.xml.slide.Slide;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.LooperMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(RobolectricTestRunner.class)
@LooperMode(LooperMode.Mode.PAUSED)
public class SlideViewHolderTest {
    /**
     * An element that should not be rendered
     */
    private static class BadElement extends PresentationElement implements ViewElement {

        protected BadElement(int x, int y) {
            super(x, y);
        }

        @Override
        public String getViewType() {
            return "bad";
        }

        @Override
        public String getSearchableContent() {
            return null;
        }

        @Override
        public View applyView(View parent, ViewGroup container, Slide slide, int id) {
            return new TextView(parent.getContext());
        }
    }

    // Testing itemView
    private RelativeLayout itemView;
    // Testing container
    private RecyclerView container;

    private static final int SLIDE_WIDTH = 1920;
    private static final int SLIDE_HEIGHT = 200;
    private static final String SLIDE_TITLE_1 = "1";
    private static final String TEST_STRING_1 = "Hello World!";
    private static final String TEST_STRING_2 = "Hello Gotham!";
    private static final int THICKNESS = 10;
    private static final int FROM_X = 0;
    private static final int FROM_Y = 0;
    private static final int TO_X = SLIDE_WIDTH;
    private static final int TO_Y = SLIDE_HEIGHT;
    private static final long FIRST_TOS = 5000;
    private static final long SECOND_TOS = 1000;

    @Before
    public void setUpActivity() {
        // The activity we use doesn't matter per se, we are testing the adapter here
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class).setup();
        MainActivity mainActivity = activityController.get();
        container = mainActivity.findViewById(R.id.recycler_view);
        container.setLayoutManager(new LinearLayoutManager(mainActivity));

        itemView = new RelativeLayout(mainActivity);
        container.addView(itemView);
        itemView.setId(R.id.slide);
    }

    @Test
    public void badElementIsNotDrawn() {
        BasicSlide basicSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_1);
        BadElement badElement = new BadElement(0, 0);
        basicSlide.addElement(badElement);
        SlideViewHolder classUnderTest = new SlideViewHolder(itemView, container, null, 0);

        classUnderTest.draw(basicSlide);

        Assert.assertEquals(0, itemView.getChildCount());
    }

    @Test
    public void elementsAreDrawn() {
        BasicSlide basicSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_1);
        TextElement textElement1 = new TextElement("mono", 22, Color.BLACK, 0, 0, MATCH_PARENT, WRAP_CONTENT, -1);
        textElement1.setContent(TEST_STRING_1);
        TextElement textElement2 = new TextElement("mono", 22, Color.BLACK, 0, 0, MATCH_PARENT, WRAP_CONTENT, -1);
        textElement2.setContent(TEST_STRING_2);

        basicSlide.addElement(textElement1);
        basicSlide.addElement(textElement2);

        SlideViewHolder classUnderTest = new SlideViewHolder(itemView, container, null, 0);

        classUnderTest.draw(basicSlide);

        // Check elements are present
        Assert.assertEquals(2, itemView.getChildCount());
        // Check element's content is right
        List<Integer> ids = classUnderTest.getTextElementIds();
        TextView textView1 = itemView.findViewById(ids.get(0));
        TextView textView2 = itemView.findViewById(ids.get(1));
        Assert.assertEquals(TEST_STRING_1, textView1.getText());
        Assert.assertEquals(TEST_STRING_2, textView2.getText());
    }

    @Test
    public void mixedElementsAreDrawn() {
        BasicSlide basicSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_1);
        TextElement textElement = new TextElement("mono", 22, Color.BLACK, 0, 0, MATCH_PARENT, WRAP_CONTENT, -1);
        textElement.setContent(TEST_STRING_1);
        VideoElement videoElement = new VideoElement("", SLIDE_WIDTH, SLIDE_HEIGHT, 0, 0, false);

        basicSlide.addElement(textElement);
        basicSlide.addElement(videoElement);

        SlideViewHolder classUnderTest = new SlideViewHolder(itemView, container, null, 0);

        classUnderTest.draw(basicSlide);

        // Check elements are present
        Assert.assertEquals(2, itemView.getChildCount());
        // Check element's type is right
        // Text element is added first to the list, so added first to the layout
        Assert.assertTrue(itemView.getChildAt(0) instanceof TextView);
        Assert.assertTrue(itemView.getChildAt(1) instanceof StyledPlayerView);
    }

    @Test
    public void shapesAreDrawnAndSetUp() {
        BasicSlide basicSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_1);
        LineElement lineElement1 = new LineElement(THICKNESS, FROM_X, FROM_Y, TO_X, TO_Y, Color.BLACK, -1);
        LineElement lineElement2 = new LineElement(THICKNESS, FROM_X, FROM_Y, TO_X, TO_Y, Color.BLACK, FIRST_TOS);
        LineElement lineElement3 = new LineElement(THICKNESS, FROM_X, FROM_Y, TO_X, TO_Y, Color.BLACK, SECOND_TOS);
        basicSlide.addElement(lineElement1);
        basicSlide.addElement(lineElement2);
        basicSlide.addElement(lineElement3);

        SlideViewHolder classUnderTest = new SlideViewHolder(itemView, container, null, 0);
        classUnderTest.draw(basicSlide);

        // Force shapes to be drawn
        itemView.getViewTreeObserver().dispatchOnGlobalLayout();

        // Before postDelayed operations on canvas, all three shapes are present
        CanvasView canvasView = classUnderTest.getCanvasView();
        Assert.assertEquals(3, canvasView.getShapes().size());

        // Wait for third line to disappear
        Shadows.shadowOf(Looper.getMainLooper()).idleFor(SECOND_TOS, TimeUnit.MILLISECONDS);
        canvasView = classUnderTest.getCanvasView();
        Assert.assertEquals(2, canvasView.getShapes().size());

        // Wait for second line to disappear
        Shadows.shadowOf(Looper.getMainLooper()).idleFor(SECOND_TOS * 4, TimeUnit.MILLISECONDS);
        canvasView = classUnderTest.getCanvasView();
        Assert.assertEquals(1, canvasView.getShapes().size());
    }

    @Test
    public void shapesAndElementsAreDrawnAndSetUp() {
        BasicSlide basicSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_1);

        TextElement textElement = new TextElement("mono", 22, Color.BLACK, 0, 0, MATCH_PARENT, WRAP_CONTENT, -1);
        textElement.setContent(TEST_STRING_1);
        basicSlide.addElement(textElement);

        LineElement lineElement1 = new LineElement(THICKNESS, FROM_X, FROM_Y, TO_X, TO_Y, Color.BLACK, -1);
        LineElement lineElement2 = new LineElement(THICKNESS, FROM_X, FROM_Y, TO_X, TO_Y, Color.BLACK, FIRST_TOS);
        basicSlide.addElement(lineElement1);
        basicSlide.addElement(lineElement2);

        SlideViewHolder classUnderTest = new SlideViewHolder(itemView, container, null, 0);
        classUnderTest.draw(basicSlide);

        // Force shapes to be drawn
        itemView.getViewTreeObserver().dispatchOnGlobalLayout();

        // Check elements are present
        // 2 = TextView and CanvasView
        Assert.assertEquals(2, itemView.getChildCount());
        // Check element's content is right
        List<Integer> ids = classUnderTest.getTextElementIds();
        TextView textView = itemView.findViewById(ids.get(0));
        Assert.assertEquals(TEST_STRING_1, textView.getText());

        // Before postDelayed operations on canvas, both shapes are present
        CanvasView canvasView = classUnderTest.getCanvasView();
        Assert.assertEquals(2, canvasView.getShapes().size());

        // Wait for second line to disappear
        Shadows.shadowOf(Looper.getMainLooper()).idleFor(FIRST_TOS, TimeUnit.MILLISECONDS);
        canvasView = classUnderTest.getCanvasView();
        Assert.assertEquals(1, canvasView.getShapes().size());
    }
}
