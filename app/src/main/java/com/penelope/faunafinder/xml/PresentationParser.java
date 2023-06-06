package com.penelope.faunafinder.xml;

import android.util.Xml;

import com.penelope.faunafinder.presentation.elements.TextElement;
import com.penelope.faunafinder.xml.slide.Slide;
import com.penelope.faunafinder.xml.slide.SlideFactory;
import com.penelope.faunafinder.xml.utils.AudioParser;
import com.penelope.faunafinder.xml.utils.CircleParser;
import com.penelope.faunafinder.xml.utils.ImageParser;
import com.penelope.faunafinder.xml.utils.LineParser;
import com.penelope.faunafinder.xml.utils.RectangleParser;
import com.penelope.faunafinder.xml.utils.TextParser;
import com.penelope.faunafinder.xml.utils.VideoParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>PresentationParsers</code> provides an high level interface to parse a presentation.
 */
public class PresentationParser {
    private static final String NAME_SPACE = null;

    // Attributes
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    // Tags
    private static final String PRESENTATION = "presentation";
    private static final String SLIDE = "slide";
    private static final String TEXT = "text";
    private static final String LINE = "line";
    private static final String TITLE = "title";
    private static final String RECTANGLE = "rectangle";
    private static final String IMAGE = "image";
    private static final String CIRCLE = "circle";
    private static final String AUDIO = "audio";
    private static final String VIDEO = "video";


    /**
     * Iterates through each tag in the presentation and extracts the relevant data.
     * @param xmlPullParser The xmlPullParser which loaded the presentation.
     * @param slideType The type of slide to use.
     * @return A list containing the slides in the presentation.
     * @throws XmlPullParserException
     * @throws IOException
     */
    private List<Slide> parsePresentation(XmlPullParser xmlPullParser, String slideType) throws XmlPullParserException, IOException {
        List<Slide> slides = new ArrayList<>();

        // Slide we currently operate on
        Slide workingSlide = null;
        TextElement text = null;

        xmlPullParser.require(XmlPullParser.START_TAG, NAME_SPACE, PRESENTATION);

        int eventType = xmlPullParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    switch (xmlPullParser.getName()) {
                        case SLIDE: {
                            String title = xmlPullParser.getAttributeValue(NAME_SPACE, TITLE);
                            int width = Integer.parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, WIDTH));
                            int height = Integer.parseInt(xmlPullParser.getAttributeValue(NAME_SPACE, HEIGHT));

                            workingSlide = SlideFactory.createSlide(slideType, width, height, title);
                            break;
                        }
                        case TEXT: {
                            if (workingSlide != null) {
                                text = TextParser.parseText(xmlPullParser);
                                workingSlide.addElement(text);
                            }
                            break;
                        }
                        case LINE:
                            if (workingSlide != null)
                                workingSlide.addElement(LineParser.parseLine(xmlPullParser));
                            break;
                        case RECTANGLE:
                            if (workingSlide != null)
                                workingSlide.addElement(RectangleParser.parseRectangle(xmlPullParser));
                            break;
                        case IMAGE:
                            if (workingSlide != null)
                                workingSlide.addElement(ImageParser.parseImage(xmlPullParser));
                            break;
                        case CIRCLE:
                            if (workingSlide != null)
                                workingSlide.addElement(CircleParser.parseCircle(xmlPullParser));
                            break;
                        case AUDIO:
                            if (workingSlide != null)
                                workingSlide.addElement(AudioParser.parseAudio(xmlPullParser));
                            break;
                        case VIDEO:
                            if (workingSlide != null)
                                workingSlide.addElement(VideoParser.parseVideo(xmlPullParser));
                            break;
                        default:
                            break;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    switch (xmlPullParser.getName()) {
                        case SLIDE:
                            slides.add(workingSlide);
                            workingSlide = null;
                            break;
                        case TEXT:
                            text = null;
                            break;
                        default:
                            break;
                    }
                    break;
                case XmlPullParser.TEXT:
                    String content = xmlPullParser.getText();
                    if (text != null && content != null)
                        text.setContent(content);
                    break;
                default:
                    break;
            }

            eventType = xmlPullParser.next();
        }

        return slides;
    }

    /**
     * Parses a presentation.
     * @param input The XML presentation as string.
     * @param slideType The type of slide to use.
     * @return A list containing the slides in the presentation.
     * @throws XmlPullParserException
     * @throws IOException
     */
    public List<Slide> parse(String input, String slideType) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        xmlPullParser.setInput(new StringReader(input));
        xmlPullParser.nextTag();


        return parsePresentation(xmlPullParser, slideType);
    }
}
