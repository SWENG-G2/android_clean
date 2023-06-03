package com.penelope.faunafinder.presentation.elements;


import android.graphics.Canvas;
import android.graphics.Paint;

import com.penelope.faunafinder.xml.slide.Slide;

import lombok.Getter;

/**
 * <code>CircleElement</code> implements the circle presentation element with behaviour respectful
 * of SWENG standard v3.
 */
@Getter
public class CircleElement extends PresentationElement implements ShapeElement {
    private final int radius;
    private final int colour;
    private final int borderWidth;
    private final int borderColour;
    private final long timeOnScreen;
    private Paint borderPaint;
    private Paint fillPaint;

    /**
     * <code>CircleElement</code> constructor.
     *
     * @param radius       The circle's radius in presentation units.
     * @param colour       Fill colour.
     * @param borderWidth  Border width, in presentation units.
     * @param borderColour Border colour.
     * @param x            X coordinate on slide.
     * @param y            Y coordinate on slide.
     * @param timeOnScreen The shape's time on screen, in milliseconds.
     */
    public CircleElement(int radius, int colour, int borderWidth, int borderColour, int x, int y,
                         long timeOnScreen) {
        super(x, y);
        this.radius = radius;
        this.colour = colour;
        this.borderWidth = borderWidth;
        this.borderColour = borderColour;
        this.timeOnScreen = timeOnScreen;
    }

    @Override
    public void draw(Canvas canvas, Slide slide) {
        // Map screen position
        int cx = Math.round((x * slide.getCalculatedWidth()) / (float) slide.getWidth());
        int cy;

        int calculatedRadius = dpToPx(radius);
        int border = dpToPx(borderWidth);

        if (x == ALIGN_CENTER_OF_PARENT) {
            cx = canvas.getWidth() / 2;
        }

        if (y == MATCH_X_CLIENT_SIDE)
            cy = cx;
        else if (y < 0) {
            // Server asked to pad client side
            // We know something is above in DP so we add that to the circle radius
            int padding = dpToPx(Math.abs(y));
            cy = calculatedRadius + padding;
        } else
            cy = dpToPx(y);


        // Set up paint objects
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Border
        borderPaint.setColor(borderColour);
        borderPaint.setStrokeWidth(border);
        borderPaint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(cx, cy, calculatedRadius, borderPaint);

        // Circle
        fillPaint.setColor(colour);
        fillPaint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(cx, cy, calculatedRadius, fillPaint);
    }

    @Override
    public String getViewType() {
        return null;
    }

    @Override
    public String getSearchableContent() {
        return null;
    }
}