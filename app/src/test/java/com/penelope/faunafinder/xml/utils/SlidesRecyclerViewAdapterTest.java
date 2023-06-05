package sweng.campusbirdsguide;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import sweng.campusbirdsguide.presentation.SlideViewHolder;
import sweng.campusbirdsguide.presentation.SlidesRecyclerViewAdapter;
import sweng.campusbirdsguide.utils.ListItemClickListener;
import sweng.campusbirdsguide.xml.slide.Slide;

@RunWith(MockitoJUnitRunner.class)
public class SlidesRecyclerViewAdapterTest {

    private SlidesRecyclerViewAdapter adapter;
    private List<Slide> slides;
    private ListItemClickListener itemClickListener;

    @Before
    public void setUp() {
        slides = new ArrayList<>();
        slides.add(createMockSlide("Slide 1", Slide.STANDARD_TYPE));
        slides.add(createMockSlide("Slide 2", Slide.EXPANDABLE_TYPE));
        slides.add(createMockSlide("Slide 3", Slide.STANDARD_TYPE));

        itemClickListener = mock(ListItemClickListener.class);
        adapter = new SlidesRecyclerViewAdapter(slides, itemClickListener, 10);
    }

    @Test
    public void onCreateViewHolder_shouldInflateStandardSlideViewHolder() {
        ViewGroup parent = mock(ViewGroup.class);
        LayoutInflater inflater = mock(LayoutInflater.class);
        View itemView = mock(View.class);
        when(inflater.inflate(R.layout.slide, parent, false)).thenReturn(itemView);
        when(parent.getContext()).thenReturn(mock(Context.class));
        when(parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(inflater);

        SlideViewHolder viewHolder = adapter.onCreateViewHolder(parent, Slide.STANDARD_TYPE);

        assertNotNull(viewHolder);
        assertEquals(itemView, viewHolder.itemView);
        // Assert other expectations for the standard slide view holder
    }

    @Test
    public void onCreateViewHolder_shouldInflateExpandableSlideViewHolder() {
        ViewGroup parent = mock(ViewGroup.class);
        LayoutInflater inflater = mock(LayoutInflater.class);
        View itemView = mock(View.class);
        when(inflater.inflate(R.layout.expandable_slide, parent, false)).thenReturn(itemView);
        when(parent.getContext()).thenReturn(mock(Context.class));
        when(parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(inflater);

        SlideViewHolder viewHolder = adapter.onCreateViewHolder(parent, Slide.EXPANDABLE_TYPE);

        assertNotNull(viewHolder);
        assertEquals(itemView, viewHolder.itemView);
        // Assert other expectations for the expandable slide view holder
    }

    @Test
    public void onBindViewHolder_shouldDrawSlideOnViewHolder() {
        Slide mockSlide = createMockSlide("Mock Slide", Slide.STANDARD_TYPE);
        SlideViewHolder viewHolder = mock(SlideViewHolder.class);
        doNothing().when(viewHolder).draw(mockSlide);

        adapter.onBindViewHolder(viewHolder, 0);

        // Verify that the SlideViewHolder's draw method is called with the correct Slide object
        verify(viewHolder).draw(mockSlide);
    }

    @Test
    public void getItemCount_shouldReturnCorrectItemCount() {
        int itemCount = adapter.getItemCount();

        assertEquals(slides.size(), itemCount);
    }

    @Test
    public void getItemViewType_shouldReturnCorrectViewType() {
        int viewType1 = adapter.getItemViewType(0);
        int viewType2 = adapter.getItemViewType(1);
        int viewType3 = adapter.getItemViewType(2);

        assertEquals(Slide.STANDARD_TYPE, viewType1);
        assertEquals(Slide.EXPANDABLE_TYPE, viewType2);
        assertEquals(Slide.STANDARD_TYPE, viewType3);
    }

    @Test
    public void getFilter_shouldReturnNonNullFilter() {
        Filter filter = adapter.getFilter();
        assertNotNull(filter);
    }

    @Test
    public void filter_performFiltering_shouldReturnFilteredSlides() {
        Filter filter = adapter.getFilter();

        Filter.FilterResults results = filter.performFiltering("Slide 1");
        assertNotNull(results);

        List<Slide> filteredSlides = (List<Slide>) results.values;
        assertEquals(1, filteredSlides.size());
        assertEquals("Slide 1", filteredSlides.get(0).getTitle());
    }

    @Test
    public void filter_publishResults_shouldUpdateSlides() {
        Filter filter = adapter.getFilter();
        assertNotNull(filter);

        Filter.FilterResults results = new Filter.FilterResults();
        List<Slide> filteredSlides = new ArrayList<>();
        filteredSlides.add(createMockSlide("Slide 2", Slide.EXPANDABLE_TYPE));
        results.count = filteredSlides.size();
        results.values = filteredSlides;

        filter.publishResults("Slide 2", results);

        assertEquals(filteredSlides, adapter.slides);
    }


    // Helper method to create a mock Slide object
    private Slide createMockSlide(String title, int type) {
        Slide mockSlide = mock(Slide.class);
        when(mockSlide.getTitle()).thenReturn(title);
        when(mockSlide.getType()).thenReturn(type);
        // Set up other necessary behavior and return values for the mock Slide object
        return mockSlide;
    }

}



}
