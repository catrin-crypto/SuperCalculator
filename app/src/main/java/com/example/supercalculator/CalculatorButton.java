package com.example.supercalculator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

public abstract class CalculatorButton extends MaterialButton {
    private RenewDisplayedDataAdapter renewDisplayedDataAdapter;
    public CalculatorButton(Context context) {
        super(context);
    }

    public CalculatorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalculatorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRenewDisplayedDataAdapter(RenewDisplayedDataAdapter renewDisplayedDataAdapter){
        this.renewDisplayedDataAdapter = renewDisplayedDataAdapter;
    }

    public abstract void modifyInputStackProcessor(InputStackProcessor inputStackProcessor);

    public void setButtonProcessor(InputStackProcessor inputStackProcessor) {
        setOnClickListener(v -> {
            if (v instanceof CalculatorButton) {
                modifyInputStackProcessor(inputStackProcessor);
                renewDisplayedDataAdapter.run();
            }
        });
    }
}
