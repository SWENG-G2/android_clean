package sweng.campusbirdsguide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.StyledPlayerView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import sweng.campusbirdsguide.presentation.SlideViewHolder;
import sweng.campusbirdsguide.presentation.elements.PresentationElement;
import sweng.campusbirdsguide.presentation.elements.ShapeElement;
import sweng.campusbirdsguide.presentation.elements.ViewElement;
import sweng.campusbirdsguide.utils.ListItemClickListener;
import sweng.campusbirdsguide.xml.slide.Slide;

public class SlideViewHolderTest {
    @Mock
    private View itemView;
    @Mock
    private ViewGroup container;
    @Mock
    private ListItemClickListener listItemClickListener;
    @Mock
    private Slide slide;

    private SlideViewHolder slideViewHolder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        slideViewHolder = new SlideViewHolder(itemView, container, listItemClickListener, 16);
    }

    @Test
    public void setParameters_shouldSetLayoutParamsForStandardTypeSlide() {
        when(slide.getType()).thenReturn(Slide.STANDARD_TYPE);
        when(slide.getCalculatedWidth()).thenReturn(500);
        when(slide.getCalculatedHeight()).thenReturn(400);

        slideViewHolder.setParameters(slide);

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        assertEquals(500, layoutParams.width);
        assertEquals(400, layoutParams.height);
    }


    @Test
    public void setParameters_shouldSetLayoutParamsForExpandableTypeSlide() {
        when(slide.getType()).thenReturn(Slide.EXPANDABLE_TYPE);
        when(slide.getCalculatedWidth()).thenReturn(500);
        when(slide.getCalculatedHeight()).thenReturn(400);

        slideViewHolder.setParameters(slide);

        ViewGroup.LayoutParams relativeLayoutParams = slideViewHolder.getRelativeLayout().getLayoutParams();
        assertEquals(500, relativeLayoutParams.width);
        assertEquals(400, relativeLayoutParams.height);

        RecyclerView.LayoutParams containerLayoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        assertEquals(8, containerLayoutParams.getMarginStart());
        assertEquals(8, containerLayoutParams.getMarginEnd());
    }

    @Test
    public void canReplace_shouldReturnIdAtIndex() {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(100);
        ids.add(200);
        ids.add(300);

        int result = slideViewHolder.canReplace(ids, 1);

        assertEquals(200, result);
    }
    @Test
    public void canReplace_shouldReturnNegativeValueForInvalidIndex() {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(100);
        ids.add(200);
        ids.add(300);

        int result = slideViewHolder.canReplace(ids, 3);

        assertTrue(result < 0);
    }

    @Test
    public void adjustId_shouldGenerateUniqueIdAndUpdateList() {
        ArrayList<Integer> list = new ArrayList<>();
        int baseId = 1000;

        int result1 = slideViewHolder.adjustId(0, list, baseId);
        int result2 = slideViewHolder.adjustId(1, list, baseId);
        int result3 = slideViewHolder.adjustId(2, list, baseId);

        assertEquals(1000, result1);
        assertEquals(1001, result2);
        assertEquals(1002, result3);
        assertTrue(list.contains(1000));
        assertTrue(list.contains(1001));
        assertTrue(list.contains(1002));
    }

    @Test
    public void iterateObjects_shouldAddShapeElementsToShapesList() {
        Slide slide = mock(Slide.class);
        ArrayList<ShapeElement> shapes = new ArrayList<>();
        PresentationElement shapeElement1 = mock(ShapeElement.class);
        PresentationElement shapeElement2 = mock(ShapeElement.class);

        when(shapeElement1 instanceof ShapeElement).thenReturn(true);
        when(shapeElement2 instanceof ShapeElement).thenReturn(true);
        when(slide.getElements()).thenReturn(Arrays.asList(new PresentationElement[]{shapeElement1, shapeElement2}));

        slideViewHolder.iterateObjects(slide, shapes);

        assertEquals(2, shapes.size());
        assertTrue(shapes.contains((ShapeElement) shapeElement1));
        assertTrue(shapes.contains((ShapeElement) shapeElement2));
    }

    @Test
    public void iterateObjects_shouldAddAudioElementToRelativeLayout() {
        Slide slide = mock(Slide.class);
        ArrayList<ShapeElement> shapes = new ArrayList<>();
        ViewElement audioElement = mock(ViewElement.class);
        when(audioElement.getViewType()).thenReturn(PresentationElement.AUDIO_ELEMENT);

        when(slide.getElements()).thenReturn(new PresentationElement[]{audioElement});

        slideViewHolder.iterateObjects(slide, shapes);

        verify(slideViewHolder.getRelativeLayout()).addView(any(ImageButton.class));
    }

    @Test
    public void iterateObjects_shouldAddImageElementToRelativeLayout() {
        Slide slide = mock(Slide.class);
        ArrayList<ShapeElement> shapes = new ArrayList<>();
        ViewElement imageElement = mock(ViewElement.class);
        when(imageElement.getViewType()).thenReturn(PresentationElement.IMAGE_ELEMENT);

        when(slide.getElements()).thenReturn(new PresentationElement[]{imageElement});

        slideViewHolder.iterateObjects(slide, shapes);

        verify(slideViewHolder.getRelativeLayout()).addView(any(ImageView.class));
    }

    @Test
    public void iterateObjects_shouldAddTextElementToRelativeLayout() {
        Slide slide = mock(Slide.class);
        ArrayList<ShapeElement> shapes = new ArrayList<>();
        ViewElement textElement = mock(ViewElement.class);
        when(textElement.getViewType()).thenReturn(PresentationElement.TEXT_ELEMENT);

        when(slide.getElements()).thenReturn(new PresentationElement[]{textElement});

        slideViewHolder.iterateObjects(slide, shapes);

        verify(slideViewHolder.getRelativeLayout()).addView(any(TextView.class));
    }

    @Test
    public void iterateObjects_shouldAddVideoElementToRelativeLayout() {
        Slide slide = mock(Slide.class);
        ArrayList<ShapeElement> shapes = new ArrayList<>();
        ViewElement videoElement = mock(ViewElement.class);
        when(videoElement.getViewType()).thenReturn(PresentationElement.VIDEO_ELEMENT);

        when(slide.getElements()).thenReturn(Arrays.asList(new PresentationElement[]{(PresentationElement) videoElement}));

        slideViewHolder.iterateObjects(slide, shapes);

        verify(slideViewHolder.getRelativeLayout()).addView(any(StyledPlayerView.class));
    }

    @Test
    public void applyView_shouldApplyViewElementToRelativeLayout() {
        ViewElement viewElement = mock(ViewElement.class);
        Slide slide = mock(Slide.class);
        int id = 123;

        slideViewHolder.applyView(viewElement, slide, id);

        verify(viewElement).applyView(any(RelativeLayout.class), any(ViewGroup.class), eq(slide), eq(id));
    }

    @Test
    public void draw_shouldInvokeItemClickListenerOnClick() {
        when(slide.getId()).thenReturn(123);

        slideViewHolder.draw(slide);
        slideViewHolder.itemView.performClick();

        verify(listItemClickListener).onItemClick(123);
    }
}

