package com.penelope.faunafinder;

import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.penelope.faunafinder.test.TestUtils;


public class MainActivityLifeCycleObserverTest {
    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void cleanUp() {
        Intents.release();
    }

    // Hack to access observer outside of activity's main thread
    private static class ObserverRunner {
        private MainActivityLifecycleObserver mainActivityLifecycleObserver;

        public void setMainActivityLifecycleObserver(MainActivityLifecycleObserver mainActivityLifecycleObserver) {
            this.mainActivityLifecycleObserver = mainActivityLifecycleObserver;
        }

        public MainActivityLifecycleObserver getMainActivityLifecycleObserver() {
            return mainActivityLifecycleObserver;
        }

        public void loadBirdXml(Uri xmlURI) {
            if (mainActivityLifecycleObserver != null)
                mainActivityLifecycleObserver.displayXMLFromUri(xmlURI);
        }
    }

    @Test
    public void opensBirdActivityOnLoadFromStorage() throws IOException {
        // Read test file
        String birdXml = TestUtils.readAsset("bird.txt");

        // Write test file to storage
        File internalStorage = ApplicationProvider.getApplicationContext().getFilesDir();
        File destination = new File(internalStorage, "test.xml");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(destination));
        outputStreamWriter.write(birdXml);
        outputStreamWriter.close();

        ObserverRunner observerRunner = new ObserverRunner();

        try (ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class)) {
            activityScenario.onActivity(activity -> {
                // Create life cycle observer
                MainActivityLifecycleObserver classUnderTest = new MainActivityLifecycleObserver(
                        activity.getActivityResultRegistry(), activity);

                observerRunner.setMainActivityLifecycleObserver(classUnderTest);
            });

            // Trigger storage loading
            observerRunner.loadBirdXml(Uri.fromFile(destination));

            // Ensure BirdActivity is started with example file content extra
            Intents.intended(IntentMatchers.hasComponent(BirdActivity.class.getName()));
            Intents.intended(IntentMatchers.hasExtra("birdXML", birdXml));
        }
    }
}
