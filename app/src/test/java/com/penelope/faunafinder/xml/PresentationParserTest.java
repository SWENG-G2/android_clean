package com.penelope.faunafinder.xml;

import com.penelope.faunafinder.MainActivity;
import com.penelope.faunafinder.presentation.elements.AudioElement;
import com.penelope.faunafinder.presentation.elements.CircleElement;
import com.penelope.faunafinder.presentation.elements.ImageElement;
import com.penelope.faunafinder.presentation.elements.PresentationElement;
import com.penelope.faunafinder.presentation.elements.RectangleElement;
import com.penelope.faunafinder.presentation.elements.TextElement;
import com.penelope.faunafinder.presentation.elements.VideoElement;
import com.penelope.faunafinder.xml.slide.Slide;
import com.penelope.faunafinder.xml.slide.SlideFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(RobolectricTestRunner.class)
public class PresentationParserTest {
    // Number of slides for each test
    private static final int N_SLIDES_GOOD = 5;
    private static final int N_SLIDES_UNKNOWN = 2;

    private MainActivity mainActivity;

    @Before
    public void setUpActivity() {
        // The activity we use doesn't matter per se, we are testing the adapter here
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class).setup();
        mainActivity = activityController.get();
    }

    private String readAsset(String filename) throws IOException {
        InputStream inputStream = mainActivity.getClassLoader().getResourceAsStream(filename);

        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining());
    }

    @Test
    public void canParseGoodInput() throws IOException, XmlPullParserException {
        String testXML = readAsset("presentation.txt");

        PresentationParser classUnderTest = new PresentationParser();

        List<Slide> slides = classUnderTest.parse(testXML, SlideFactory.BASIC_SLIDE);

        Assert.assertEquals(N_SLIDES_GOOD, slides.size());

        slides.forEach(slide -> {
            List<PresentationElement> elements = slide.getElements();

            // No need to check all attributes as those are tested in the individual parser tests
            Assert.assertTrue(elements.get(0) instanceof TextElement);
            Assert.assertTrue(elements.get(1) instanceof TextElement);
            Assert.assertTrue(elements.get(2) instanceof ImageElement);
        });
    }

    @Test
    public void canHandleUnknownElementsInput() throws IOException, XmlPullParserException {
        String testXML = readAsset("unknown_elements_presentation.txt");

        PresentationParser classUnderTest = new PresentationParser();

        List<Slide> slides = classUnderTest.parse(testXML, SlideFactory.BASIC_SLIDE);

        Assert.assertEquals(N_SLIDES_UNKNOWN, slides.size());

        // First slide has rectangle, text, audio, image, circle in this order
        List<PresentationElement> elements = slides.get(0).getElements();
        Assert.assertTrue(elements.get(0) instanceof RectangleElement);
        Assert.assertTrue(elements.get(1) instanceof TextElement);
        Assert.assertTrue(elements.get(2) instanceof AudioElement);
        Assert.assertTrue(elements.get(3) instanceof ImageElement);
        Assert.assertTrue(elements.get(4) instanceof CircleElement);

        // Second slide has gif (unknown, should not affect order nor be present),
        // video, text, triangle (same as gif) in this order
        elements = slides.get(1).getElements();
        Assert.assertTrue(elements.get(0) instanceof VideoElement);
        Assert.assertTrue(elements.get(1) instanceof TextElement);
    }

    @Test
    public void throwsForMalformedElementInput() throws IOException {
        String testXML = readAsset("malformed_element_presentation.txt");

        PresentationParser classUnderTest = new PresentationParser();

        Assert.assertThrows(XmlPullParserException.class, () -> {
            classUnderTest.parse(testXML, SlideFactory.BASIC_SLIDE);
        });
    }

    @Test
    public void throwsForMalformedPresentationInput() throws IOException {
        String testXML = readAsset("malformed_presentation.txt");

        PresentationParser classUnderTest = new PresentationParser();

        Assert.assertThrows(XmlPullParserException.class, () -> {
            classUnderTest.parse(testXML, SlideFactory.BASIC_SLIDE);
        });
    }

}
