package com.penelope.faunafinder;

import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import com.penelope.faunafinder.test.TestUtils;

public class MainActivityTest {
    private static final int TEST_CAMPUS_ID = 1;
    private static final int EXPECTED_BIRDS = 3;

    private Intent testIntent;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();

        // Wipe shared preferences
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.campusConfiguration),
                        Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(context.getString(R.string.campusId)).apply();

        // Create test intent
        testIntent = new Intent(context, MainActivity.class);

        Intents.init();
    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    /**
     * Sets up test with a mock web server and the selected campus id shared pref
     * @throws IOException
     */
    private void injectCampus() throws IOException {
        Context context = ApplicationProvider.getApplicationContext();

        // Inject selected campus
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.campusConfiguration),
                        Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(context.getString(R.string.campusId), TEST_CAMPUS_ID)
                .apply();

        // Mock web server
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody(
                TestUtils.readAsset("birds.txt")));
        mockWebServer.start();

        // Inject mock server url
        String testingUrl = mockWebServer.url("birds").toString();
        testIntent.putExtra(context.getString(R.string.testingUrl), testingUrl);
    }

    @Test
    public void userIsAdvisedToPickCampus() {
        try (ActivityScenario<MainActivity> ignored = ActivityScenario.launch(MainActivity.class)) {
            Espresso.onView(ViewMatchers.withId(R.id.select_location_tv)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        }
    }

    @Test
    public void listIsPopulated() throws IOException {
        injectCampus();
        try (ActivityScenario<MainActivity> ignored = ActivityScenario.launch(testIntent)) {
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).check(
                    ViewAssertions.matches(ViewMatchers.hasChildCount(EXPECTED_BIRDS)));
        }
    }

    @Test
    public void listIsInteractive() throws IOException {
        injectCampus();
        try (ActivityScenario<MainActivity> ignored = ActivityScenario.launch(testIntent)) {
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).perform(
                    actionOnItemAtPosition(0, click()));

            Intents.intended(IntentMatchers.hasComponent(BirdActivity.class.getName()));
        }
    }

    @Test
    public void listIsSearchable() throws IOException {
        injectCampus();
        try (ActivityScenario<MainActivity> ignored = ActivityScenario.launch(testIntent)) {
            // Before search list is full
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).check(
                    ViewAssertions.matches(ViewMatchers.hasChildCount(EXPECTED_BIRDS)));

            // Search Barnacle Goose
            Espresso.onView(ViewMatchers.withId(R.id.search)).perform(click());
            Espresso.onView(ViewMatchers.withId(androidx.appcompat.R.id.search_src_text))
                    .perform(typeText("Barnacle Goose"));

            // Should only have one child now (Barnacle Goose)
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).check(
                    ViewAssertions.matches(ViewMatchers.hasChildCount(1)));

            // Clear Search bar
            Espresso.onView(ViewMatchers.withId(R.id.search)).perform(click());
            Espresso.onView(ViewMatchers.withId(androidx.appcompat.R.id.search_src_text))
                    .perform(clearText(), closeSoftKeyboard());

            // With clear search list is full
            Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).check(
                    ViewAssertions.matches(ViewMatchers.hasChildCount(EXPECTED_BIRDS)));
        }
    }
}
