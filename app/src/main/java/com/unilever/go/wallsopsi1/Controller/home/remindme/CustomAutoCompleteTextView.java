package com.unilever.go.wallsopsi1.Controller.home.remindme;

import android.content.Context;
import android.util.AttributeSet;

public class CustomAutoCompleteTextView extends android.support.v7.widget.AppCompatMultiAutoCompleteTextView {
    public CustomAutoCompleteTextView(Context context) {
        super(context);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void replaceText(CharSequence text) {
        super.replaceText(text);
    }
}
