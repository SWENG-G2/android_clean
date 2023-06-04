package com.penelope.faunafinder.xml.utils;


import android.graphics.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <code>ElementParser</code> is an abstract class which provides base methods and constants
 * to parse elements from presentaton.
 */
public abstract class ElementParser {
    protected static final String NAME_SPACE = null;

    // Common attributes, shared by multiple elements
    protected static final String X_COORDINATE = "xCoordinate";
    protected static final String Y_COORDINATE = "yCoordinate";
    protected static final String WIDTH = "width";
    protected static final String HEIGHT = "height";
    protected static final String COLOUR = "colour";
    protected static final String URL = "url";
    protected static final String LOOP = "loop";
    protected static final String RADIUS = "radius";
    protected static final String BORDER_WIDTH = "borderWidth";
    protected static final String BORDER_COLOUR = "borderColour";
    protected static final String TIME_ON_SCREEN = "timeOnScreen";
    protected static final String DELAY = "delay";

    private static final int EXPECTED_COLOUR_STRING_LENGTH = 9;
    // Colour string is #AARRGGBB according to standard
    private static final int ALPHA_START_STRING_INDEX = 7;
    private static final int RGB_START_STRING_INDEX = 1;


    /**
     * Parser a colour string.
     *
     * @param colour The colour string.
     * @return {@link Color}
     */
    protected static int parseColour(String colour) {
        if (colour != null && colour.length() == EXPECTED_COLOUR_STRING_LENGTH) {
            String formattedColour = "#" + colour.substring(ALPHA_START_STRING_INDEX)
                    + colour.substring(RGB_START_STRING_INDEX, ALPHA_START_STRING_INDEX);
            try {
                return Color.parseColor(formattedColour);
            } catch (Exception exception) {
                exception.printStackTrace();

                return Color.TRANSPARENT;
            }
        }
        return Color.TRANSPARENT;
    }

    /**
     * Parser an integer string.
     *
     * @param stringValue The integer string.
     * @return The integer value.
     */
    protected static int parseInt(String stringValue) {
        try {
            if (stringValue != null)
                return Integer.parseInt(stringValue);
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }
        return 0;
    }

    /**
     * Parses a timestamp.
     *
     * @param stringValue The timestamp string.
     * @return The timestamp value in milliseconds or -1.
     */
    protected static long parseTimeOnScreen(String stringValue) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss", Locale.UK);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            if (stringValue != null) {
                Date date = simpleDateFormat.parse(stringValue);
                if (date != null)
                    return date.getTime();
            }
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        return -1;
    }

    /**
     * Parses a delay string.
     *
     * @param stringValue The delay string.
     * @return The delay in milliseconds or -1.
     */
    protected static long parseDelay(String stringValue) {
        if (stringValue != null) {
            try {
                return Long.parseLong(stringValue) * 1000L;
            } catch (NumberFormatException numberFormatException) {
                return -1;
            }
        }
        return -1;
    }
}