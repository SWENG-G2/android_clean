package com.penelope.faunafinder.presentation;

import android.graphics.Canvas;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.util.ArrayList;
import java.util.List;

import com.penelope.faunafinder.MainActivity;
import com.penelope.faunafinder.R;
import com.penelope.faunafinder.presentation.CanvasView;
import com.penelope.faunafinder.presentation.elements.ShapeElement;
import com.penelope.faunafinder.xml.slide.BasicSlide;

@RunWith(RobolectricTestRunner.class)
public class CanvasViewTest {
    private static final int SLIDE_WIDTH = 1920;
    private static final int SLIDE_HEIGHT = 1080;
    private static final String SLIDE_TITLE = "Test slide title";
    private static final int SHAPES = 10;

    private BasicSlide slide;
    private RelativeLayout parent;

    @Before
    public void setUp() {
        try (ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class)) {
            controller.setup();
            // Dummy activity
            MainActivity mainActivity = controller.get();

            // Dummy slide
            slide = new BasicSlide(SLIDE_WIDTH, SLIDE_HEIGHT, SLIDE_TITLE);

            // The upper view group
            ConstraintLayout constraintLayout = mainActivity.findViewById(R.id.main_activity);

            // The "slide"
            parent = new RelativeLayout(mainActivity);

            constraintLayout.addView(parent);
        }
    }

    @Test
    public void drawsShapes() {
        CanvasView classUnderTest = new CanvasView(parent.getContext());

        // Canvas passed to CanvasView when forcing drawing for test
        Canvas canvas = new Canvas();

        // Mock shape element to check interactions
        ShapeElement shapeElementMock = mock(ShapeElement.class);
        List<ShapeElement> shapes = new ArrayList<>();
        for (int i = 0; i < SHAPES; i++)
            shapes.add(shapeElementMock);

        // Configure CanvasView
        classUnderTest.setSlide(slide);
        classUnderTest.setShapes(shapes);

        // Add CanvasView to parent
        parent.addView(classUnderTest);

        // Force drawing in Robolectric
        classUnderTest.draw(canvas);

        // Ensure shapes are drawn
        verify(shapeElementMock, times(SHAPES)).draw(eq(canvas), eq(slide));
    }
}
