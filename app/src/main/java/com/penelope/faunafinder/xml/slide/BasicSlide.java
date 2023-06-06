package com.penelope.faunafinder.xml.slide;

import android.content.Context;
import android.view.View;

/**
 * <code>BasicSlide</code> is the simplest form of slide. Effectively a clone of
 * {@link AbstractSlide}.
 */
public class BasicSlide extends AbstractSlide {

    /**
     * <code>BasicSlide</code> constructor.
     *
     * @param width  The slide's width.
     * @param height The slide's height.
     * @param title  The slide's title.
     */
    public BasicSlide(int width, int height, String title) {
        super(width, height, title);
    }

    @Override
    public void slideSpecifics(View containerView, Context context) {
        // No-op, nothing specific
    }
}
