package com.example.supercalculator;

import android.content.Context;
import android.util.AttributeSet;

public class CommaButton extends CalculatorButton {
    public CommaButton(Context context) {
        super(context);
    }

    public CommaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void modifyInputStackProcessor(InputStackProcessor inputStackProcessor) {
        String lastNumber = inputStackProcessor.processLastNumber();
        if (lastNumber.indexOf(',') != -1 || lastNumber.indexOf('.') != -1) {// if we already have comma in last number
            return;
        } else if (lastNumber.length() > 0) {
            inputStackProcessor.appendInputStack(",");
        } else {
            inputStackProcessor.appendInputStack( "0,");
        }
    }
}
