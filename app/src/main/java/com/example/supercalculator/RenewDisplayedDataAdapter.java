package com.example.supercalculator;

import android.widget.TextView;

import java.util.Locale;

public class RenewDisplayedDataAdapter implements Runnable{
    private TextView stackView;
    private TextView resultView;
    private ButtonProcessor buttonProcessor;

    public RenewDisplayedDataAdapter(TextView stackView, TextView resultView, ButtonProcessor buttonProcessor) {
        this.stackView = stackView;
        this.resultView = resultView;
        this.buttonProcessor = buttonProcessor;
    }

    @Override
    public void run() {
        stackView.setText(String.format(Locale.getDefault(), "%s",
                buttonProcessor.getInputStack()));
        resultView.setText(String.format(Locale.getDefault(), "%s",
                buttonProcessor.getCurrentResult()));
    }
}
