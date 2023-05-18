package com.penelope.faunafinder.presentation.elements;


import android.graphics.Canvas;
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
     */
    public RectangleElement(int width, int height, int colour, int borderWidth,
                            int borderColour, int x, int y) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.colour = colour;
        this.borderWidth = borderWidth;
        this.borderColour = borderColour;
    }

    @Override
    public void draw(Canvas canvas, Slide slide) {
        // Map coordinates
        int left = Math.round((x * slide.getCalculatedWidth()) / (float) slide.getWidth());
        int right =
                Math.round(left + (width * slide.getCalculatedWidth()) / (float) slide.getWidth());
        int top = dpToPx(y);
        int bottom = top + dpToPx(height);
        int border = dpToPx(borderWidth);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(borderColour);
        paint.setStrokeWidth(border);
        paint.setStyle(Paint.Style.STROKE);

        // Border
        Rect rectangle = new Rect(left, top, right, bottom);
        canvas.drawRect(rectangle, paint);

        paint.setColor(colour);
        paint.setStyle(Paint.Style.FILL);

        // Rectangle
        canvas.drawRect(rectangle, paint);
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
