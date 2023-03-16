package com.penelope.faunafinder.xml.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import com.penelope.faunafinder.presentation.elements.RectangleElement;
import com.penelope.faunafinder.xml.utils.RectangleParser;

@RunWith(RobolectricTestRunner.class)
public class RectangleParserTest {
    private static final String GOOD_XML = "<rectangle width=\"800\" height=\"600\" xCoordinate=\"50\" yCoordinate=\"350\" colour=\"#00FFFFFF\" borderColour=\"#FF0000FF\" borderWidth=\"5\" shadowRadius=\"5\" shadowDx=\"5\" shadowDy=\"7\" shadowColour=\"#0000FFFF\" />";
    private static final int GOOD_XML_WIDTH = 800;
    private static final int GOOD_XML_HEIGHT = 600;
    private static final int GOOD_XML_COLOUR = Color.CYAN;
    private static final int GOOD_XML_BORDER_WIDTH = 5;
    private static final int GOOD_XML_BORDER_COLOUR = COLOR.RED;
    private static final int GOOD_XML_X_COORDINATE = 50;
    private static final int GOOD_XML_Y_COORDINATE = 350;

    private static final String BAD_XML = "<rectangle width=\"a\" height=\"b\" xCoordinate=\"c\" yCoordinate=\"d\" colour=\"e\" borderColour=\"f\" borderWidth=\"g\" shadowRadius=\"5\" shadowDx=\"5\" shadowDy=\"7\" shadowColour=\"#0000FFFF\" />";



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
        RectangleElement expectedRectangleElement = new RectangleElement(GOOD_XML_WIDTH,GOOD_XML_HEIGHT,GOOD_XML_COLOUR,GOOD_XML_BORDER_WIDTH,GOOD_XML_BORDER_COLOUR,GOOD_XML_X_COORDINATE,GOOD_XML_Y_COORDINATE);
        RectangleElement realRectangleElement = RectangleParser.parseRectangle(xmlPullParser);
        int realRectangleElementWidth = realRectangleElement.getWidth();
        int realRectangleElementHeight = realRectangleElement.getHeight();
        int realRectangleElementColour = realRectangleElement.getColour();
        int realRectangleElementBorderWidth = realRectangleElement.getBorderWidth();
        int realRectangleElementBorderColour = realRectangleElement.getBorderColour();
        int realRectangleElementXCoordinate = realRectangleElement.getX();
        int realRectangleElementYCoordinate = realRectangleElement.getY();

        Assert.assertEquals(GOOD_XML_WIDTH,realRectangleElementWidth);
        Assert.assertEquals(GOOD_XML_HEIGHT,realRectangleElementHeight);
        Assert.assertEquals(GOOD_XML_COLOUR,realRectangleElementColour);
        Assert.assertEquals(GOOD_XML_BORDER_WIDTH,realRectangleElementBorderWidth);
        Assert.assertEquals(GOOD_XML_BORDER_COLOUR,realRectangleElementBorderColour);
        Assert.assertEquals(GOOD_XML_X_COORDINATE,realRectangleElementXCoordinate);
        Assert.assertEquals(GOOD_XML_Y_COORDINATE,realRectangleElementYCoordinate);

    }

    @Test
    public void parseRectangleBadInputs() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(BAD_XML);
        RectangleElement expectedRectangleElement = new RectangleElement(0,0,0,0,0,0,0);
        int expectedRectangleElementWidth = expectedRectangleElement.getWidth();
        int expectedRectangleElementHeight = expectedRectangleElement.getHeight();
        int expectedRectangleElementColour = expectedRectangleElement.getColour();
        int expectedRectangleElementBorderWidth = expectedRectangleElement.getBorderWidth();
        int expectedRectangleElementBorderColour = expectedRectangleElement.getBorderColour();
        int expectedRectangleElementXCoordinate = expectedRectangleElement.getX();
        int expectedRectangleElementYCoordinate = expectedRectangleElement.getY();

        RectangleElement realRectangleElement = RectangleParser.parseRectangle(xmlPullParser);
        int realRectangleElementWidth = realRectangleElement.getWidth();
        int realRectangleElementHeight = realRectangleElement.getHeight();
        int realRectangleElementColour = realRectangleElement.getColour();
        int realRectangleElementBorderWidth = realRectangleElement.getBorderWidth();
        int realRectangleElementBorderColour = realRectangleElement.getBorderColour();
        int realRectangleElementXCoordinate = realRectangleElement.getX();
        int realRectangleElementYCoordinate = realRectangleElement.getY();

        Assert.assertEquals(expectedRectangleElementWidth,realRectangleElementWidth);
        Assert.assertEquals(expectedRectangleElementHeight,realRectangleElementHeight);
        Assert.assertEquals(expectedRectangleElementColour,realRectangleElementColour);
        Assert.assertEquals(expectedRectangleElementBorderWidth,realRectangleElementBorderWidth);
        Assert.assertEquals(expectedRectangleElementBorderColour,realRectangleElementBorderColour);
        Assert.assertEquals(expectedRectangleElementXCoordinate,realRectangleElementXCoordinate);
        Assert.assertEquals(expectedRectangleElementYCoordinate,realRectangleElementYCoordinate);




    }
}
