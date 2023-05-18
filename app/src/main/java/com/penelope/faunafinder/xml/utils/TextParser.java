package com.penelope.faunafinder.xml.utils;

import com.penelope.faunafinder.presentation.elements.TextElement;

import org.xmlpull.v1.XmlPullParser;

/**
 * <code>TextParser</code> is a utility class used to parse text elements from presentation.
 */
public class TextParser extends ElementParser {
    private static final String FONT_SIZE = "fontSize";
    private static final String FONT_NAME = "fontName";

    /**
     * Parses a text element from an {@link XmlPullParser} at the right position.
     *
     * @param xmlPullParser The XmlPullParser.
     * @return A set-up {@link TextElement} with fallback default values.
     */
    public static TextElement parseText(XmlPullParser xmlPullParser) {
        int xCoordinate = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, X_COORDINATE));
        int yCoordinate = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, Y_COORDINATE));
        int width = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, WIDTH));
        int height = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, HEIGHT));
        int fontSize = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, FONT_SIZE));
        String font = xmlPullParser.getAttributeValue(NAME_SPACE, FONT_NAME);
        long timeOnScreen = parseTimeOnScreen(xmlPullParser.getAttributeValue(NAME_SPACE, TIME_ON_SCREEN));
        int colour = parseColour(xmlPullParser.getAttributeValue(NAME_SPACE, COLOUR));

        return new TextElement(font, fontSize, colour, xCoordinate, yCoordinate, width, height, timeOnScreen);
    }
}
