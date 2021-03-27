package com.demomvvm.customView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatButton;

import com.demomvvm.R;

public class CustomButton extends AppCompatButton {

    public CustomButton(Context context) {
        super(context);
        createButton(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs, R.style.Widget_AppCompat_Button_Borderless);
        createButton(context);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createButton(context);
    }

    private void createButton(Context context) {
        if(!isInEditMode()) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Avenir-Bold.ttf");
            setTypeface(typeface);

            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        }
    }
}
