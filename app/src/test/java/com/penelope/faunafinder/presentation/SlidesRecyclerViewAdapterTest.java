package com.penelope.faunafinder.presentation;

import static org.junit.Assert.fail;

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
import com.penelope.faunafinder.xml.slide.Slide;

@RunWith(RobolectricTestRunner.class)
public class SlidesRecyclerViewAdapterTest {
    private static final int SLIDE_WIDTH = 1920;
    private static final int SLIDE_HEIGHT = 200;
    private static final String SLIDE_TITLE = "1";
    private static final int MATCH_PARENT = -4;
    private static final int WRAP_CONTENT = -5;
    private static final int TEXT_BASE_ID = 5000;
    private static final String TEST_STRING = "Hello World!";

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
        Slide testSlide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);
        // Create test element
        TextElement testTextElement = new TextElement("mono", 22, Color.BLACK, 0, 0, MATCH_PARENT, WRAP_CONTENT, -1);
        testTextElement.setContent(TEST_STRING);
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
        recyclerView.layout(0, 0, 0, 0);

        // Get view holder
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(0);

        if (viewHolder != null) {
            // Only one element should be present
            Assert.assertEquals(1, ((ViewGroup) viewHolder.itemView).getChildCount());

            // Get text view
            TextView textView = viewHolder.itemView.findViewById(TEXT_BASE_ID);
            Assert.assertEquals(TEST_STRING, textView.getText().toString());
        } else fail();
    }
}
