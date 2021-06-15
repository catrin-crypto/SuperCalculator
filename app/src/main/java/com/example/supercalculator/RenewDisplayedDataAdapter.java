package com.example.supercalculator;

import android.widget.TextView;

import java.util.Locale;

public class RenewDisplayedDataAdapter implements Runnable{
    private TextView stackView;
    private TextView resultView;
    private InputStackProcessor inputStackProcessor;

    public RenewDisplayedDataAdapter(TextView stackView, TextView resultView, InputStackProcessor inputStackProcessor) {
        this.stackView = stackView;
        this.resultView = resultView;
        this.inputStackProcessor = inputStackProcessor;
    }

    @Override
    public void run() {
        stackView.setText(String.format(Locale.getDefault(), "%s",
                inputStackProcessor.getInputStack()));
        resultView.setText(String.format(Locale.getDefault(), "%s",
                inputStackProcessor.getCurrentResult()));
    }
}
