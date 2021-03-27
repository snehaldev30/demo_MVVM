package com.demomvvm.customView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.demomvvm.R;

public class CustomEditText extends AppCompatEditText {
    public CustomEditText(Context context) {
        super(context);
        createButton(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        createButton(context);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createButton(context);
    }

    private void createButton(Context context) {
        if(!isInEditMode()) {
            Typeface typeface;
            switch (getTypeface().getStyle()) {
                case Typeface.NORMAL:
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-Regular.ttf");
                    break;

                case Typeface.BOLD:
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-Bold.ttf");
                    break;

                case Typeface.BOLD_ITALIC:
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-BoldItalic.ttf");
                    break;

                case Typeface.ITALIC:
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-Italic.ttf");
                    break;

                default:
                    typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-Regular.ttf");
                    break;
            }

            setTextColor(context.getResources().getColor(R.color.black));
            setTextSize(16);
            setTypeface(typeface);
        }
    }
}

