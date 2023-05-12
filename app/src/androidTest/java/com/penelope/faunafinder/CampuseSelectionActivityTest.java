package com.penelope.faunafinder;


import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import com.penelope.faunafinder.test.TestUtils;

public class CampusSelectionActivityTest {
    private static final int EXPECTED_CAMPUSES = 4;

    private Intent testIntent;

    @Before
    public void setUp() throws IOException {
        Context context = ApplicationProvider.getApplicationContext();

        // Create test intent
        testIntent = new Intent(context, CampusSelectionActivity.class);

        // Mock web server
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody(
                TestUtils.readAsset("list.txt")));
        mockWebServer.start();

        // Inject mock server url
        String testingUrl = mockWebServer.url("list").toString();
        testIntent.putExtra(context.getString(R.string.testingUrl), testingUrl);

        Intents.init();
    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    @Test
    public void listIsPopulated() {
        try (ActivityScenario<MainActivity> ignored = ActivityScenario.launch(testIntent)) {
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).check(
                    ViewAssertions.matches(ViewMatchers.hasChildCount(EXPECTED_CAMPUSES)));
        }
    }

    @Test
    public void listIsInteractive() {
        try (ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(testIntent)) {
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).perform(
                    actionOnItemAtPosition(0, click()));

            assertEquals(Lifecycle.State.DESTROYED, activityScenario.getState());
        }
    }

    @Test
    public void listIsSearchable() {
        try (ActivityScenario<MainActivity> ignored = ActivityScenario.launch(testIntent)) {
            // Before search list is full
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).check(
                    ViewAssertions.matches(ViewMatchers.hasChildCount(EXPECTED_CAMPUSES)));

            // Search York
            Espresso.onView(ViewMatchers.withId(R.id.search)).perform(click());
            Espresso.onView(ViewMatchers.withId(androidx.appcompat.R.id.search_src_text))
                    .perform(typeText("York"), closeSoftKeyboard());

            // Should only have two children now (York, York St John)
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).check(
                    ViewAssertions.matches(ViewMatchers.hasChildCount(2)));

            // Clear Search bar
            Espresso.onView(ViewMatchers.withId(R.id.search)).perform(click());
            Espresso.onView(ViewMatchers.withId(androidx.appcompat.R.id.search_src_text))
                    .perform(clearText(), closeSoftKeyboard());

            // With clear search list is full
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).check(
                    ViewAssertions.matches(ViewMatchers.hasChildCount(EXPECTED_CAMPUSES)));
        }
    }
}