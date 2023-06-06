package com.penelope.faunafinder.xml.utils;

import android.graphics.Color;

import com.penelope.faunafinder.presentation.elements.LineElement;

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
public class LineParserTest {
    private static final String GOOD_XML = "<line thickness=\"5\" fromX=\"100\" fromY=\"110\" toX=\"400\" toY=\"130\" colour=\"#FF000069\" timeOnScreen=\"00:00:10\" />";
    private static final int GOOD_XML_THICKNESS = 5;
    private static final int GOOD_XML_FROM_X = 100;
    private static final int GOOD_XML_FROM_Y = 110;
    private static final int GOOD_XML_TO_X = 400;
    private static final int GOOD_XML_TO_Y = 130;
    private static final int GOOD_XML_COLOUR = Color.parseColor("#69FF0000");
    private static final long GOOD_XML_TIME_ON_SCREEN = 10L * 1000;

    private static final String BAD_XML = "<line thickness=\"vnefei\" fromX=\"djcvwdv\" fromY=\"wdvkncssi\" toX=\"fved\" toY=\"wjdvcb\" colour=\"123\" timeOnScreen=\"p00:00:10\" />";
    private static final int BAD_XML_INTEGER = 0;
    private static final int BAD_XML_COLOUR = Color.TRANSPARENT;
    private static final int BAD_XML_TIME = -1;


    private XmlPullParser getXpp(String mockInput) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
        xmlPullParser.setInput(new StringReader(mockInput));

        xmlPullParser.nextTag();
        return xmlPullParser;
    }

    @Test
    public void parseLineWorks() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(GOOD_XML);
        LineElement realLineElement = LineParser.parseLine(xmlPullParser);
        int realLineElementThickness = realLineElement.getThickness();
        int realLineElementFromX = realLineElement.getFromX();
        int realLineElementFromY = realLineElement.getFromY();
        int realLineElementToX = realLineElement.getToX();
        int realLineElementToY = realLineElement.getToY();
        int realLineElementColour = realLineElement.getColour();
        long realLineElementTimeOnScreen = realLineElement.getTimeOnScreen();

        Assert.assertEquals(GOOD_XML_THICKNESS, realLineElementThickness);
        Assert.assertEquals(GOOD_XML_FROM_X, realLineElementFromX);
        Assert.assertEquals(GOOD_XML_FROM_Y, realLineElementFromY);
        Assert.assertEquals(GOOD_XML_TO_X, realLineElementToX);
        Assert.assertEquals(GOOD_XML_TO_Y, realLineElementToY);
        Assert.assertEquals(GOOD_XML_COLOUR, realLineElementColour);
        Assert.assertEquals(GOOD_XML_TIME_ON_SCREEN, realLineElementTimeOnScreen);
    }

    @Test
    public void parseLineBadInputs() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(BAD_XML);
        LineElement realLineElement = LineParser.parseLine(xmlPullParser);
        int realLineElementThickness = realLineElement.getThickness();
        int realLineElementFromX = realLineElement.getFromX();
        int realLineElementFromY = realLineElement.getFromY();
        int realLineElementToX = realLineElement.getToX();
        int realLineElementToY = realLineElement.getToY();
        int realLineElementColour = realLineElement.getColour();
        long realLineElementTimeOnScreen = realLineElement.getTimeOnScreen();

        Assert.assertEquals(BAD_XML_INTEGER, realLineElementThickness);
        Assert.assertEquals(BAD_XML_INTEGER, realLineElementFromX);
        Assert.assertEquals(BAD_XML_INTEGER, realLineElementFromY);
        Assert.assertEquals(BAD_XML_INTEGER, realLineElementToX);
        Assert.assertEquals(BAD_XML_INTEGER, realLineElementToY);
        Assert.assertEquals(BAD_XML_COLOUR, realLineElementColour);
        Assert.assertEquals(BAD_XML_TIME, realLineElementTimeOnScreen);
    }

}
