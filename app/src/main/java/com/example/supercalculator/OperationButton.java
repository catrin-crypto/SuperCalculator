package com.example.supercalculator;

import android.content.Context;
import android.util.AttributeSet;

public class OperationButton extends CalculatorButton {
    public OperationButton(Context context) {
        super(context);
    }

    public OperationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OperationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void modifyInputStackProcessor(InputStackProcessor inputStackProcessor) {
        String inputStack = inputStackProcessor.getInputStack();
        if ((inputStack.length() == 1 && inputStack.charAt(0) == '-')) {
            inputStackProcessor.clearInputStack();
        } else if (inputStack.length() > 0 &&
            InputStackProcessor.OPERATIONS_CHARS.indexOf(inputStack.charAt(inputStack.length() - 1)) > -1) {
            inputStackProcessor.replaceLastInputStackChar((String)this.getText());
        } else if (inputStack.length() > 0) {
            inputStackProcessor.appendInputStack((String)this.getText());
        } else if (this.getText().equals("-")) {
            inputStackProcessor.clearInputStack();
            inputStackProcessor.appendInputStack("-");
        }
    }
}
