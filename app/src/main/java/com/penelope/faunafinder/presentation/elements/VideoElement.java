package com.penelope.faunafinder.presentation.elements;


import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.penelope.faunafinder.R;
import com.penelope.faunafinder.xml.slide.Slide;

import lombok.Getter;

/**
 * <code>VideoElement</code> implements the video presentation element with behaviour respectful
 * of SWENG standard v3.
 */
@Getter
public class VideoElement extends PresentationElement implements ViewElement {
    private final String url;
    private final int width;
    private final int height;
    private final boolean loop;

    /**
     * <code>VideoElement</code> constructor.
     *
     * @param url    URL to the video resource.
     * @param width  Video width in presentation units.
     * @param height Video height in presentation units.
     * @param x      X coordinate on slide.
     * @param y      Y coordinate on slide.
     * @param loop   Whether the video resource sould be playd in a loop.
     */
    public VideoElement(String url, int width, int height, int x, int y, boolean loop) {
        super(x, y);
        this.url = url;
        this.width = width;
        this.height = height;
        this.loop = loop;
    }

    @Override
    public View applyView(View parent, ViewGroup container, Slide slide, int id) {
        // Load and set up ExoPlayer
        StyledPlayerView styledPlayerView = parent.findViewById(id);
        ExoPlayer exoPlayer = new ExoPlayer.Builder(parent.getContext()).build();
        styledPlayerView.setPlayer(exoPlayer);
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Map coordinates and size
        int xPos = Math.round((x * slide.getCalculatedWidth()) / (float) slide.getWidth());
        int yPos = dpToPx(y);
        int calculatedWidth =
                Math.round((width * slide.getCalculatedWidth()) / (float) slide.getWidth());
        int calculatedHeight = dpToPx(height);

        // Apply layout rules
        if (noHorizontalLayoutRulesToApply(layoutParams))
            layoutParams.leftMargin = xPos;
        layoutParams.topMargin = yPos;
        layoutParams.width = calculatedWidth;
        layoutParams.height = calculatedHeight;

        styledPlayerView.setLayoutParams(layoutParams);

        Uri videoURI = Uri.parse(url);
        MediaItem video = MediaItem.fromUri(videoURI);

        exoPlayer.addMediaItem(video);
        exoPlayer.prepare();

        container.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                // Release player when container leaves screen
                exoPlayer.release();
            }
        });

        styledPlayerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(@NonNull View v) {
            }

            @Override
            public void onViewDetachedFromWindow(@NonNull View v) {
                // Pause player when it leaves the screen
                if (exoPlayer.isPlaying())
                    exoPlayer.pause();
            }
        });

        return styledPlayerView;
    }

    @Override
    public String getViewType() {
        return VIDEO_ELEMENT;
    }

    @Override
    public String getSearchableContent() {
        return null;
    }
}