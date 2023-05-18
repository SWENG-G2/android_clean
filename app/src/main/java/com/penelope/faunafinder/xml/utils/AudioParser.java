package com.penelope.faunafinder.xml.utils;

import com.penelope.faunafinder.presentation.elements.AudioElement;

import org.xmlpull.v1.XmlPullParser;

/**
 * <code>AudioParser</code> is a utility class used to parse audio elements from presentation.
 */
public class AudioParser extends ElementParser {
    /**
     * Parses an audio element from an {@link XmlPullParser} at the right position.
     *
     * @param xmlPullParser The XmlPullParser.
     * @return A set-up {@link AudioElement} with fallback default values.
     */
    public static AudioElement parseAudio(XmlPullParser xmlPullParser) {
        int xCoordinate = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, X_COORDINATE));
        int yCoordinate = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, Y_COORDINATE));
        String url = xmlPullParser.getAttributeValue(NAME_SPACE, URL);
        boolean loop = Boolean.parseBoolean(xmlPullParser.getAttributeValue(NAME_SPACE, LOOP));


        return new AudioElement(url, loop, xCoordinate, yCoordinate);
    }
}
