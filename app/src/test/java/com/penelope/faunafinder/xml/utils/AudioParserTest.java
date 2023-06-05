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
import com.penelope.faunafinder.xml.utils.AudioParser;

@RunWith(RobolectricTestRunner.class)
public class AudioParserTest {
    private static final String GOOD_XML = "<audio url=\"https://open.spotify.com/track/1xs8bOvm3IzEYmcLJVOc34?si=c67a4ae59837441e\" loop=\"true\" xCoordinate=\"250\" yCoordinate=\"100\" />";
    private static final String GOOD_XML_URL = "https://open.spotify.com/track/1xs8bOvm3IzEYmcLJVOc34?si=c67a4ae59837441e";
    private static final boolean GOOD_XML_BOOLEAN = true;
    private static final int GOOD_XML_X_COORDINATE = 250;
    private static final int GOOD_XML_Y_COORDINATE = 100;

    private static final String BAD_XML = "<audio url=\"2\" loop=\"true\" xCoordinate=\"pink\" yCoordinate=\"white\" />";

    private XmlPullParser getXpp(String mockInput) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
        xmlPullParser.setInput(new StringReader(mockInput));

        xmlPullParser.nextTag();
        return xmlPullParser;
    }

    @Test
    public void parseAudioWorks() throws XmlPullParserException, IOException {
        //parseAudio returns a new audio element with the appropriate attributes specified on the xml file
        //I set getters in AudioElement and PresentationElement to assert each parameter
        XmlPullParser xmlPullParser = getXpp(GOOD_XML);
        AudioElement realAudioElement = AudioParser.parseAudio(xmlPullParser);
        AudioElement expectedAudioElement = new AudioElement(GOOD_XML_URL, GOOD_XML_BOOLEAN, GOOD_XML_X_COORDINATE, GOOD_XML_Y_COORDINATE);
        String realAudioElementUrl = realAudioElement.getUrl();
        boolean realAudioElementLoop = realAudioElement.isLoop();
        int realAudioElementXCoordinate = realAudioElement.getX();
        int realAudioElementYCoordinate = realAudioElement.getY();

        Assert.assertEquals(GOOD_XML_URL, realAudioElementUrl);
        Assert.assertEquals(GOOD_XML_BOOLEAN, realAudioElementLoop);
        Assert.assertEquals(GOOD_XML_X_COORDINATE, realAudioElementXCoordinate);
        Assert.assertEquals(GOOD_XML_Y_COORDINATE, realAudioElementYCoordinate);
    }

    @Test
    public void parseAudioBadInputs() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(BAD_XML);
        AudioElement realAudioElement = AudioParser.parseAudio(xmlPullParser);
        AudioElement expectedAudioElement = new AudioElement("2", true, 0, 0);
        String realAudioElementUrl = realAudioElement.getUrl();
        boolean realAudioElementLoop = realAudioElement.isLoop();
        int realAudioElementXCoordinate = realAudioElement.getX();
        int realAudioElementYCoordinate = realAudioElement.getY();

        String expectedAudioElementUrl = expectedAudioElement.getUrl();
        boolean expectedAudioElementLoop = expectedAudioElement.isLoop();
        int expectedAudioElementXCoordinate = expectedAudioElement.getX();
        int expectedAudioElementYCoordinate = expectedAudioElement.getY();
        Assert.assertEquals(expectedAudioElementUrl, realAudioElementUrl);
        Assert.assertEquals(expectedAudioElementLoop, realAudioElementLoop);
        Assert.assertEquals(expectedAudioElementXCoordinate, realAudioElementXCoordinate);
        Assert.assertEquals(expectedAudioElementYCoordinate, realAudioElementYCoordinate);

    }


}
