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
public class CircleElementTest {

    private static final int RADIUS = 10;
    private static final int COLOUR = Color.BLUE;
    private static final int BORDER_WIDTH = 2;
    private static final int BORDER_COLOUR = Color.BLACK;
    private static final int X = 20;
    private static final int Y = 30;
    private static final long TIME_ON_SCREEN = -1;
    private static final int SHADOW_COLOUR = Color.RED;
    private static final int SHADOW_DX = -10;
    private static final int SHADOW_DY = 10;
    private static final int SHADOW_RADIUS = 10;
    private static final float CX = 10;
    private static final float CY = 30;
    private static final float CALCULATED_SHADOW_DX = -5;

    @Test
    @Config(qualifiers = "mdpi")
    public void drawIsCorrect() {
        // create circle element
        CircleElement circleElement = new CircleElement(RADIUS, COLOUR, BORDER_WIDTH, BORDER_COLOUR, X, Y, TIME_ON_SCREEN, SHADOW_COLOUR, SHADOW_DX, SHADOW_DY, SHADOW_RADIUS);

        // mock canvas and slide objects
        Canvas canvas = mock(Canvas.class);
        Slide slide = mock(Slide.class);

        // set up slide dimensions
        when(slide.getWidth()).thenReturn(200);
        when(slide.getCalculatedWidth()).thenReturn(100);

        // call the draw() method
        circleElement.draw(canvas, slide);

        // create paint object with expected params
        Paint expectedBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        expectedBorderPaint.setColor(BORDER_COLOUR);
        expectedBorderPaint.setStrokeWidth(BORDER_WIDTH);
        expectedBorderPaint.setStyle(Paint.Style.STROKE);

        // create paint object with expected params
        Paint expectedFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        expectedFillPaint.setColor(COLOUR);
        expectedFillPaint.setStyle(Paint.Style.FILL);

        // Blur mask can't be tested. It has no accessible fields
        Paint expectedShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        expectedShadowPaint.setColor(SHADOW_COLOUR);

        // get the actual paint object
        Paint actualBorderPaint = circleElement.getBorderPaint();
        Paint actualFillPaint = circleElement.getFillPaint();
        Paint actualShadowPaint = circleElement.getShadowPaint();

        // verify expected against the actual
        assertEquals(expectedBorderPaint.getColor(), actualBorderPaint.getColor());
        assertEquals(expectedBorderPaint.getStrokeWidth(), actualBorderPaint.getStrokeWidth(), 0.001);
        assertEquals(expectedBorderPaint.getStyle(), actualBorderPaint.getStyle());
        assertEquals(expectedFillPaint.getColor(), actualFillPaint.getColor());
        assertEquals(expectedFillPaint.getStyle(), actualFillPaint.getStyle());
        assertEquals(expectedShadowPaint.getColor(), actualShadowPaint.getColor());

        // verify the functions were called correctly
        verify(canvas, times(1)).drawCircle(
                eq(CX + CALCULATED_SHADOW_DX),
                eq(CY + SHADOW_DY),
                eq((float) RADIUS),
                any(Paint.class));

        verify(canvas, times(2)).drawCircle(
                eq(CX),
                eq(CY),
                eq((float) RADIUS),
                any(Paint.class));
    }

    @Test
    public void getViewTypeIsCorrect() {
        CircleElement circleElement = new CircleElement(RADIUS, COLOUR, BORDER_WIDTH, BORDER_COLOUR, X, Y, TIME_ON_SCREEN, SHADOW_COLOUR, SHADOW_DX, SHADOW_DY, SHADOW_RADIUS);

        assertNull(circleElement.getViewType());
    }

    @Test
    public void getSearchableContentIsCorrect() {
        CircleElement circleElement = new CircleElement(RADIUS, COLOUR, BORDER_WIDTH, BORDER_COLOUR, X, Y, TIME_ON_SCREEN, SHADOW_COLOUR, SHADOW_DX, SHADOW_DY, SHADOW_RADIUS);

        assertNull(circleElement.getSearchableContent());
    }
}
