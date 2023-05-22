package com.penelope.faunafinder;


import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToLastPosition;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;

import com.penelope.faunafinder.presentation.SlidesRecyclerViewAdapter;
import com.penelope.faunafinder.test.TestUtils;

import org.hamcrest.core.AllOf;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class BirdActivityTest {
    private static final int EXPECTED_SLIDES = 4;

    private Intent testIntent;
    private String birdXml;

    @Before
    public void setUp() throws IOException {
        Context context = ApplicationProvider.getApplicationContext();

        // Create test intent
        testIntent = new Intent(context, BirdActivity.class);

        // Read bird.txt raw file
        birdXml = TestUtils.readAsset("bird.txt");

        // Mock web server
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody(birdXml));
        mockWebServer.start();

        // Inject mock server url
        String testingUrl = mockWebServer.url("bird").toString();
        testIntent.putExtra(context.getString(R.string.testingUrl), testingUrl);

        Intents.init();
    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    @Test
    public void loadsFromInternet() {
        testIntent.putExtra("birdId", 1);

        try (ActivityScenario<BirdActivity> activityScenario = ActivityScenario.launch(testIntent)) {
            activityScenario.onActivity(activity -> {
                // All items will never be visible so need to check with adapter
                RecyclerView recyclerView = activity.findViewById(R.id.recycler_view);
                SlidesRecyclerViewAdapter adapter =
                        (SlidesRecyclerViewAdapter) recyclerView.getAdapter();

                assertNotNull(adapter);
                assertEquals(EXPECTED_SLIDES, adapter.getItemCount());
            });
        }
    }

    @Test
    public void loadsFromStorage() {
        testIntent.putExtra("birdXML", birdXml);

        try (ActivityScenario<BirdActivity> activityScenario = ActivityScenario.launch(testIntent)) {
            activityScenario.onActivity(activity -> {
                // All items will never be visible so need to check with adapter
                RecyclerView recyclerView = activity.findViewById(R.id.recycler_view);
                SlidesRecyclerViewAdapter adapter =
                        (SlidesRecyclerViewAdapter) recyclerView.getAdapter();

                assertNotNull(adapter);
                assertEquals(EXPECTED_SLIDES, adapter.getItemCount());
            });
        }
    }

    @Test
    public void detailSlideExpandsAndCollapses() {
        testIntent.putExtra("birdId", 1);

        try (ActivityScenario<BirdActivity> activityScenario = ActivityScenario.launch(testIntent)) {
            // Scroll to last
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).perform(scrollToLastPosition());

            // Content should be collapsed
            Espresso.onView(AllOf.allOf(ViewMatchers.withId(R.id.slide),
                            ViewMatchers.withParent(ViewMatchers.withId(R.id.expandable_slide)),
                            ViewMatchers.withParent(ViewMatchers.hasDescendant(
                                    ViewMatchers.withText("Location")))
                    ))
                    .check(ViewAssertions.matches(IsNot.not(ViewMatchers.isDisplayed())));

            // Click on slide title
            Espresso.onView(AllOf.allOf(ViewMatchers.withId(R.id.title_layout),
                            ViewMatchers.withParent(ViewMatchers.withId(R.id.expandable_slide)),
                            ViewMatchers.withParent(ViewMatchers.hasDescendant(
                                    ViewMatchers.withText("Location")))))
                    .perform(ViewActions.click());


            // Content should be expanded
            Espresso.onView(AllOf.allOf(ViewMatchers.withId(R.id.slide),
                            ViewMatchers.withParent(ViewMatchers.withId(R.id.expandable_slide)),
                            ViewMatchers.withParent(ViewMatchers.hasDescendant(
                                    ViewMatchers.withText("Location")))
                    ))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

            // Click on slide title
            Espresso.onView(AllOf.allOf(ViewMatchers.withId(R.id.title_layout),
                            ViewMatchers.withParent(ViewMatchers.withId(R.id.expandable_slide)),
                            ViewMatchers.withParent(ViewMatchers.hasDescendant(
                                    ViewMatchers.withText("Location")))))
                    .perform(ViewActions.click());

            // Content should be collapsed
            Espresso.onView(AllOf.allOf(ViewMatchers.withId(R.id.slide),
                            ViewMatchers.withParent(ViewMatchers.withId(R.id.expandable_slide)),
                            ViewMatchers.withParent(ViewMatchers.hasDescendant(
                                    ViewMatchers.withText("Location")))
                    ))
                    .check(ViewAssertions.matches(IsNot.not(ViewMatchers.isDisplayed())));
        }
    }
}
