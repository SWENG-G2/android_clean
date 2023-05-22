package com.penelope.faunafinder.xml.slide;

import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_HEIGHT;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_TITLE;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_TITLE_ID;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_WIDTH;

import com.penelope.faunafinder.xml.slide.BasicSlide;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class BasicSlideTest {
    @Test
    public void idIsAlwaysNumerical() {
        BasicSlide classUnderTest = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE_ID);
        assertEquals(Integer.parseInt(SLIDE_TITLE_ID), classUnderTest.getId());

        classUnderTest = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);
        assertEquals(-1, classUnderTest.getId());
    }
}
