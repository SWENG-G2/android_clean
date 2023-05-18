package com.penelope.faunafinder.presentation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.penelope.faunafinder.presentation.elements.ShapeElement;
import com.penelope.faunafinder.xml.slide.Slide;

import java.util.List;

import lombok.Setter;

/**
 * <code>CanvasView</code> is a simple view used as canvas in presentations.
 */
public class CanvasView extends View {
    @Setter
    private Slide slide = null;
    @Setter
    private List<ShapeElement> shapes = null;

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (slide != null && shapes != null) {
            // Draw every shape in slide
            for (ShapeElement element : shapes) {
                element.draw(canvas, slide);
            }
        }
    }
}
