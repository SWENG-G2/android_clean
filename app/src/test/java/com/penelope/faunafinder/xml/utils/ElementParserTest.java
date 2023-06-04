package com.penelope.faunafinder.xml.utils;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ElementParserTest {
    private static final int NULL_RET = -1;

    @Test
    public void nullGuards() {
        // parseInt returns 0 for null
        Assert.assertEquals(0, ElementParser.parseInt(null));

        Assert.assertEquals(NULL_RET, ElementParser.parseDelay(null));
        Assert.assertEquals(NULL_RET, ElementParser.parseTimeOnScreen(null));
        Assert.assertEquals(Color.TRANSPARENT, ElementParser.parseColour(null));
    }

    @Test
    public void parseIntBadInputs() {
        Assert.assertEquals(0, ElementParser.parseInt(""));
        Assert.assertEquals(0, ElementParser.parseInt("1.2"));
        Assert.assertEquals(0, ElementParser.parseInt("Not a number"));
    }

    @Test
    public void parseTimeOnScreenBadInputs() {
        Assert.assertEquals(NULL_RET, ElementParser.parseTimeOnScreen(""));
        Assert.assertEquals(NULL_RET, ElementParser.parseTimeOnScreen("1.2"));
        Assert.assertEquals(NULL_RET, ElementParser.parseTimeOnScreen("Not a number"));
        Assert.assertEquals(NULL_RET, ElementParser.parseTimeOnScreen("1:0"));
    }

    @Test
    public void parseDelayBadInputs() {
        Assert.assertEquals(NULL_RET, ElementParser.parseDelay(""));
        Assert.assertEquals(NULL_RET, ElementParser.parseDelay("Not a number"));
        Assert.assertEquals(NULL_RET, ElementParser.parseDelay("1:0"));
    }

    @Test
    public void parseColourBadInputs() {
        Assert.assertEquals(Color.TRANSPARENT, ElementParser.parseColour(""));
        Assert.assertEquals(Color.TRANSPARENT, ElementParser.parseColour("Not a col"));
        Assert.assertEquals(Color.TRANSPARENT, ElementParser.parseColour("1:0"));
    }

    @Test
    public void parseIntWorks() {
        Assert.assertEquals(10, ElementParser.parseInt("10"));
        Assert.assertEquals(-10, ElementParser.parseInt("-10"));
    }

    @Test
    public void parseDelayWorks() {
        Assert.assertEquals(3 * 1000, ElementParser.parseDelay("3"));
    }

    @Test
    public void parseTimeOnScreenWorks() {
        // Returns in ms from 0 time unix timestamp
        Assert.assertEquals(1000, ElementParser.parseTimeOnScreen("0:0:1"));
        // 1H 1m 1s in ms
        Assert.assertEquals(1000 + 60 * 1000 + 60 * 60 * 1000, ElementParser.parseTimeOnScreen("1:1:1"));
    }

    @Test
    public void parseColourWorks() {
        Assert.assertEquals(Color.BLACK, ElementParser.parseColour("#000000FF"));
        Assert.assertEquals(Color.RED, ElementParser.parseColour("#FF0000FF"));
        Assert.assertEquals(Color.WHITE, ElementParser.parseColour("#FFFFFFFF"));
    }
}
