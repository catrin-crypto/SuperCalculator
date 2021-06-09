package com.example.supercalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final String STATE_BUTTON_PROCESSOR = "butProcState1";
    private TextView inputStackView;
    private TextView resultView;

    private ButtonProcessor buttonProcessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputStackView = findViewById(R.id.operationField);
        resultView = findViewById(R.id.result);
        buttonProcessor = new ButtonProcessor();

        ViewGroup group = (ViewGroup) findViewById(R.id.include);
        View v;
        for (int i = 0; i < group.getChildCount(); i++) {
            v = group.getChildAt(i);
            if (v instanceof LinearLayout) {
                ViewGroup subGroup = ((LinearLayout) v);
                View subView;
                for (int j = 0; j < subGroup.getChildCount(); j++) {
                    subView = subGroup.getChildAt(j);
                    if (subView instanceof CalculatorButton) {
                        ((CalculatorButton) subView).setRenewDisplayedDataAdapter(
                                new RenewDisplayedDataAdapter(inputStackView, resultView,buttonProcessor));
                        ((CalculatorButton) subView).setButtonProcessor(buttonProcessor);

                    }
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable(STATE_BUTTON_PROCESSOR,buttonProcessor);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        buttonProcessor = savedInstanceState.getParcelable(STATE_BUTTON_PROCESSOR);
    }
}