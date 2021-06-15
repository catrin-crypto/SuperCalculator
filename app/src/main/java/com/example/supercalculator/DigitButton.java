package com.example.supercalculator;

import android.content.Context;
import android.util.AttributeSet;

public class DigitButton extends CalculatorButton {
    public DigitButton(Context context) {
        super(context);
    }

    public DigitButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DigitButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void modifyInputStackProcessor(InputStackProcessor inputStackProcessor) {
        inputStackProcessor.appendInputStack((String) this.getText());
    }
}
