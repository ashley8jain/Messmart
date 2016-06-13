package com.ashleyjain.messmart.function;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ashleyjain.messmart.StartActivity;

/**
 * Created by ashleyjain on 13/06/16.
 */
public class CustomText extends TextView {
    public CustomText(Context context) {
        super(context);
        this.setTypeface(StartActivity.font);
    }

    public CustomText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(StartActivity.font);
    }

    public CustomText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTypeface(StartActivity.font);

    }

}
