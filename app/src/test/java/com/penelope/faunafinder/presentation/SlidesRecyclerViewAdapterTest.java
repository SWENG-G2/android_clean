package com.penelope.faunafinder.presentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.util.ArrayList;
import java.util.List;

import com.penelope.faunafinder.MainActivity;
import com.penelope.faunafinder.R;
import com.penelope.faunafinder.presentation.elements.TextElement;
import com.penelope.faunafinder.xml.slide.BasicSlide;
import com.penelope.faunafinder.xml.slide.BirdSlide;
import com.penelope.faunafinder.xml.slide.DetailSlide;
import com.penelope.faunafinder.xml.slide.Slide;

@RunWith(RobolectricTestRunner.class)
public class SlidesRecyclerViewAdapterTest {
    private static final int SLIDE_WIDTH = 1920;
    private static final int SLIDE_HEIGHT = 200;
    private static final String SLIDE_TITLE_1 = "1";
    private static final String SLIDE_TITLE_2 = "2";
    private static final int MATCH_PARENT = -4;
    private static final int WRAP_CONTENT = -5;
    private static final int TEXT_BASE_ID = 5000;
    private static final String TEST_STRING_1 = "Hello World!";
    private static final String TEST_STRING_2 = "Hello Gotham!";
    private static final String FILTER_STRING_1 = "wo";
    private static final String FILTER_STRING_2 = "1";
    private static final String FILTER_STRING_3 = "go";
    private static final String FILTER_STRING_4 = "2";
    private static final String FILTER_STRING_5 = "Missing text";

    // Testing recyclerView
    private RecyclerView recyclerView;

    @Before
    public void setUpActivity() {
        // The activity we use doesn't matter per se, we are testing the adapter here
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class).setup();
        MainActivity mainActivity = activityController.get();

        recyclerView = mainActivity.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
    }

    @Test
    public void adapterLaysViews() {
        // Create test slide
        Slide testSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_1);
        // Create test element
        TextElement testTextElement = new TextElement("mono", 22, Color.BLACK, 0, 0, MATCH_PARENT, WRAP_CONTENT, -1);
        testTextElement.setContent(TEST_STRING_1);
        // Add element to slide
        testSlide.addElement(testTextElement);

        List<Slide> testSlides = new ArrayList<>();
        testSlides.add(testSlide);

        // Create adapter
        SlidesRecyclerViewAdapter slidesRecyclerViewAdapter = new SlidesRecyclerViewAdapter(testSlides, null, 0);
        // Set adapter
        recyclerView.setAdapter(slidesRecyclerViewAdapter);
        // Robolectric workaround required to render recycler view
        recyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        recyclerView.layout(0, 0, SLIDE_WIDTH, SLIDE_HEIGHT);

        // Get view holder
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(0);

        if (viewHolder != null) {
            // Only one element should be present
            Assert.assertEquals(1, ((ViewGroup) viewHolder.itemView).getChildCount());

            // Get text view
            TextView textView = viewHolder.itemView.findViewById(TEXT_BASE_ID);
            Assert.assertEquals(TEST_STRING_1, textView.getText().toString());
        } else fail();
    }

    @Test
    public void slidesAreInflatedCorrectly() {
        Slide basicSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_1);
        Slide birdSlide = new BirdSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_1);
        Slide detailSlide = new DetailSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_1);

        List<Slide> testSlides = new ArrayList<>();
        testSlides.add(basicSlide);
        testSlides.add(birdSlide);
        testSlides.add(detailSlide);

        // Create adapter
        SlidesRecyclerViewAdapter slidesRecyclerViewAdapter = new SlidesRecyclerViewAdapter(testSlides, null, 0);
        // Set adapter
        recyclerView.setAdapter(slidesRecyclerViewAdapter);
        // Robolectric workaround required to render recycler view
        recyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        recyclerView.layout(0, 0, SLIDE_WIDTH, 3*SLIDE_HEIGHT);

        int index = 0;
        RecyclerView.ViewHolder viewHolder;
        for (Slide testSlide : testSlides) {
            // Get view holder
            viewHolder = recyclerView.findViewHolderForAdapterPosition(index);
            if (viewHolder == null)
                fail("View holder at position " + index + " is null!");

            View view = viewHolder.itemView;
            ViewGroup layout;
            if (testSlide.getType() == Slide.STANDARD_TYPE)
                layout = view.findViewById(R.id.slide);
            else
                layout = view.findViewById(R.id.expandable_slide);

            assertEquals(testSlide.getType(), slidesRecyclerViewAdapter.getItemViewType(index));
            assertNotNull(layout);

            index++;
        }
    }

    private void filter(SlidesRecyclerViewAdapter slidesRecyclerViewAdapter, String query, String expected) {
        slidesRecyclerViewAdapter.getFilter().filter(query);
        Robolectric.flushForegroundThreadScheduler();
        assertEquals(1, recyclerView.getChildCount());
        SlideViewHolder viewHolder = (SlideViewHolder) recyclerView.findViewHolderForAdapterPosition(0);
        if (viewHolder != null) {
            int textviewId = viewHolder.getTextElementIds().get(0);
            TextView textView = viewHolder.getItemView().findViewById(textviewId);
            assertEquals(expected, textView.getText());
        } else
            fail("View holder is null for slide1");
    }

    @Test
    public void filteringWorks() {
        Slide slide1 = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_1);
        TextElement textElement1 = new TextElement("mono", 22, Color.BLACK, 0, 0, MATCH_PARENT, WRAP_CONTENT, -1);
        textElement1.setContent(TEST_STRING_1);
        slide1.addElement(textElement1);

        Slide slide2 = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_2);
        TextElement textElement2 = new TextElement("mono", 22, Color.BLACK, 0, 0, MATCH_PARENT, WRAP_CONTENT, -1);
        textElement2.setContent(TEST_STRING_2);
        slide2.addElement(textElement2);

        List<Slide> testSlides = new ArrayList<>();
        testSlides.add(slide1);
        testSlides.add(slide2);

        // Create adapter
        SlidesRecyclerViewAdapter slidesRecyclerViewAdapter = new SlidesRecyclerViewAdapter(testSlides, null, 0);
        // Set adapter
        recyclerView.setAdapter(slidesRecyclerViewAdapter);
        // Robolectric workaround required to render recycler view
        recyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        recyclerView.layout(0, 0, SLIDE_WIDTH, 2*SLIDE_HEIGHT);

        // Filter for first element
        filter(slidesRecyclerViewAdapter, FILTER_STRING_1, TEST_STRING_1);
        filter(slidesRecyclerViewAdapter, FILTER_STRING_2, TEST_STRING_1);
        // Filter for second element
        filter(slidesRecyclerViewAdapter, FILTER_STRING_3, TEST_STRING_2);
        filter(slidesRecyclerViewAdapter, FILTER_STRING_4, TEST_STRING_2);

        // Filter for missing content
        slidesRecyclerViewAdapter.getFilter().filter(FILTER_STRING_5);
        Robolectric.flushForegroundThreadScheduler();
        assertEquals(0, recyclerView.getChildCount());
    }
}
