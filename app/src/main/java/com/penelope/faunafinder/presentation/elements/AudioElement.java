package com.penelope.faunafinder.presentation.elements;


import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.penelope.faunafinder.R;
import com.penelope.faunafinder.xml.slide.Slide;

import java.io.IOException;

import lombok.Getter;

/**
 * <code>AudioElement</code> implements the audio presentation element with behaviour respectful of
 * SWENG standard v3.
 */
@Getter
public class AudioElement extends PresentationElement implements View.OnClickListener, ViewElement {
    private static final int DP_SIZE = 100;
    private final String url;
    private final boolean loop;
    private final MediaPlayer mediaPlayer;

    /**
     * <code>AudioElement</code> constructor.
     *
     * @param url  URL to the audio resource.
     * @param loop Whether the resource should be played in a loop.
     * @param x    X coordinate on slide.
     * @param y    Y coordinate on slide.
     */
    public AudioElement(MediaPlayer mediaPlayer, String url, boolean loop, int x, int y) {
        super(x, y);
        this.url = url;
        this.loop = loop;
        if (mediaPlayer != null)
            this.mediaPlayer = mediaPlayer;
        else
            this.mediaPlayer = new MediaPlayer();
    }

    @Override
    public View applyView(View parent, ViewGroup container, Slide slide, int id) {
        ImageButton button = parent.findViewById(id);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        // Map screen position
        float xPos = (x * slide.getCalculatedWidth()) / (float) slide.getWidth();
        float yPos = dpToPx(y);

        // Load play icon
        Drawable icon = ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_bird);
        button.setBackground(icon);

        // Set layout rules
        if (noHorizontalLayoutRulesToApply(layoutParams))
            layoutParams.leftMargin = Math.round(xPos);
        layoutParams.topMargin = Math.round(yPos);

        // Map size on screen
        int size = dpToPx(DP_SIZE);
        layoutParams.width = size;
        layoutParams.height = size;

        // Apply layout params
        button.setLayoutParams(layoutParams);

        button.setOnClickListener(this);
        setUpMediaPlayer(container);

        button.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(@NonNull View v) {
            }

            @Override
            public void onViewDetachedFromWindow(@NonNull View v) {
                // Pause and seek to 0 when view leaves screen
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
            }
        });

        return button;
    }

    @Override
    public String getViewType() {
        return AUDIO_ELEMENT;
    }

    @Override
    public String getSearchableContent() {
        return null;
    }

    /**
     * Sets up the media player object with the required parameters.
     *
     * @param container {@link ViewGroup} containing the view.
     */
    private void setUpMediaPlayer(ViewGroup container) {
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA).build());

        // Prepare media player in the background
        Runnable runnable = () -> {
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();

                if (loop)
                    mediaPlayer.setLooping(true);

                // Seek to start once media has been played
                mediaPlayer.setOnCompletionListener(mediaPlayer -> mediaPlayer.seekTo(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();

        container.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(@NonNull View v) {
            }

            @Override
            public void onViewDetachedFromWindow(@NonNull View v) {
                // Release mediaplayer when container leaves the screen
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
            else
                mediaPlayer.start();
        }
    }
}
