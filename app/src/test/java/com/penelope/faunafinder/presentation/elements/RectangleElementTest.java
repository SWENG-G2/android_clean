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
import android.graphics.Rect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.penelope.faunafinder.xml.slide.Slide;

@RunWith(RobolectricTestRunner.class)
public class RectangleElementTest {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 200;
    private static final int COLOUR = Color.BLUE;
    private static final int BORDER_WIDTH = 5;
    private static final int BORDER_COLOUR = Color.RED;
    private static final int X = 10;
    private static final int Y = 20;

    @Test
    @Config(qualifiers = "mdpi")
    public void drawIsCorrect() {
        // Create rectangle element
        RectangleElement rectangleElement = new RectangleElement(WIDTH, HEIGHT, COLOUR, BORDER_WIDTH, BORDER_COLOUR, X, Y);

        // Mock canvas and slide objects
        Canvas canvas = mock(Canvas.class);
        Slide slide = mock(Slide.class);

        // Set up slide dimensions
        when(slide.getWidth()).thenReturn(200);
        when(slide.getCalculatedWidth()).thenReturn(100);

        // Call the draw() method
        rectangleElement.draw(canvas, slide);

        // create paint object with expected params
        Paint expectedBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint expectedFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        expectedBorderPaint.setColor(BORDER_COLOUR);
        expectedBorderPaint.setStrokeWidth(BORDER_WIDTH);
        expectedBorderPaint.setStyle(Paint.Style.STROKE);

        expectedFillPaint.setColor(COLOUR);
        expectedFillPaint.setStyle(Paint.Style.FILL);

        // get the actual paint object
        Paint actualBorderPaint = rectangleElement.getBorderPaint();
        Paint actualFillPaint = rectangleElement.getFillPaint();

        // verify expected against the actual
        assertEquals(expectedBorderPaint.getColor(), actualBorderPaint.getColor());
        assertEquals(expectedBorderPaint.getStrokeWidth(), actualBorderPaint.getStrokeWidth(), 0.001);
        assertEquals(expectedBorderPaint.getStyle(), actualBorderPaint.getStyle());
        assertEquals(expectedFillPaint.getColor(), actualFillPaint.getColor());
        assertEquals(expectedFillPaint.getStyle(), actualFillPaint.getStyle());

        verify(canvas, times(2)).drawRect(
                eq(new Rect(5, 20, 55, 220)),
                any(Paint.class)
        );
    }

    @Test
    public void getViewTypeIsCorrect() {
        RectangleElement rectangleElement = new RectangleElement(WIDTH, HEIGHT, COLOUR, BORDER_WIDTH, BORDER_COLOUR, X, Y);

        assertNull(rectangleElement.getViewType());
    }

    @Test
    public void getSearchableContentIsCorrect() {
        RectangleElement rectangleElement = new RectangleElement(WIDTH, HEIGHT, COLOUR, BORDER_WIDTH, BORDER_COLOUR, X, Y);

        assertNull(rectangleElement.getSearchableContent());
    }

}