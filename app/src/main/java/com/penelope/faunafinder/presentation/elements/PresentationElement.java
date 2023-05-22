package com.penelope.faunafinder.presentation.elements;


import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import lombok.Getter;

/**
 * <code>PresentationElement</code> is the abstract class which provides
 * all the methods and constants every presentation element needs and must provide.
 */
@Getter
public abstract class PresentationElement {
    protected static final DisplayMetrics displayMetrics
            = Resources.getSystem().getDisplayMetrics();
    protected static final int MATCH_X_CLIENT_SIDE = -1;
    protected static final int MATCH_WIDTH_CLIENT_SIDE = -1;
    protected static final int ALIGN_CENTER_OF_PARENT = -2;
    private static final int ALIGN_END_OF_PARENT = -3;
    protected static final int MATCH_PARENT = -4;
    protected static final int WRAP_CONTENT = -5;

    // View types
    public static final String AUDIO_ELEMENT = "audio";
    public static final String IMAGE_ELEMENT = "image";
    public static final String TEXT_ELEMENT = "text";
    public static final String VIDEO_ELEMENT = "video";


    protected final int x, y;

    protected PresentationElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method an element provides to inform about the its type
     *
     * @return {@link #AUDIO_ELEMENT} or {@link #IMAGE_ELEMENT} or {@link #TEXT_ELEMENT} or {@link #VIDEO_ELEMENT}
     */
    abstract public String getViewType();

    /**
     * Method used by the adapter to filter search queries.
     *
     * @return The text content of an element, or null if no searchable content should be provided.
     */
    abstract public String getSearchableContent();

    /**
     * Utility used to convert DPs into pixels.
     *
     * @param input The value to convert in DPs.
     * @return The converted value in pixels.
     */
    protected int dpToPx(int input) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, input,
                displayMetrics));
    }

    /**
     * Checks if layout rules are to be applied based on the requested screen position.
     *
     * @param layoutParams {@link RelativeLayout.LayoutParams} of the view being manipulated by the element.
     * @return Boolean, whether no rules have been applied (true) or a rule has been applied (false).
     */
    protected boolean noHorizontalLayoutRulesToApply(RelativeLayout.LayoutParams layoutParams) {
        switch (x) {
            case ALIGN_END_OF_PARENT:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                return false;
            case ALIGN_CENTER_OF_PARENT:
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                return false;
            default:
                return true;
        }
    }
}
