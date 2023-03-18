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

import com.penelope.faunafinder.presentation.elements.VideoElement;
import com.penelope.faunafinder.xml.utils.VideoParser;

@RunWith(RobolectricTestRunner.class)
public class VideoParserTest {
    private static final String GOOD_XML = "<video url=\"fvbiy\" loop=\"true\" width=\"300\" height=\"200\" xCoordinate=\"150\" yCoordinate=\"0\" />";
    private static final String BAD_XML = "<video url=\"fvbiy\" loop=\"true\" width=\"a\" height=\"b\" xCoordinate=\"c\" yCoordinate=\"d\" />";


    private XmlPullParser getXpp(String mockInput) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
        xmlPullParser.setInput(new StringReader(mockInput));

        xmlPullParser.nextTag();
        return xmlPullParser;
    }

    @Test
    public void parseVideoWorks() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(GOOD_XML);
        VideoElement expectedVideoElement = new VideoElement("fvbiy",300,200,150,0,true);
        VideoElement realVideoElement = VideoParser.parseVideo(xmlPullParser);
        String expectedVideoElementUrl = expectedVideoElement.getUrl();
        int expectedVideoElementWidth = expectedVideoElement.getWidth();
        int expectedVideoElementHeight = expectedVideoElement.getHeight();
        int expectedVideoElementXCoordinate = expectedVideoElement.getX();
        int expectedVideoElementYCoordinate = expectedVideoElement.getY();
        boolean expectedVideoElementLoop = expectedVideoElement.isLoop();

        String realVideoElementUrl = realVideoElement.getUrl();
        int realVideoElementWidth = realVideoElement.getWidth();
        int realVideoElementHeight = realVideoElement.getHeight();
        int realVideoElementXCoordinate = realVideoElement.getX();
        int realVideoElementYCoordinate = realVideoElement.getY();
        boolean realVideoElementLoop = realVideoElement.isLoop();

        Assert.assertEquals(expectedVideoElementUrl,realVideoElementUrl);
        Assert.assertEquals(expectedVideoElementWidth,realVideoElementWidth);
        Assert.assertEquals(expectedVideoElementHeight,realVideoElementHeight);
        Assert.assertEquals(expectedVideoElementXCoordinate,realVideoElementXCoordinate);
        Assert.assertEquals(expectedVideoElementYCoordinate,realVideoElementYCoordinate);
        Assert.assertEquals(expectedVideoElementLoop,realVideoElementLoop);





    }

    @Test
    public void parseVideoBadInputs() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = getXpp(BAD_XML);
        VideoElement expectedVideoElement = new VideoElement("fvbiy",0,0,0,0,true);
        VideoElement realVideoElement = VideoParser.parseVideo(xmlPullParser);
        String expectedVideoElementUrl = expectedVideoElement.getUrl();
        int expectedVideoElementWidth = expectedVideoElement.getWidth();
        int expectedVideoElementHeight = expectedVideoElement.getHeight();
        int expectedVideoElementXCoordinate = expectedVideoElement.getX();
        int expectedVideoElementYCoordinate = expectedVideoElement.getY();
        boolean expectedVideoElementLoop = expectedVideoElement.isLoop();

        String realVideoElementUrl = realVideoElement.getUrl();
        int realVideoElementWidth = realVideoElement.getWidth();
        int realVideoElementHeight = realVideoElement.getHeight();
        int realVideoElementXCoordinate = realVideoElement.getX();
        int realVideoElementYCoordinate = realVideoElement.getY();
        boolean realVideoElementLoop = realVideoElement.isLoop();

        Assert.assertEquals(expectedVideoElementUrl,realVideoElementUrl);
        Assert.assertEquals(expectedVideoElementWidth,realVideoElementWidth);
        Assert.assertEquals(expectedVideoElementHeight,realVideoElementHeight);
        Assert.assertEquals(expectedVideoElementXCoordinate,realVideoElementXCoordinate);
        Assert.assertEquals(expectedVideoElementYCoordinate,realVideoElementYCoordinate);
        Assert.assertEquals(expectedVideoElementLoop,realVideoElementLoop);

    }
}
