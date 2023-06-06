package com.penelope.faunafinder.xml.utils;

import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import com.penelope.faunafinder.presentation.elements.AudioElement;

@RunWith(RobolectricTestRunner.class)
public class AudioParserTest {
    private static final String GOOD_XML = "<audio url=\"https://open.spotify.com/track/1xs8bOvm3IzEYmcLJVOc34?si=c67a4ae59837441e\" loop=\"true\" xCoordinate=\"250\" yCoordinate=\"100\" />";
    private static final String GOOD_XML_URL = "https://open.spotify.com/track/1xs8bOvm3IzEYmcLJVOc34?si=c67a4ae59837441e";
    private static final boolean GOOD_XML_LOOP = true;
    private static final int GOOD_XML_X_COORDINATE = 250;
    private static final int GOOD_XML_Y_COORDINATE = 100;

    private static final String BAD_XML = "<audio url=\"2\" loop=\"cookies\" xCoordinate=\"pink\" yCoordinate=\"white\" />";
    private static final String BAD_XML_URL = "2";
    private static final boolean BAD_XML_LOOP = false;
    private static final int BAD_XML_X_COORDINATE = 0;
    private static final int BAD_XML_Y_COORDINATE = 0;

    private XmlPullParser getXpp(String mockInput) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
        xmlPullParser.setInput(new StringReader(mockInput));

        xmlPullParser.nextTag();
        return xmlPullParser;
    }

    @Test
    public void parseAudioWorks() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(GOOD_XML);
        AudioElement realAudioElement = AudioParser.parseAudio(xmlPullParser);
        String realAudioElementUrl = realAudioElement.getUrl();
        boolean realAudioElementLoop = realAudioElement.isLoop();
        int realAudioElementXCoordinate = realAudioElement.getX();
        int realAudioElementYCoordinate = realAudioElement.getY();

        Assert.assertEquals(GOOD_XML_URL, realAudioElementUrl);
        Assert.assertEquals(GOOD_XML_LOOP, realAudioElementLoop);
        Assert.assertEquals(GOOD_XML_X_COORDINATE, realAudioElementXCoordinate);
        Assert.assertEquals(GOOD_XML_Y_COORDINATE, realAudioElementYCoordinate);
    }

    @Test
    public void parseAudioBadInputs() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(BAD_XML);
        AudioElement realAudioElement = AudioParser.parseAudio(xmlPullParser);
        String realAudioElementUrl = realAudioElement.getUrl();
        boolean realAudioElementLoop = realAudioElement.isLoop();
        int realAudioElementXCoordinate = realAudioElement.getX();
        int realAudioElementYCoordinate = realAudioElement.getY();

        Assert.assertEquals(BAD_XML_URL, realAudioElementUrl);
        Assert.assertEquals(BAD_XML_LOOP, realAudioElementLoop);
        Assert.assertEquals(BAD_XML_X_COORDINATE, realAudioElementXCoordinate);
        Assert.assertEquals(BAD_XML_Y_COORDINATE, realAudioElementYCoordinate);
    }
}
