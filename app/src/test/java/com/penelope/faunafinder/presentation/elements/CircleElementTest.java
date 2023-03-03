package sweng.campusbirdsguide.presentation.elements;

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

import sweng.campusbirdsguide.xml.slide.Slide;

@RunWith(RobolectricTestRunner.class)
public class CircleElementTest {

    private static final int RADIUS = 10;
    private static final int COLOUR = Color.BLUE;
    private static final int BORDER_WIDTH = 2;
    private static final int BORDER_COLOUR = Color.BLACK;
    private static final int X = 20;
    private static final int Y = 30;

    @Test
    @Config(qualifiers = "mdpi")
    public void drawIsCorrect() {
        // create circle element
        CircleElement circleElement = new CircleElement(RADIUS, COLOUR, BORDER_WIDTH, BORDER_COLOUR, X, Y);

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

        // get the actual paint object
        Paint actualBorderPaint = circleElement.getBorderPaint();

        // verify expected against the actual
        assertEquals(expectedBorderPaint.getColor(), actualBorderPaint.getColor());
        assertEquals(expectedBorderPaint.getStrokeWidth(), actualBorderPaint.getStrokeWidth(), 0.001);
        assertEquals(expectedBorderPaint.getStyle(), actualBorderPaint.getStyle());

        // verify the functions were called correctly
        verify(canvas, times(2)).drawCircle(eq(10f), eq(30f), eq(10f), any(Paint.class));
    }

    @Test
    public void getViewTypeIsCorrect() {
        CircleElement circleElement = new CircleElement(RADIUS, COLOUR, BORDER_WIDTH, BORDER_COLOUR, X, Y);

        assertNull(circleElement.getViewType());
    }

    @Test
    public void getSearchableContentIsCorrect() {
        CircleElement circleElement = new CircleElement(RADIUS, COLOUR, BORDER_WIDTH, BORDER_COLOUR, X, Y);

        assertNull(circleElement.getSearchableContent());
    }

}