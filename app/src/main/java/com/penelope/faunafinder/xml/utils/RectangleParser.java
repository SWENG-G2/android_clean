package com.penelope.faunafinder.xml.utils;

import com.penelope.faunafinder.presentation.elements.RectangleElement;

import org.xmlpull.v1.XmlPullParser;

/**
 * <code>RectangleParser</code> is a utility class used to parse rectangle elements from
 * presentation.
 */
public class RectangleParser extends ElementParser {
    /**
     * Parses a rectangle element from an {@link XmlPullParser} at the right position.
     *
     * @param xmlPullParser The XmlPullParser.
     * @return A set-up {@link RectangleElement} with fallback default values.
     */
    public static RectangleElement parseRectangle(XmlPullParser xmlPullParser) {
        int width = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, WIDTH));
        int height = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, HEIGHT));
        int colour = parseColour(xmlPullParser.getAttributeValue(NAME_SPACE, COLOUR));
        int borderWidth = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, BORDER_WIDTH));
        int borderColour = parseColour(xmlPullParser.getAttributeValue(NAME_SPACE, BORDER_COLOUR));
        int x = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, X_COORDINATE));
        int y = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, Y_COORDINATE));
        long timeOnScreen = parseTimeOnScreen(xmlPullParser.getAttributeValue(NAME_SPACE,
                TIME_ON_SCREEN));

        return new RectangleElement(width, height, colour, borderWidth, borderColour, x, y,
                timeOnScreen);
    }
}
