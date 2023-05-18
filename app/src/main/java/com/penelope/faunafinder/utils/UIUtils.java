package com.penelope.faunafinder.utils;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.penelope.faunafinder.R;
import com.penelope.faunafinder.presentation.SlidesRecyclerViewAdapter;
import com.penelope.faunafinder.xml.PresentationParser;
import com.penelope.faunafinder.xml.slide.Slide;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * <code>UIUtils</code> is a UI utilities class.
 */
public class UIUtils {
    /**
     * Populates a list from an xml content.
     *
     * @param xml                 The xml content.
     * @param parent              The Parent view to the list.
     * @param slideType           The type of slide to use.
     * @param listItemClickAction Action to perform when item is clicked.
     * @param horizontalMargin    Horizontal margin for the slides.
     * @return A fully set-up {@link SlidesRecyclerViewAdapter} or null on failure.
     */
    public static SlidesRecyclerViewAdapter populateList(String xml, View parent, String slideType,
                                                         ListItemClickAction listItemClickAction,
                                                         int horizontalMargin) {
        PresentationParser parser = new PresentationParser();

        try {
            List<Slide> slides = parser.parse(xml, slideType);

            // Construct click listener callback
            ListItemClickListener listItemClickListener = id -> {
                try {
                    if (listItemClickAction != null)
                        listItemClickAction.performAction(id);
                } catch (NumberFormatException ignored) {
                }
                // Exception can be ignored, it would happen only on detail slides
            };


            SlidesRecyclerViewAdapter slidesRecyclerViewAdapter =
                    new SlidesRecyclerViewAdapter(slides, listItemClickListener, horizontalMargin);
            RecyclerView recyclerView = parent.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext()));
            recyclerView.setAdapter(slidesRecyclerViewAdapter);

            return slidesRecyclerViewAdapter;
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
