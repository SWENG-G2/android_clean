package com.penelope.faunafinder.xml.utils;

import com.penelope.faunafinder.presentation.elements.LineElement;

import org.xmlpull.v1.XmlPullParser;

/**
 * <code>LineParser</code> is a utility class used to parse line elements from presentation.
 */
public class LineParser extends ElementParser {
    private static final String FROM_X = "fromX";
    private static final String FROM_Y = "fromY";
    private static final String TO_X = "toX";
    private static final String TO_Y = "toY";
    private static final String THICKNESS = "thickness";

    /**
     * Parses a line element from an {@link XmlPullParser} at the right position.
     *
     * @param xmlPullParser The XmlPullParser.
     * @return A set-up {@link LineElement} with fallback default values.
     */
    public static LineElement parseLine(XmlPullParser xmlPullParser) {
        int thickness = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, THICKNESS));
        int fromX = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, FROM_X));
        int fromY = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, FROM_Y));
        int toX = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, TO_X));
        int toY = parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, TO_Y));
        int colour = parseColour(xmlPullParser.getAttributeValue(NAME_SPACE, COLOUR));

        return new LineElement(thickness, fromX, fromY, toX, toY, colour);
    }
}
