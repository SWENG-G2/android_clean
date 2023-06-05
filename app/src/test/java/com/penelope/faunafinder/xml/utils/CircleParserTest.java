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
    private static final String GOOD_XML = "<circle radius=\"10\" xCoordinate=\"450\" yCoordinate=\"650\" colour=\"#FF0000FF\" borderColour=\"#00FF00FF\" borderWidth=\"10\" shadowRadius=\"15\" shadowDx=\"10\" shadowDy=\"0\" shadowColour=\"#FF00FFFF\" timeOnScreen=\"00:01:00\" />";
    private static int GOOD_XML_RADIUS = 10;
    private static int GOOD_XML_X_COORDINATE = 450;
    private static int GOOD_XML_Y_COORDINATE = 650;
    private static int GOOD_XML_COLOUR = Color.RED;
    private static int GOOD_XML_BORDER_COLOUR = Color.GREEN;
    private static int GOOD_XML_BORDER_WIDTH = 10;
    private static final String BAD_XML = "<circle radius=\"sdhcb\" xCoordinate=\"ssfjv\" yCoordinate=\"fvibwwi\" colour=\"123\" borderColour=\"123\" borderWidth=\"adbf\" shadowRadius=\"sfvbf\" shadowDx=\"wrvfjf\" shadowDy=\"wrfbj\" shadowColour=\"1234\" timeOnScreen=\"00:01:00\" />";

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
        int realAudioElementRadius = realCircleElement.getRadius();
        int realAudioElementColour = realCircleElement.getColour();
        int realAudioElementBorderWidth = realCircleElement.getBorderWidth();
        int realAudioElementBorderColour = realCircleElement.getBorderColour();
        int realAudioElementXCoordinate = realCircleElement.getX();
        int realAudioElementYCoordinate = realCircleElement.getY();


        Assert.assertEquals(GOOD_XML_RADIUS, realAudioElementRadius);
        Assert.assertEquals(GOOD_XML_COLOUR, realAudioElementColour);
        Assert.assertEquals(GOOD_XML_BORDER_WIDTH, realAudioElementBorderWidth);
        Assert.assertEquals(GOOD_XML_BORDER_COLOUR, realAudioElementBorderColour);
        Assert.assertEquals(GOOD_XML_X_COORDINATE, realAudioElementXCoordinate);
        Assert.assertEquals(GOOD_XML_Y_COORDINATE, realAudioElementYCoordinate);

    }

    @Test
    public void circleParseBadInputs() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(BAD_XML);
        CircleElement expectedCircleElement = new CircleElement(0, Color.TRANSPARENT, 0, Color.TRANSPARENT, 0, 0);
        int expectedCircleElementRadius = expectedCircleElement.getRadius();
        int expectedCircleElementColour = expectedCircleElement.getColour();
        int expectedCircleElementBorderWidth = expectedCircleElement.getBorderWidth();
        int expectedCircleElementBorderColour = expectedCircleElement.getBorderColour();
        int expectedCircleElementXCoordinate = expectedCircleElement.getX();
        int expectedCircleElementYCoordinate = expectedCircleElement.getY();

        CircleElement realCircleElement = CircleParser.parseCircle(xmlPullParser);
        int realAudioElementRadius = realCircleElement.getRadius();
        int realAudioElementColour = realCircleElement.getColour();
        int realAudioElementBorderWidth = realCircleElement.getBorderWidth();
        int realAudioElementBorderColour = realCircleElement.getBorderColour();
        int realAudioElementXCoordinate = realCircleElement.getX();
        int realAudioElementYCoordinate = realCircleElement.getY();


        Assert.assertEquals(expectedCircleElementRadius, realAudioElementRadius);
        Assert.assertEquals(expectedCircleElementColour, realAudioElementColour);
        Assert.assertEquals(expectedCircleElementBorderWidth, realAudioElementBorderWidth);
        Assert.assertEquals(expectedCircleElementBorderColour, realAudioElementBorderColour);
        Assert.assertEquals(expectedCircleElementXCoordinate, realAudioElementXCoordinate);
        Assert.assertEquals(expectedCircleElementYCoordinate, realAudioElementYCoordinate);


    }


}
