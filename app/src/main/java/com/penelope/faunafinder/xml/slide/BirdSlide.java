package com.penelope.faunafinder.xml.slide;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.penelope.faunafinder.R;

/**
 * <code>BirdSlide</code> is the slide to be used in a Campus presentation, where all birds are
 * listed.
 */
public class BirdSlide extends AbstractSlide {
    /**
     * <code>BirdSlide</code> constructor.
     *
     * @param width  The slide's width.
     * @param height The slide's height.
     * @param title  The slide's title.
     */
    public BirdSlide(int width, int height, String title) {
        super(width, height, title);

        // Recalculate width to account for horizontal margin
        this.calculatedWidth =
                Math.round(
                        this.calculatedWidth - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                                , HORIZONTAL_MARGIN, this.displayMetrics));
    }

    @Override
    public void slideSpecifics(View containerView, Context context) {
        // Set slide background
        containerView.setBackground(ContextCompat.getDrawable(context, R.drawable.slide_bg));
    }
}
