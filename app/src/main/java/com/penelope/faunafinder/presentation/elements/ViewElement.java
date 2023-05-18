package com.penelope.faunafinder.presentation.elements;

import android.view.View;
import android.view.ViewGroup;

import com.penelope.faunafinder.xml.slide.Slide;

/**
 * Any class extending {@link PresentationElement} and handling a class which extends {@link View}
 * must implement this interface.
 */
public interface ViewElement {
    /**
     * Method called by the ViewHolder to apply view manipulations.
     *
     * @param parent    The View containing the element, usually {@link android.widget.RelativeLayout}
     * @param container The ViewGroup attached to the ViewHolder.
     * @param slide     The slide the element belongs to.
     * @param id        The ID of the View the element is going to manipulate.
     * @return The manipulated View.
     */
    View applyView(View parent, ViewGroup container, Slide slide, int id);
}
