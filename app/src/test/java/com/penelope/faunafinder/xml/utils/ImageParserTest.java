package com.penelope.faunafinder.xml.utils;

import com.penelope.faunafinder.presentation.elements.ImageElement;

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
public class ImageParserTest {
    private static final String GOOD_XML = "<image url=\"\" width=\"300\" height=\"300\" rotation=\"69\" delay=\"3\" xCoordinate=\"100\" yCoordinate=\"120\" timeOnScreen=\"00:00:20\" />";
    private static final String GOOD_XML_URL = "";
    private static final int GOOD_XML_WIDTH = 300;
    private static final int GOOD_XML_HEIGHT = 300;
    private static final int GOOD_XML_X_COORDINATE = 100;
    private static final int GOOD_XML_Y_COORDINATE = 120;
    private static final int GOOD_XML_ROTATION = 69;
    private static final long GOOD_XML_DELAY = 3L * 1000;
    private static final long GOOD_XML_TIME_ON_SCREEN = 20L * 1000;

    private static final String BAD_XML = "<image url=\"-1\" width=\"abruvh\" height=\"sfvhbi\" rotation=\"adhcue\" delay=\"djwhff\" xCoordinate=\"adhbfbd\" yCoordinate=\"ahdj\" timeOnScreen=\"bugftyfy\" />";
    private static final int BAD_XML_INTEGER = 0;
    private static final String BAD_XML_URL = "-1";
    private static final int BAD_XML_TIME = -1;


    private XmlPullParser getXpp(String mockInput) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
        xmlPullParser.setInput(new StringReader(mockInput));

        xmlPullParser.nextTag();
        return xmlPullParser;
    }

    @Test
    public void parseImageWorks() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(GOOD_XML);
        ImageElement realImageElement = ImageParser.parseImage(xmlPullParser);
        ImageElement expectedImageElement = new ImageElement(GOOD_XML_URL, GOOD_XML_WIDTH, GOOD_XML_HEIGHT, GOOD_XML_X_COORDINATE, GOOD_XML_Y_COORDINATE, GOOD_XML_ROTATION, GOOD_XML_DELAY, GOOD_XML_TIME_ON_SCREEN);
        String realImageElementUrl = realImageElement.getUrl();
        int realImageElementWidth = realImageElement.getWidth();
        int realImageElementHeight = realImageElement.getHeight();
        int realImageElementXCoordinate = realImageElement.getX();
        int realImageElementYCoordinate = realImageElement.getY();
        int realImageElementRotation = realImageElement.getRotation();
        long realImageElementDelay = realImageElement.getDelay();
        long realImageElementTimeOnScreen = realImageElement.getTimeOnScreen();

        Assert.assertEquals(GOOD_XML_URL, realImageElementUrl);
        Assert.assertEquals(GOOD_XML_WIDTH, realImageElementWidth);
        Assert.assertEquals(GOOD_XML_HEIGHT, realImageElementHeight);
        Assert.assertEquals(GOOD_XML_X_COORDINATE, realImageElementXCoordinate);
        Assert.assertEquals(GOOD_XML_Y_COORDINATE, realImageElementYCoordinate);
        Assert.assertEquals(GOOD_XML_ROTATION, realImageElementRotation);
        Assert.assertEquals(GOOD_XML_DELAY, realImageElementDelay);
        Assert.assertEquals(GOOD_XML_TIME_ON_SCREEN, realImageElementTimeOnScreen);


    }

    @Test
    public void parseImageBadInputs() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(BAD_XML);
        ImageElement realImageElement = ImageParser.parseImage(xmlPullParser);
        String realImageElementUrl = realImageElement.getUrl();
        int realImageElementWidth = realImageElement.getWidth();
        int realImageElementHeight = realImageElement.getHeight();
        int realImageElementXCoordinate = realImageElement.getX();
        int realImageElementYCoordinate = realImageElement.getY();
        int realImageElementRotation = realImageElement.getRotation();
        long realImageElementDelay = realImageElement.getDelay();
        long realImageElementTimeOnScreen = realImageElement.getTimeOnScreen();

        Assert.assertEquals(BAD_XML_URL, realImageElementUrl);
        Assert.assertEquals(BAD_XML_INTEGER, realImageElementWidth);
        Assert.assertEquals(BAD_XML_INTEGER, realImageElementHeight);
        Assert.assertEquals(BAD_XML_INTEGER, realImageElementXCoordinate);
        Assert.assertEquals(BAD_XML_INTEGER, realImageElementYCoordinate);
        Assert.assertEquals(BAD_XML_INTEGER, realImageElementRotation);
        Assert.assertEquals(BAD_XML_TIME, realImageElementDelay);
        Assert.assertEquals(BAD_XML_TIME, realImageElementTimeOnScreen);
    }


}
