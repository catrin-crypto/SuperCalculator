package com.example.supercalculator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActionButton extends CalculatorButton{
    public ActionButton(@NonNull Context context) {
        super(context);
    }

    public ActionButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ActionButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
