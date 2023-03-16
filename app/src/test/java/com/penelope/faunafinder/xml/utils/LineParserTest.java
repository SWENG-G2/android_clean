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

import com.penelope.faunafinder.presentation.elements.LineElement;
import com.penelope.faunafinder.xml.utils.LineParser;


@RunWith(RobolectricTestRunner.class)
public class LineParserTest {
    private static final String GOOD_XML = "<line thickness=\"5\" fromX=\"100\" fromY=\"110\" toX=\"400\" toY=\"130\" colour=\"#FF000069\" />";
    private static final int GOOD_XML_THICKNESS = 5;
    private static final int GOOD_XML_FROM_X = 100;
    private static final int GOOD_XML_FROM_Y = 110;
    private static final int GOOD_XML_TO_X = 400;
    private static final int GOOD_XML_TO_Y = 130;
    //colour is wrong for now
    private static final int GOOD_XML_COLOUR = android.R.color.holo_red_light;

    private static final String BAD_XML = "<line thickness=\"vnefei\" fromX=\"djcvwdv\" fromY=\"wdvkncssi\" toX=\"fved\" toY=\"wjdvcb\" colour=\"123\" />";



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

        Assert.assertEquals(GOOD_XML_THICKNESS,realLineElementThickness);
        Assert.assertEquals(GOOD_XML_FROM_X,realLineElementFromX);
        Assert.assertEquals(GOOD_XML_FROM_Y,realLineElementFromY);
        Assert.assertEquals(GOOD_XML_TO_X,realLineElementToX);
        Assert.assertEquals(GOOD_XML_TO_Y,realLineElementToY);
        Assert.assertEquals(GOOD_XML_COLOUR,realLineElementColour);

    }

    @Test
    public void parseLineBadInputs() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(BAD_XML);
        LineElement realLineElement = LineParser.parseLine(xmlPullParser);
        //test is failing because expected colour is transparent but the actual colour is 0??
        LineElement expectedLineElement = new LineElement(0,0,0,0,0, android.R.color.transparent);
        int realLineElementThickness = realLineElement.getThickness();
        int realLineElementFromX = realLineElement.getFromX();
        int realLineElementFromY = realLineElement.getFromY();
        int realLineElementToX = realLineElement.getToX();
        int realLineElementToY = realLineElement.getToY();
        int realLineElementColour = realLineElement.getColour();

        int expectedLineElementThickness = expectedLineElement.getThickness();
        int expectedLineElementFromX = expectedLineElement.getFromX();
        int expectedLineElementFromY = expectedLineElement.getFromY();
        int expectedLineElementToX = expectedLineElement.getToX();
        int expectedLineElementToY = expectedLineElement.getToY();
        int expectedLineElementColour = expectedLineElement.getColour();

        Assert.assertEquals(expectedLineElementThickness,realLineElementThickness);
        Assert.assertEquals(expectedLineElementFromX,realLineElementFromX);
        Assert.assertEquals(expectedLineElementFromY,realLineElementFromY);
        Assert.assertEquals(expectedLineElementToX,realLineElementToX);
        Assert.assertEquals(expectedLineElementToY,realLineElementToY);
        Assert.assertEquals(expectedLineElementColour,realLineElementColour);



    }

}
