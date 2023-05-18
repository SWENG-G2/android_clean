package com.penelope.faunafinder.xml.utils;

import com.penelope.faunafinder.presentation.elements.ImageElement;

import org.xmlpull.v1.XmlPullParser;

/**
 * <code>ImageParser</code> is a utility class used to parse image elements from presentation.
 */
public class ImageParser extends ElementParser {
    private static final String ROTATION = "rotation";

    /**
     * Parses an image element from an {@link XmlPullParser} at the right position.
     *
     * @param xmlPullParser The XmlPullParser.
     * @return A set-up {@link ImageElement} with fallback default values.
     */
    public static ImageElement parseImage(XmlPullParser xmlPullParser) {
        String url = xmlPullParser.getAttributeValue(NAME_SPACE, URL);
        int width = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, WIDTH));
        int height = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, HEIGHT));
        int x = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, X_COORDINATE));
        int y = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, Y_COORDINATE));
        int rotation = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, ROTATION));
        long delay = parseDelay(xmlPullParser.getAttributeValue(NAME_SPACE, DELAY));
        long timeOnScreen = parseTimeOnScreen(xmlPullParser.getAttributeValue(NAME_SPACE, TIME_ON_SCREEN));

        return new ImageElement(url, width, height, x, y, rotation, delay, timeOnScreen);
    }
}
