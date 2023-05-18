package com.penelope.faunafinder.xml.utils;

import com.penelope.faunafinder.presentation.elements.VideoElement;

import org.xmlpull.v1.XmlPullParser;

/**
 * <code>VideoParser</code> is a utility class used to parse video elements from presentation.
 */
public class VideoParser extends ElementParser {

    /**
     * Parses a video element from an {@link XmlPullParser} at the right position.
     *
     * @param xmlPullParser The XmlPullParser.
     * @return A set-up {@link VideoElement} with fallback default values.
     */
    public static VideoElement parseVideo(XmlPullParser xmlPullParser) {
        String url = xmlPullParser.getAttributeValue(NAME_SPACE, URL);
        int width = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, WIDTH));
        int height = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, HEIGHT));
        int x = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, X_COORDINATE));
        int y = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, Y_COORDINATE));
        boolean loop = Boolean.parseBoolean(xmlPullParser.getAttributeValue(NAME_SPACE, LOOP));

        return new VideoElement(url, width, height, x, y, loop);
    }
}
