package com.penelope.faunafinder.presentation.elements;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.penelope.faunafinder.xml.slide.Slide;

@RunWith(RobolectricTestRunner.class)
public class LineElementTest {

    private static final int THICKNESS = 2;
    private static final int FROM_X = 5;
    private static final int FROM_Y = 15;
    private static final int TO_X = 10;
    private static final int TO_Y = 30;
    private static final int COLOUR = Color.RED;
    private static final long TIME_ON_SCREEN = -1;

    @Test
    @Config(qualifiers = "mdpi")
    public void drawIsCorrect() {
        // create line element
        LineElement lineElement = new LineElement(THICKNESS, FROM_X, FROM_Y, TO_X, TO_Y, COLOUR, TIME_ON_SCREEN);

        // mock canvas and slide objects
        Canvas canvas = mock(Canvas.class);
        Slide slide = mock(Slide.class);

        // set up canvas dimensions
        when(canvas.getWidth()).thenReturn(350);

        // set up slide dimensions
        when(slide.getWidth()).thenReturn(200);
        when(slide.getCalculatedWidth()).thenReturn(100);

        // call the draw() method
        lineElement.draw(canvas, slide);

        // create paint object with expected params
        Paint expectedLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        expectedLinePaint.setColor(COLOUR);
        expectedLinePaint.setStrokeWidth(THICKNESS);

        // verify expected against the actual
        assertEquals(expectedLinePaint.getColor(), lineElement.getColour());

        // verify the functions were called correctly
        verify(canvas, times(1)).drawLine(
                eq(8.75f),
                eq(15.0f),
                eq(17.5f),
                eq(30.0f),
                any(Paint.class));
    }

    @Test
    public void getViewTypeIsCorrect() {
        LineElement lineElement = new LineElement(THICKNESS, FROM_X, FROM_Y, TO_X, TO_Y, COLOUR, TIME_ON_SCREEN);

        assertNull(lineElement.getViewType());
    }

    @Test
    public void getSearchableContentIsCorrect() {
        LineElement lineElement = new LineElement(THICKNESS, FROM_X, FROM_Y, TO_X, TO_Y, COLOUR, TIME_ON_SCREEN);

        assertNull(lineElement.getSearchableContent());
    }


}