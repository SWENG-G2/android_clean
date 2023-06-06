package com.penelope.faunafinder;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <code>MainActivityLifecycleObserver</code> is a DefaultLifecycleObserver used to load XML files
 * from storage.
 */
public class MainActivityLifecycleObserver implements DefaultLifecycleObserver {
    public static final String LOAD_BIRD_FROM_STORAGE = "loadBirdFromStorage";


    private final ActivityResultRegistry activityResultRegistry;
    private final Activity activity;
    private ActivityResultLauncher<String> activityResultLauncher;

    public MainActivityLifecycleObserver(@NonNull ActivityResultRegistry activityResultRegistry,
                                         Activity activity) {
        this.activityResultRegistry = activityResultRegistry;
        this.activity = activity;
    }

    /**
     * Reads and parses the file from URI and starts a BirdActivity to display the information.
     *
     * @param xmlURI The URI.
     */
    public void displayXMLFromUri(Uri xmlURI) {
        StringBuilder xmlBuilder = new StringBuilder();
        String xml = null;
        try {
            // Load file
            InputStream inputStream = activity.getContentResolver().openInputStream(xmlURI);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (bufferedReader.ready()) { // Create string content
                xmlBuilder.append(bufferedReader.readLine());
            }

            xml = xmlBuilder.toString();
            inputStream.close();
        } catch (IOException | OutOfMemoryError e) {
            e.printStackTrace();
        }

        // Send xml content to bird activity
        if (xml != null && xml.length() > 0) {
            Intent birdIntent = new Intent(activity.getApplicationContext(), BirdActivity.class);
            birdIntent.putExtra("birdXML", xml);
            try {
                activity.startActivity(birdIntent);
            } catch (RuntimeException runtimeException) {
                runtimeException.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onCreate(owner);

        // Register on file selected callback
        activityResultLauncher = activityResultRegistry.register(LOAD_BIRD_FROM_STORAGE, owner,
                new ActivityResultContracts.GetContent(), this::displayXMLFromUri);
    }

    /**
     * Launches the file picker to load an XML from storage.
     */
    public void loadBirdFromStorage() {
        activityResultLauncher.launch("text/xml");
    }
}
