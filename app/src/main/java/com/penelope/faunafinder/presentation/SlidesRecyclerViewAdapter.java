package com.penelope.faunafinder.presentation;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.penelope.faunafinder.R;
import com.penelope.faunafinder.presentation.elements.PresentationElement;
import com.penelope.faunafinder.utils.ListItemClickListener;
import com.penelope.faunafinder.xml.slide.Slide;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>SlidesRecyclerViewAdapter</code> is the {@link RecyclerView.Adapter} to be used when
 * displaying slides in a list.
 */
public class SlidesRecyclerViewAdapter extends RecyclerView.Adapter<SlideViewHolder> implements Filterable {
    private final List<Slide> initialSlides;
    private final ListItemClickListener listItemClickListener;
    private final int horizontalMargin;
    private List<Slide> slides;

    /**
     * <code>SlidesRecyclerViewAdapter</code> constructor.
     *
     * @param slides                The List of slides contained in the presentation.
     * @param listItemClickListener Listener callback for item click.
     * @param horizontalMargin      Horizontal margin for the slides.
     */
    public SlidesRecyclerViewAdapter(List<Slide> slides,
                                     ListItemClickListener listItemClickListener,
                                     int horizontalMargin) {
        this.slides = slides;
        this.initialSlides = slides;
        this.listItemClickListener = listItemClickListener;
        this.horizontalMargin = horizontalMargin;
    }


    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == Slide.STANDARD_TYPE)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide, parent,
                    false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_slide,
                    parent, false);

        return new SlideViewHolder(view, parent, listItemClickListener, horizontalMargin);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        // Draw slide content when view is drawn
        holder.draw(slides.get(position));
    }

    @Override
    public int getItemCount() {
        return slides.size();
    }

    @Override
    public int getItemViewType(int position) {
        return slides.get(position).getType();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Slide> filteredSlides = new ArrayList<>();

                // Empty query
                if (constraint.length() == 0)
                    return null;

                String lowercaseConstraint = constraint.toString().toLowerCase();

                for (Slide slide : initialSlides) {
                    // Filter by slide title
                    if (slide.getTitle().toLowerCase().contains(lowercaseConstraint)) {
                        filteredSlides.add(slide);
                        break;
                    }

                    // Filter by presentation elements content within slide
                    for (PresentationElement element : slide.getElements()) {
                        String searchableContent = element.getSearchableContent();
                        if (searchableContent != null &&
                                searchableContent.contains(lowercaseConstraint)) {
                            filteredSlides.add(slide);
                            break;
                        }
                    }
                }

                filterResults.count = filteredSlides.size();
                filterResults.values = filteredSlides;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null)
                    slides = (List<Slide>) results.values;
                else
                    slides = initialSlides;

                notifyDataSetChanged();
            }
        };
    }
}
