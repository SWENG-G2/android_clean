package com.penelope.faunafinder.xml.utils;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import com.penelope.faunafinder.presentation.elements.CircleElement;
import com.penelope.faunafinder.xml.utils.CircleParser;

@RunWith(RobolectricTestRunner.class)
public class CircleParserTest {
    private static final String GOOD_XML = "<circle radius=\"10\" xCoordinate=\"450\" yCoordinate=\"650\" colour=\"#FF0000FF\" borderColour=\"#00FF00FF\" borderWidth=\"10\" shadowRadius=\"15\" shadowDx=\"10\" shadowDy=\"1\" shadowColour=\"#FF00FFFF\" timeOnScreen=\"00:01:00\" />";
    private static final int GOOD_XML_RADIUS = 10;
    private static final int GOOD_XML_X_COORDINATE = 450;
    private static final int GOOD_XML_Y_COORDINATE = 650;
    private static final int GOOD_XML_COLOUR = Color.RED;
    private static final int GOOD_XML_BORDER_COLOUR = Color.GREEN;
    private static final int GOOD_XML_BORDER_WIDTH = 10;
    private static final int GOOD_XML_SHADOW_RADIUS = 15;
    private static final int GOOD_XML_SHADOW_DX = 10;
    private static final int GOOD_XML_SHADOW_DY = 1;
    private static final int GOOD_XML_SHADOW_COLOR = Color.parseColor("#FF00FF");
    private static final long GOOD_XML_TIME_ON_SCREEN = 60L * 1000;

    private static final String BAD_XML = "<circle radius=\"sdhcb\" xCoordinate=\"ssfjv\" yCoordinate=\"fvibwwi\" colour=\"123\" borderColour=\"123\" borderWidth=\"adbf\" shadowRadius=\"sfvbf\" shadowDx=\"wrvfjf\" shadowDy=\"wrfbj\" shadowColour=\"1234\" timeOnScreen=\"00:0f:00\" />";
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
    public void circleParseWorks() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(GOOD_XML);
        CircleElement realCircleElement = CircleParser.parseCircle(xmlPullParser);
        int radius = realCircleElement.getRadius();
        int colour = realCircleElement.getColour();
        int borderWidth = realCircleElement.getBorderWidth();
        int borderColour = realCircleElement.getBorderColour();
        int x = realCircleElement.getX();
        int y = realCircleElement.getY();
        int shadowRadius = realCircleElement.getShadowRadius();
        int shadowDx = realCircleElement.getShadowDx();
        int shadowDy = realCircleElement.getShadowDy();
        int shadowColour = realCircleElement.getShadowColour();
        long timeOnScreen = realCircleElement.getTimeOnScreen();


        Assert.assertEquals(GOOD_XML_RADIUS, radius);
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
    public void circleParseBadInputs() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(BAD_XML);

        CircleElement realCircleElement = CircleParser.parseCircle(xmlPullParser);
        int radius = realCircleElement.getRadius();
        int colour = realCircleElement.getColour();
        int borderWidth = realCircleElement.getBorderWidth();
        int borderColour = realCircleElement.getBorderColour();
        int x = realCircleElement.getX();
        int y = realCircleElement.getY();
        int shadowRadius = realCircleElement.getShadowRadius();
        int shadowDx = realCircleElement.getShadowDx();
        int shadowDy = realCircleElement.getShadowDy();
        int shadowColour = realCircleElement.getShadowColour();
        long timeOnScreen = realCircleElement.getTimeOnScreen();

        Assert.assertEquals(BAD_XML_INTEGER, radius);
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
