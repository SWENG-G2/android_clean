package com.penelope.faunafinder;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove;
import static androidx.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static androidx.test.espresso.assertion.PositionAssertions.isTopAlignedWith;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AboutUsViewTest {
    @Rule
    public ActivityScenarioRule<AboutUsActivity> activityRule =
            new ActivityScenarioRule<AboutUsActivity>(AboutUsActivity.class);

    @Before
    public void init() {
        Intents.init();
    }
    @After
    public void cleanUp() {
        Intents.release();
    }
    @Test
    public void toolbarDisplayedAtTopOfScreen()
    {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withId(R.id.toolbar)).check(isTopAlignedWith(withId(R.id.aboutus_view_activity)));
    }
    @Test
    public void appNameDisplayedTopOfToolbar()
    {
        onView(withText(R.string.fauna_finder)).check(matches(isDisplayed()));
        onView(withText(R.string.fauna_finder)).check(isTopAlignedWith(withId(R.id.toolbar)));
    }
    @Test
    public void appVersionDisplayedBelowAppName()
    {
        onView(withText(R.string.version)).check(matches(isDisplayed()));
        onView(withText(R.string.version)).check(isCompletelyBelow(withText(R.string.fauna_finder)));
    }
    @Test
    public void appLogoDisplayedBelowToolbar()
    {
        onView(withId(R.id.appLogo)).check(matches(isDisplayed()));
        onView(withId(R.id.appLogo)).check(isCompletelyBelow(withId(R.id.toolbar)));
    }
    @Test
    public void creatorsButtonDisplayedBetweenLogoAndUsageButton()
    {
        onView(withText(R.string.creators_title)).check(matches(isDisplayed()));
        onView(withText(R.string.creators_title)).check(isCompletelyBelow(withId(R.id.appLogo)));
        onView(withText(R.string.creators_title)).check(isCompletelyAbove(withText(R.string.app_usage_title)));
    }
    @Test
    public void usageButtonDisplayedBetweenCreatorsButtonAndLicensingButton()
    {
        onView(withText(R.string.app_usage_title)).check(matches(isDisplayed()));
        onView(withText(R.string.app_usage_title)).check(isCompletelyBelow(withText(R.string.creators_title)));
        onView(withText(R.string.app_usage_title)).check(isCompletelyAbove(withText(R.string.licensing_title)));
    }
    @Test
    public void licensingButtonDisplayedBelowUsageButton()
    {
        onView(withText(R.string.licensing_title)).check(matches(isDisplayed()));
        onView(withText(R.string.licensing_title)).check(isCompletelyBelow(withText(R.string.app_usage_title)));
    }

    @Test
    public void copyrightDisplayedAtBottomOfPage()
    {
        onView(withText(R.string.penelope_copyright)).check(matches(isDisplayed()));
        onView(withText(R.string.penelope_copyright)).check(isCompletelyBelow(withText(R.string.licensing_title)));
    }

    @Test
    public void usageButtonTakesYouToUsagePage()
    {
        onView(withId(R.id.usage_btn)).perform(ViewActions.click());
    }
    @Test
    public void creatorsButtonTakesYouToCreatorsPage()
    {
        onView(withId(R.id.creators_btn)).perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasComponent(AboutUsCreatorsActivity.class.getName()));
    }
    @Test
    public void licensesButtonTakesYouToLicensesPage()
    {
        onView(withId(R.id.licensing_btn)).perform(ViewActions.click());
        Intents.intended(IntentMatchers.hasComponent(OssLicensesMenuActivity.class.getName()));
    }
}

