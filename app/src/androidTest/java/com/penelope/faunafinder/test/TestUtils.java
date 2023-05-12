package com.penelope.faunafinder.test;

import androidx.test.platform.app.InstrumentationRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class TestUtils {
    /**
     * Reads an asset and returns content as String
     * @param assetName The file name of the asset, e.g. bird.txt
     * @return The content of the asset as String
     * @throws IOException
     */
    public static String readAsset(String assetName) throws IOException {
        // https://stackoverflow.com/a/62087588
        InputStream inputStream = InstrumentationRegistry.getInstrumentation().
                getContext().getAssets().open(assetName);

        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining());
    }
}
