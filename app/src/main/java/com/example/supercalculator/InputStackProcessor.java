package com.example.supercalculator;

import android.os.Parcel;
import android.os.Parcelable;

public class InputStackProcessor implements Parcelable {
    public final static String OPERATIONS_CHARS = "+-/*";
    private String inputStack;

    public InputStackProcessor() {
         this.inputStack = "";
    }

    protected InputStackProcessor(Parcel in) {

        inputStack = in.readString();
    }

    public static final Creator<InputStackProcessor> CREATOR = new Creator<InputStackProcessor>() {
        @Override
        public InputStackProcessor createFromParcel(Parcel in) {
            return new InputStackProcessor(in);
        }

        @Override
        public InputStackProcessor[] newArray(int size) {
            return new InputStackProcessor[size];
        }
    };

    public void appendInputStack(String valueToAppend){
        inputStack += valueToAppend;
    }

    public void replaceLastInputStackChar(String stringToPutInsteadLastChar){
        inputStack = inputStack.substring(0, inputStack.length() - 1) + stringToPutInsteadLastChar;
    }

    public String getInputStack(){
        return inputStack;
    }

    public void clearInputStack(){
        inputStack = "";
    }

    public void countResult(){
        inputStack = getCurrentResult();
    }

    public String getCurrentResult() {
        double res = 0;
        char curOp = '+';
        String operand = "0";
        for (int i = 0; i < inputStack.length(); i++) {
            char curChar = inputStack.charAt(i);
            if ((curChar >= '0' && curChar <= '9') ||
                    curChar == ',' || curChar == '.') {
                operand += curChar;
            } else if (OPERATIONS_CHARS.indexOf(curChar) > -1) {
                res = execOperation(res, curOp, operand);
                curOp = curChar;
                operand = "0";
            }
        }
        return niceDoubleFormat(res);

    }

    public static String niceDoubleFormat(double d) {
        if (d == Math.floor(d))
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
        for (int i = inputStack.length() - 1; i >= 0; i--) {
            if (OPERATIONS_CHARS.indexOf(inputStack.charAt(i)) != -1) {
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
