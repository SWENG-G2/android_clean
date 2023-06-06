package com.penelope.faunafinder.xml.slide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_HEIGHT;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_TITLE;
import static com.penelope.faunafinder.xml.slide.SlideConstants.SLIDE_WIDTH;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import com.penelope.faunafinder.xml.slide.BasicSlide;
import com.penelope.faunafinder.xml.slide.BirdSlide;
import com.penelope.faunafinder.xml.slide.DetailSlide;
import com.penelope.faunafinder.xml.slide.Slide;
import com.penelope.faunafinder.xml.slide.SlideFactory;

@RunWith(RobolectricTestRunner.class)
public class SlideFactoryTest {
    @Test
    public void canCreateBasicSlide() {
        Slide slide = SlideFactory.createSlide(SlideFactory.BASIC_SLIDE, SLIDE_WIDTH,
                SLIDE_HEIGHT, SLIDE_TITLE);

        assertTrue(slide instanceof BasicSlide);
        assertEquals(slide.getWidth(), SLIDE_WIDTH);
        assertEquals(slide.getHeight(), SLIDE_HEIGHT);
        assertEquals(slide.getTitle(), SLIDE_TITLE);
    }

    @Test
    public void canCreateBirdSlide() {
        Slide slide = SlideFactory.createSlide(SlideFactory.BIRD_SLIDE, SLIDE_WIDTH,
                SLIDE_HEIGHT, SLIDE_TITLE);

        assertTrue(slide instanceof BirdSlide);
        assertEquals(slide.getWidth(), SLIDE_WIDTH);
        assertEquals(slide.getHeight(), SLIDE_HEIGHT);
        assertEquals(slide.getTitle(), SLIDE_TITLE);
    }

    @Test
    public void canCreateDetailSlide() {
        Slide slide = SlideFactory.createSlide(SlideFactory.DETAIL_SLIDE, SLIDE_WIDTH,
                SLIDE_HEIGHT, SLIDE_TITLE);

        assertTrue(slide instanceof DetailSlide);
        assertEquals(slide.getWidth(), SLIDE_WIDTH);
        assertEquals(slide.getHeight(), SLIDE_HEIGHT);
        assertEquals(slide.getTitle(), SLIDE_TITLE);
    }

    @Test
    public void defaultsToBasicSlide() {
        Slide slide = SlideFactory.createSlide("Bad Type", SLIDE_WIDTH,
                SLIDE_HEIGHT, SLIDE_TITLE);

        assertTrue(slide instanceof BasicSlide);
        assertEquals(slide.getWidth(), SLIDE_WIDTH);
        assertEquals(slide.getHeight(), SLIDE_HEIGHT);
        assertEquals(slide.getTitle(), SLIDE_TITLE);
    }
}
