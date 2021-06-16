package com.example.supercalculator;

import android.content.Context;
import android.util.AttributeSet;

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

    @Override
    public void modifyInputStackProcessor(InputStackProcessor inputStackProcessor) {
        if (this.getText().equals("del")) {
            String inputStack = inputStackProcessor.getInputStack();
            if (inputStack.length() > 0) {
                inputStackProcessor.replaceLastInputStackChar("");
            }
        } else if (this.getText().equals("C")) {
            inputStackProcessor.clearInputStack();
        } else if (this.getText().equals("=")) {
            String inputStack = inputStackProcessor.getInputStack();
            if (inputStack.length() > 0 &&
                    InputStackProcessor.OPERATIONS_CHARS.indexOf(inputStack.charAt(inputStack.length() - 1)) > -1) {
                inputStackProcessor.replaceLastInputStackChar("+");
            }else { inputStackProcessor.appendInputStack("+"); }
            inputStackProcessor.countResult();
        }
    }
}
