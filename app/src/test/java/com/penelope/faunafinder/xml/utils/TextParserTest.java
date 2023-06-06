package com.penelope.faunafinder.xml.utils;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import com.penelope.faunafinder.presentation.elements.TextElement;
import com.penelope.faunafinder.xml.utils.TextParser;

@RunWith(RobolectricTestRunner.class)
public class TextParserTest {

    private static final String GOOD_XML = "<text fontName=\"mono\" fontSize=\"32\" colour=\"#FF7B00FF\" xCoordinate=\"300\" yCoordinate=\"250\" timeOnScreen=\"00:00:05\" width=\"500\" height=\"100\">";
    private static final String BAD_XML = "<text fontName=\"12\" fontSize=\"a\" colour=\"b\" xCoordinate=\"d\" yCoordinate=\"e\" timeOnScreen=\"f\" width=\"g\" height=\"h\">";

    private XmlPullParser getXpp(String mockInput) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
        xmlPullParser.setInput(new StringReader(mockInput));

        xmlPullParser.nextTag();
        return xmlPullParser;
    }

    @Test
    public void parseTextWorks() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(GOOD_XML);
        TextElement expectedTextElement = new TextElement("mono", 32, Color.parseColor("#FFFF7B00"), 300, 250, 500, 100, 5L * 1000);
        TextElement realTextElement = TextParser.parseText(xmlPullParser);
        int expectedTextElementXCoordinate = expectedTextElement.getX();
        int expectedTextElementYCoordinate = expectedTextElement.getY();
        int expectedTextElementWidth = expectedTextElement.getWidth();
        int expectedTextElementHeight = expectedTextElement.getHeight();
        int expectedTextElementFontSize = expectedTextElement.getFontSize();
        String expectedTextElementFont = expectedTextElement.getFont();
        long expectedTextElementTimeOnScreen = expectedTextElement.getTimeOnScreen();
        int expectedTextElementColour = expectedTextElement.getColor();

        int realTextElementXCoordinate = realTextElement.getX();
        int realTextElementWidth = realTextElement.getWidth();
        int realTextElementYCoordinate = realTextElement.getY();
        int realTextElementHeight = realTextElement.getHeight();
        int realTextElementFontSize = realTextElement.getFontSize();
        String realTextElementFont = realTextElement.getFont();
        long realTextElementTimeOnScreen = realTextElement.getTimeOnScreen();
        int realTextElementColour = realTextElement.getColor();

        Assert.assertEquals(expectedTextElementFont, realTextElementFont);
        Assert.assertEquals(expectedTextElementFontSize, realTextElementFontSize);
        Assert.assertEquals(expectedTextElementColour, realTextElementColour);
        Assert.assertEquals(expectedTextElementXCoordinate, realTextElementXCoordinate);
        Assert.assertEquals(expectedTextElementYCoordinate, realTextElementYCoordinate);
        Assert.assertEquals(expectedTextElementWidth, realTextElementWidth);
        Assert.assertEquals(expectedTextElementHeight, realTextElementHeight);
        Assert.assertEquals(expectedTextElementTimeOnScreen, realTextElementTimeOnScreen);
    }

    @Test
    public void parseTextBadInputs() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(BAD_XML);
        //Color is wrong for now
        TextElement expectedTextElement = new TextElement("12", 0, 0, 0, 0, 0, 0, -1);
        TextElement realTextElement = TextParser.parseText(xmlPullParser);
        int expectedTextElementXCoordinate = expectedTextElement.getX();
        int expectedTextElementYCoordinate = expectedTextElement.getY();
        int expectedTextElementWidth = expectedTextElement.getWidth();
        int expectedTextElementHeight = expectedTextElement.getHeight();
        int expectedTextElementFontSize = expectedTextElement.getFontSize();
        String expectedTextElementFont = expectedTextElement.getFont();
        long expectedTextElementTimeOnScreen = expectedTextElement.getTimeOnScreen();
        int expectedTextElementColour = expectedTextElement.getColor();

        int realTextElementXCoordinate = realTextElement.getX();
        int realTextElementWidth = realTextElement.getWidth();
        int realTextElementYCoordinate = realTextElement.getY();
        int realTextElementHeight = realTextElement.getHeight();
        int realTextElementFontSize = realTextElement.getFontSize();
        String realTextElementFont = realTextElement.getFont();
        long realTextElementTimeOnScreen = realTextElement.getTimeOnScreen();
        int realTextElementColour = realTextElement.getColor();

        Assert.assertEquals(expectedTextElementFont, realTextElementFont);
        Assert.assertEquals(expectedTextElementFontSize, realTextElementFontSize);
        Assert.assertEquals(expectedTextElementColour, realTextElementColour);
        Assert.assertEquals(expectedTextElementXCoordinate, realTextElementXCoordinate);
        Assert.assertEquals(expectedTextElementYCoordinate, realTextElementYCoordinate);
        Assert.assertEquals(expectedTextElementWidth, realTextElementWidth);
        Assert.assertEquals(expectedTextElementHeight, realTextElementHeight);
        Assert.assertEquals(expectedTextElementTimeOnScreen, realTextElementTimeOnScreen);
    }

}
