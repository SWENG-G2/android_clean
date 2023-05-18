package com.penelope.faunafinder.presentation.elements;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.penelope.faunafinder.xml.slide.Slide;

import lombok.Getter;

@Getter
public class ImageElement extends PresentationElement implements ViewElement {
    private final String url;
    private final int width;
    private final int height;
    private final long timeOnScreen;
    private final int rotation;
    private final long delay;

    public ImageElement(String url, int width, int height,
                        int x, int y, int rotation, long delay,
                        long timeOnScreen) {
        super(x, y);
        this.url = url;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.delay = delay;
        this.timeOnScreen = timeOnScreen;
    }

    //Inherited methods
    @Override
    public String getViewType() {
        return IMAGE_ELEMENT;
    }

    @Override
    public String getSearchableContent() {
        return null;
    }

    @Override
    public View applyView(View parent, ViewGroup container, Slide slide, int id) {
        ImageView imageView = (ImageView) parent.findViewById(id);

        //format image for scale position and rotation
        int viewWidth = Math.round((width * slide.getCalculatedWidth()) / (float) slide.getWidth());
        int viewHeight = height != MATCH_WIDTH_CLIENT_SIDE ? dpToPx(height) : viewWidth;
        int leftMarg = Math.round((x * slide.getCalculatedWidth()) / (float) slide.getWidth());
        int topMarg = dpToPx(y);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(viewWidth, viewHeight);
        layoutParams.leftMargin = leftMarg;
        layoutParams.topMargin = topMarg;
        noHorizontalLayoutRulesToApply(layoutParams);
        imageView.setLayoutParams(layoutParams);
        imageView.setRotation((float) rotation);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        Glide.with(parent)
                .load(url)
                .fitCenter()
                .into(imageView);


        //account for delay and timeOnScreen
        if (delay != -1) {
            imageView.setVisibility(View.INVISIBLE);
            imageView.postDelayed(() -> imageView.setVisibility(View.VISIBLE), delay);
        }

        if (timeOnScreen != -1) {
            imageView.postDelayed(() -> imageView.setVisibility(View.INVISIBLE), delay + timeOnScreen);
        }

        return imageView;
    }
}