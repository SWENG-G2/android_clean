package com.penelope.faunafinder.xml.utils;

import com.penelope.faunafinder.presentation.elements.CircleElement;

import org.xmlpull.v1.XmlPullParser;

/**
 * <code>CircleParser</code> is a utility class used to parse circle elements from presentation.
 */
public class CircleParser extends ElementParser {
    /**
     * Parses a circle element from an {@link XmlPullParser} at the right position.
     *
     * @param xmlPullParser The XmlPullParser.
     * @return A set-up {@link CircleElement} with fallback default values.
     */
    public static CircleElement parseCircle(XmlPullParser xmlPullParser) {
        int radius = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, RADIUS));
        int colour = parseColour(xmlPullParser.getAttributeValue(NAME_SPACE, COLOUR));
        int borderWidth = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, BORDER_WIDTH));
        int borderColour = parseColour(xmlPullParser.getAttributeValue(NAME_SPACE, BORDER_COLOUR));
        int x = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, X_COORDINATE));
        int y = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, Y_COORDINATE));
        long timeOnScreen = parseTimeOnScreen(xmlPullParser.getAttributeValue(NAME_SPACE,
                TIME_ON_SCREEN));
        int shadowColour = parseColour(xmlPullParser.getAttributeValue(NAME_SPACE, SHADOW_COLOUR));
        int shadowDx = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, SHADOW_DX));
        int shadowDy = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, SHADOW_DY));
        int shadowRadius = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, SHADOW_RADIUS));

        return new CircleElement(radius, colour, borderWidth, borderColour, x, y, timeOnScreen, shadowColour, shadowDx, shadowDy, shadowRadius);
    }
}
