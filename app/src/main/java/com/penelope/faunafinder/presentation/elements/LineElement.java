package com.penelope.faunafinder.presentation.elements;


import android.graphics.Canvas;
import android.graphics.Paint;

import com.penelope.faunafinder.xml.slide.Slide;

import lombok.Getter;

/**
 * <code>LineElement</code> implements the line presentation element with behaviour respectful
 * of SWENG standard v3.
 */
@Getter
public class LineElement extends PresentationElement implements ShapeElement {
    private final int thickness;
    private final int fromX;
    private final int fromY;
    private final int toX;
    private final int toY;
    private final int colour;

    /**
     * <code>LineElement</code> constructor.
     * @param thickness Line thickness in DP.
     * @param fromX Point 1 X in slide coordinates.
     * @param fromY Point 1 Y in slide coordinates.
     * @param toX Point 2 X in slide coordinates.
     * @param toY Point 2 X in slide coordinate.
     * @param colour Line colour.
     */
    public LineElement(int thickness, int fromX, int fromY, int toX, int toY, int colour) {
        // Line doesn't have x and y
        super(0, 0);

        this.thickness = dpToPx(thickness);
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.colour = colour;
    }

    @Override
    public void draw(Canvas canvas, Slide slide) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(colour);
        paint.setStrokeWidth(thickness);

        // Map coordinates
        float startX = (fromX * canvas.getWidth()) / (float) slide.getWidth();
        float endX = (toX * canvas.getWidth()) / (float) slide.getWidth();
        float startY = dpToPx(fromY);
        float endY = dpToPx(toY);

        canvas.drawLine(startX, startY, endX, endY, paint);
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
