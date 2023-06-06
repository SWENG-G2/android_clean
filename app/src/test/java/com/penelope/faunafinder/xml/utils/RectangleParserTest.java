package com.penelope.faunafinder.xml.utils;

import android.graphics.Color;

import com.penelope.faunafinder.presentation.elements.RectangleElement;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

@RunWith(RobolectricTestRunner.class)
public class RectangleParserTest {
    private static final String GOOD_XML = "<rectangle width=\"800\" height=\"600\" xCoordinate=\"50\" yCoordinate=\"350\" colour=\"#00FFFFFF\" borderColour=\"#FF0000FF\" borderWidth=\"5\" shadowRadius=\"5\" shadowDx=\"5\" shadowDy=\"7\" shadowColour=\"#0000FFFF\" timeOnScreen=\"00:00:10\" />";
    private static final int GOOD_XML_WIDTH = 800;
    private static final int GOOD_XML_HEIGHT = 600;
    private static final int GOOD_XML_COLOUR = Color.parseColor("#FF00FFFF");
    private static final int GOOD_XML_BORDER_WIDTH = 5;
    private static final int GOOD_XML_BORDER_COLOUR = Color.RED;
    private static final int GOOD_XML_X_COORDINATE = 50;
    private static final int GOOD_XML_Y_COORDINATE = 350;
    private static final int GOOD_XML_SHADOW_RADIUS = 5;
    private static final int GOOD_XML_SHADOW_DX = 5;
    private static final int GOOD_XML_SHADOW_DY = 7;
    private static final int GOOD_XML_SHADOW_COLOR = Color.BLUE;
    private static final long GOOD_XML_TIME_ON_SCREEN = 10L * 1000;

    private static final String BAD_XML = "<rectangle width=\"a\" height=\"b\" xCoordinate=\"c\" yCoordinate=\"d\" colour=\"e\" borderColour=\"f\" borderWidth=\"g\" shadowRadius=\"5f\" shadowDx=\"j5\" shadowDy=\"o\" shadowColour=\"0000FFFF\" timeOnScreen=\"00:00l10\" />";
    private static final int BAD_XML_INTEGER = 0;
    private static final int BAD_XML_COLOUR = Color.TRANSPARENT;
    private static final int BAD_XML_TIME_ON_SCREEN = -1;

    private XmlPullParser getXpp(String mockInput) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
        xmlPullParser.setInput(new StringReader(mockInput));

        xmlPullParser.nextTag();
        return xmlPullParser;
    }


    @Test
    public void parseRectangleWorks() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(GOOD_XML);
        RectangleElement realRectangleElement = RectangleParser.parseRectangle(xmlPullParser);
        int width = realRectangleElement.getWidth();
        int height = realRectangleElement.getHeight();
        int colour = realRectangleElement.getColour();
        int borderWidth = realRectangleElement.getBorderWidth();
        int borderColour = realRectangleElement.getBorderColour();
        int x = realRectangleElement.getX();
        int y = realRectangleElement.getY();
        int shadowRadius = realRectangleElement.getShadowRadius();
        int shadowDx = realRectangleElement.getShadowDx();
        int shadowDy = realRectangleElement.getShadowDy();
        int shadowColour = realRectangleElement.getShadowColour();
        long timeOnScreen = realRectangleElement.getTimeOnScreen();

        Assert.assertEquals(GOOD_XML_WIDTH, width);
        Assert.assertEquals(GOOD_XML_HEIGHT, height);
        Assert.assertEquals(GOOD_XML_COLOUR, colour);
        Assert.assertEquals(GOOD_XML_BORDER_WIDTH, borderWidth);
        Assert.assertEquals(GOOD_XML_BORDER_COLOUR, borderColour);
        Assert.assertEquals(GOOD_XML_X_COORDINATE, x);
        Assert.assertEquals(GOOD_XML_Y_COORDINATE, y);
        Assert.assertEquals(GOOD_XML_SHADOW_RADIUS, shadowRadius);
        Assert.assertEquals(GOOD_XML_SHADOW_COLOR, shadowColour);
        Assert.assertEquals(GOOD_XML_SHADOW_DX, shadowDx);
        Assert.assertEquals(GOOD_XML_SHADOW_DY, shadowDy);
        Assert.assertEquals(GOOD_XML_TIME_ON_SCREEN, timeOnScreen);
    }

    @Test
    public void parseRectangleBadInputs() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(BAD_XML);

        RectangleElement realRectangleElement = RectangleParser.parseRectangle(xmlPullParser);
        int width = realRectangleElement.getWidth();
        int height = realRectangleElement.getHeight();
        int colour = realRectangleElement.getColour();
        int borderWidth = realRectangleElement.getBorderWidth();
        int borderColour = realRectangleElement.getBorderColour();
        int x = realRectangleElement.getX();
        int y = realRectangleElement.getY();
        int shadowRadius = realRectangleElement.getShadowRadius();
        int shadowDx = realRectangleElement.getShadowDx();
        int shadowDy = realRectangleElement.getShadowDy();
        int shadowColour = realRectangleElement.getShadowColour();
        long timeOnScreen = realRectangleElement.getTimeOnScreen();

        Assert.assertEquals(BAD_XML_INTEGER, width);
        Assert.assertEquals(BAD_XML_INTEGER, height);
        Assert.assertEquals(BAD_XML_COLOUR, colour);
        Assert.assertEquals(BAD_XML_INTEGER, borderWidth);
        Assert.assertEquals(BAD_XML_COLOUR, borderColour);
        Assert.assertEquals(BAD_XML_INTEGER, x);
        Assert.assertEquals(BAD_XML_INTEGER, y);
        Assert.assertEquals(BAD_XML_INTEGER, shadowRadius);
        Assert.assertEquals(BAD_XML_COLOUR, shadowColour);
        Assert.assertEquals(BAD_XML_INTEGER, shadowDx);
        Assert.assertEquals(BAD_XML_INTEGER, shadowDy);
        Assert.assertEquals(BAD_XML_TIME_ON_SCREEN, timeOnScreen);
    }
}
