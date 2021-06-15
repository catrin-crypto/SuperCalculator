package com.example.supercalculator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class CalculatorButton extends MaterialButton {
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

    public void setMyClickListener(TextView textInput) {
        setOnClickListener(v -> {
            if (v instanceof Button) {
                textInput.setText(String.format(Locale.getDefault(), "%s",
                        textInput.getText().toString() + ((Button) v).getText()));
            }
        });
    }
    public void setRenewDisplayedDataAdapter(RenewDisplayedDataAdapter renewDisplayedDataAdapter){
        this.renewDisplayedDataAdapter = renewDisplayedDataAdapter;
    }
    public void setButtonProcessor(ButtonProcessor buttonProcessor) {
        setOnClickListener(v -> {
            if (v instanceof Button) {
                buttonProcessor.processButton((Button) v);
                renewDisplayedDataAdapter.run();
            }
        });
    }
}
