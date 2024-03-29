package com.penelope.faunafinder.presentation.elements;


import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.penelope.faunafinder.xml.slide.Slide;

import lombok.Getter;

/**
 * <code>RectangleElement</code> implements the rectangle presentation element with behaviour
 * respectful of SWENG standard v3.
 */
@Getter
public class RectangleElement extends PresentationElement implements ShapeElement {
    private final int width;
    private final int height;
    private final int colour;
    private final int borderWidth;
    private final int borderColour;
    private final long timeOnScreen;
    private final int shadowColour;
    private final int shadowDx;
    private final int shadowDy;
    private final int shadowRadius;
    private Paint borderPaint;
    private Paint fillPaint;
    private Paint shadowPaint;

    /**
     * <code>RectangleElement</code> constructor.
     *
     * @param width        Rectangle width in presentation units.
     * @param height       Rectangle height in presentation units.
     * @param colour       Fill colour.
     * @param borderWidth  Border width in DP.
     * @param borderColour Border Colour
     * @param x            X coordinate on slide.
     * @param y            Y coordinate on slide.
     * @param timeOnScreen The shape's time on screen, in milliseconds.
     * @param shadowColour The rectangle's shadow colour.
     * @param shadowDx     The rectangle's shadow dx.
     * @param shadowDy     The rectangle's shadow dy.
     * @param shadowRadius The rectangle's shadow radius.
     */
    public RectangleElement(int width, int height, int colour, int borderWidth,
                            int borderColour, int x, int y, long timeOnScreen, int shadowColour, int shadowDx, int shadowDy, int shadowRadius) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.borderWidth = borderWidth;
        this.borderColour = borderColour;
        this.timeOnScreen = timeOnScreen;
        this.shadowColour = shadowColour;
        this.shadowDx = shadowDx;
        this.shadowDy = shadowDy;
        this.shadowRadius = shadowRadius;
    }

    @Override
    public void draw(Canvas canvas, Slide slide) {
        int left = Math.round((x * slide.getCalculatedWidth()) / (float) slide.getWidth());
        int right =
                Math.round(left + (width * slide.getCalculatedWidth()) / (float) slide.getWidth());
        int top = dpToPx(y);
        int bottom = top + dpToPx(height);
        int border = dpToPx(borderWidth);

        // Set up paint objects
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Draw shadow
        if (shadowColour != Color.TRANSPARENT) {
            int blurRadius = (shadowRadius <= 0) ? 1 : shadowRadius;
            int dx = Math.round((shadowDx * slide.getCalculatedWidth()) / (float) slide.getWidth());
            int dy = dpToPx(shadowDy);
            shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            shadowPaint.setColor(shadowColour);
            shadowPaint.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL));

            Rect shadow = new Rect(left + dx, top + dy, right + dx, bottom + dy);
            canvas.drawRect(shadow, shadowPaint);
        }

        borderPaint.setColor(borderColour);
        borderPaint.setStrokeWidth(border);
        borderPaint.setStyle(Paint.Style.STROKE);

        // Border
        Rect rectangle = new Rect(left, top, right, bottom);
        canvas.drawRect(rectangle, borderPaint);

        fillPaint.setColor(colour);
        fillPaint.setStyle(Paint.Style.FILL);

        // Rectangle
        canvas.drawRect(rectangle, fillPaint);
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
