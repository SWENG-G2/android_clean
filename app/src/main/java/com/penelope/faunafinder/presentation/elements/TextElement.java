package com.penelope.faunafinder.presentation.elements;


import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.penelope.faunafinder.R;
import com.penelope.faunafinder.xml.slide.Slide;

import lombok.Getter;

@Getter
public class TextElement extends PresentationElement implements ViewElement {
    private String content;
    private int fontSize;
    private int width;
    private int height;
    private long timeOnScreen;
    private int color;
    private String font;

    public TextElement(String font, int fontSize, int color,
                       int x, int y, int width, int height,
                       long timeOnScreen) {
        super(x, y);
        create(font, fontSize, color, width, height, timeOnScreen);
    }

    //Inherited methods
    @Override
    public String getViewType() {
        return TEXT_ELEMENT;
    }

    @Override
    public String getSearchableContent() {
        return content.toLowerCase();
    }

    @Override
    public View applyView(View parent, ViewGroup container, Slide slide, int id) {
        TextView textView = (TextView) parent.findViewById(id);
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
        setTextViewWHParams(slide, mlp);
        setTextViewXYParams(slide, mlp);
        setTextViewFontParams(parent, textView);
        setTextViewTimer(textView);
        return textView;
    }

    //Private methods
    private void setTextViewWHParams(Slide slide, ViewGroup.MarginLayoutParams mlp) {
        if (width > 0) {
            mlp.width = Math.round((width * slide.getCalculatedWidth()) / (float) slide.getWidth());
        } else if (width == MATCH_PARENT) {
            mlp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        } else {
            mlp.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
        }

        if (height > 0) {
            mlp.height = dpToPx(height);
        } else if (height == MATCH_PARENT) {
            mlp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        } else {
            mlp.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        }
    }

    private void setTextViewXYParams(Slide slide, ViewGroup.MarginLayoutParams mlp) {
        int leftMarg = Math.round((x * slide.getCalculatedWidth()) / (float) slide.getWidth());
        int topMarg = dpToPx(y);
        mlp.leftMargin = leftMarg;
        mlp.topMargin = topMarg;
    }

    private void setTextViewFontParams(View parent, TextView textView) {
        textView.setText(content);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        textView.setTextColor(color);
        if (font != null) {
            Typeface type;

            if (font.equals("mono")) {
                type = ResourcesCompat.getFont(parent.getContext(), R.font.chivo_mono_regular);
            } else {
                type = ResourcesCompat.getFont(parent.getContext(), R.font.roboto_condensed_regular);
            }

            textView.setTypeface(type);
        }
    }

    private void setTextViewTimer(TextView textView) {
        textView.setVisibility(View.VISIBLE);
        if (timeOnScreen != -1) {
            textView.postDelayed(() -> textView.setVisibility(View.INVISIBLE), timeOnScreen);
        }
    }

    private void create(String font, int fontSize, int color, int width,
                        int height, long timeOnScreen) {

        this.width = width;
        this.height = height;
        this.fontSize = fontSize;
        this.color = color;
        this.font = font;
        this.timeOnScreen = timeOnScreen;
    }

    //Setters and getters
    public void setContent(String content) {
        this.content = content;
    }
}