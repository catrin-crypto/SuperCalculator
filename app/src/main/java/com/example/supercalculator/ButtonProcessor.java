package com.example.supercalculator;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class ButtonProcessor implements Parcelable {
    private TextView stackView;
    private TextView resultView;
    private String inputStack;
    private final static String operationsChars = "+-/*";

    public ButtonProcessor(TextView stackView, TextView resultView) {
        this.stackView = stackView;
        this.resultView = resultView;
        this.inputStack = "";
    }

    protected ButtonProcessor(Parcel in) {
        inputStack = in.readString();
    }

    public static final Creator<ButtonProcessor> CREATOR = new Creator<ButtonProcessor>() {
        @Override
        public ButtonProcessor createFromParcel(Parcel in) {
            return new ButtonProcessor(in);
        }

        @Override
        public ButtonProcessor[] newArray(int size) {
            return new ButtonProcessor[size];
        }
    };

    public void processButton(Button button) {
        if (button instanceof CalculatorButton && !(button instanceof ActionButton)) {
            if (button instanceof DigitButton) {
                inputStack = inputStack + button.getText();
            } else if (button instanceof CommaButton) {
                String lastNumber = processLastNumber();
                if (lastNumber.indexOf(',') != -1) {// if we already have comma in last number
                    return;
                } else if (lastNumber.length() > 0) {
                    inputStack = inputStack + ',';
                } else {
                    inputStack = inputStack + "0,";
                }
            } else if (button instanceof OperationButton) {
                if ((inputStack.length() == 1 && inputStack.charAt(0) == '-')) {
                    inputStack = "";
                } else if (inputStack.length() > 0 &&
                        operationsChars.indexOf(inputStack.charAt(inputStack.length() - 1)) > -1) {
                    inputStack = inputStack.substring(0, inputStack.length() - 1) + button.getText();
                } else if (inputStack.length() > 0) {
                    inputStack += button.getText();
                } else if (button.getText().equals("-")) {
                    inputStack = "-";
                }
            }

        } else if (button instanceof ActionButton) {
            if (button.getText().equals("del")) {
                if (inputStack.length() > 0) {
                    inputStack = inputStack.substring(0, inputStack.length() - 1);
                }
            } else if (button.getText().equals("C")) {
                inputStack = "";
            } else if (button.getText().equals("=")) {
                inputStack += '+';
                inputStack = getCurrentResult();
            }
        }
        renewDisplayedData();
    }

    public void renewDisplayedData() {
        stackView.setText(String.format(Locale.getDefault(), "%s",
                inputStack));
        resultView.setText(String.format(Locale.getDefault(), "%s",
                getCurrentResult()));
    }

    public String getCurrentResult() {
        double res = 0;
        char curOp = '+';
        String operand = "0";
        for (int i = 0; i < inputStack.length(); i++) {
            char curChar = inputStack.charAt(i);
            if ((curChar >= '0' && curChar <= '9') ||
                    curChar == ',') {
                operand += curChar;
            } else if (operationsChars.indexOf(curChar) > -1) {
                res = execOperation(res, curOp, operand);
                curOp = curChar;
                operand = "0";
            }
        }
        return niceDoubleFormat(res);

    }

    public static String niceDoubleFormat(double d) {
        if (d == (long) d)
            return String.format("%d", (long) d);
        else
            return String.format("%s", d);
    }

    public double execOperation(double operand1, char operator, String textOperand2) {
        double operand2 = Double.parseDouble(textOperand2.replace(",", "."));
        switch (operator) {
            case '+':
                operand1 += operand2;
                break;
            case '-':
                operand1 -= operand2;
                break;
            case '/':
                operand1 /= operand2;
                break;
            case '*':
                operand1 *= operand2;
                break;
        }
        return operand1;
    }

    public String processLastNumber() {
        String lastNumber = "";
        for (int i = inputStack.length() - 1; i > 0; i--) {
            if (operationsChars.indexOf(inputStack.charAt(i)) != -1) {
                return lastNumber;
            } else lastNumber += inputStack.charAt(i);
        }
        return lastNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(inputStack);

    }
}
