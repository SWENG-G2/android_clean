package com.penelope.faunafinder.utils;

import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.penelope.faunafinder.MainActivity;
import com.penelope.faunafinder.R;
import com.penelope.faunafinder.presentation.SlidesRecyclerViewAdapter;
import com.penelope.faunafinder.utils.ListItemClickAction;
import com.penelope.faunafinder.utils.ListItemClickListener;
import com.penelope.faunafinder.utils.UIUtils;
import com.penelope.faunafinder.xml.PresentationParser;
import com.penelope.faunafinder.xml.slide.BasicSlide;
import com.penelope.faunafinder.xml.slide.Slide;
import com.penelope.faunafinder.xml.slide.SlideFactory;

@RunWith(RobolectricTestRunner.class)
public class UIUtilsTest {
    private static final int SLIDE_WIDTH = 1920;
    private static final int SLIDE_HEIGHT = 1080;
    private static final String SLIDE_TITLE = "Test slide title";
    private static final int SLIDES = 10;
    private static final int MARGIN = 52;

    private RelativeLayout parent;
    private RecyclerView recyclerView;

    @Before
    public void setUp() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            // Dummy activity
            MainActivity mainActivity = controller.get();

            // The upper view group
            ConstraintLayout constraintLayout = mainActivity.findViewById(R.id.main_activity);

            // The "slide"
            parent = new RelativeLayout(mainActivity);

            // Dummy recycler view
            recyclerView = new RecyclerView(parent.getContext());
            parent.addView(recyclerView);
            recyclerView.setId(R.id.recycler_view);

            constraintLayout.addView(parent);
        }
    }

    @Test
    public void producesCorrectAdapter() throws XmlPullParserException, IOException {
        // Presentation parser mock
        PresentationParser parser = Mockito.mock(PresentationParser.class);

        // Dummy slides
        List<Slide> slides = new ArrayList<>();
        for (int i = 0; i < SLIDES; i++)
            slides.add(new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE));

        // Always return dummy slides from parser
        Mockito.when(parser.parse(ArgumentMatchers.any(String.class),
                ArgumentMatchers.any(String.class))).thenReturn(slides);

        // Result from listItemClickAction
        List<Integer> actionResults = new ArrayList<>();
        actionResults.add(0); // To be modified
        actionResults.add(1); // Expected

        // Increments item at i index of actionResults array
        ListItemClickAction listItemClickAction = i -> {
            actionResults.set(i, actionResults.get(i) + 1);
        };

        // Get generated adapter for testing
        SlidesRecyclerViewAdapter slidesRecyclerViewAdapter = UIUtils.populateList("", parent,
                SlideFactory.BASIC_SLIDE, listItemClickAction, MARGIN, parser);

        if (slidesRecyclerViewAdapter == null)
            fail("SlidesRecyclerViewAdapter is null");

        // Get item click listener
        ListItemClickListener listItemClickListener =
                slidesRecyclerViewAdapter.getListItemClickListener();

        // Perform click action
        listItemClickListener.onItemClick(0);

        assertEquals(slidesRecyclerViewAdapter, recyclerView.getAdapter());
        assertEquals(slides, slidesRecyclerViewAdapter.getSlides());
        assertEquals(MARGIN, slidesRecyclerViewAdapter.getHorizontalMargin());
        assertEquals(actionResults.get(1).intValue(), actionResults.get(0).intValue());
    }
}
