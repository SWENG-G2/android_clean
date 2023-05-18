package com.penelope.faunafinder.xml.slide;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.penelope.faunafinder.presentation.elements.PresentationElement;

import java.util.ArrayList;

/**
 * <code>AbstractSlide</code> provides the basic slide functionality.
 */
public abstract class AbstractSlide implements Slide {
    protected final DisplayMetrics displayMetrics;
    private final String title;
    private final ArrayList<PresentationElement> elements;
    protected int width, height, calculatedWidth, calculatedHeight, type;

    /**
     * <code>AbstractSlide</code> constructor.
     *
     * @param width  The slide's width.
     * @param height The slide's height.
     * @param title  The slide's title.
     */
    public AbstractSlide(int width, int height, String title) {
        displayMetrics = Resources.getSystem().getDisplayMetrics();

        this.height = height;
        this.width = width;
        this.calculatedWidth = displayMetrics.widthPixels;
        // Assume height in DP, makes it easier to work with reference system
        this.calculatedHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, displayMetrics));
        this.title = title;
        this.type = STANDARD_TYPE;

        this.elements = new ArrayList<>();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getCalculatedWidth() {
        return calculatedWidth;
    }

    @Override
    public int getCalculatedHeight() {
        return calculatedHeight;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public ArrayList<PresentationElement> getElements() {
        return elements;
    }

    @Override
    public void addElement(PresentationElement presentationElement) {
        elements.add(presentationElement);
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public int getId() {
        try {
            return Integer.parseInt(getTitle());
        } catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }
}
