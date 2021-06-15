package com.example.supercalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.radiobutton.MaterialRadioButton;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

public class MainActivity extends AppCompatActivity {
    private static final String NameSharedPreference = "PREF_NAME";
    private static final String AppTheme = "APP_THEME";
    private static final int DayStyle = 0;
    private static final int NightStyle = 1;
    static final String STATE_BUTTON_PROCESSOR = "butProcState1";
    private TextView inputStackView;
    private TextView resultView;
    private ButtonProcessor buttonProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(getAppThemeMode(DayStyle));
        setContentView(R.layout.activity_main);
        initThemeChooser();
        inputStackView = findViewById(R.id.operationField);
        resultView = findViewById(R.id.result);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        if (buttonProcessor == null) {
            buttonProcessor = new ButtonProcessor();
        }

        ViewGroup group = (ViewGroup) findViewById(R.id.include);
        View v;
        RenewDisplayedDataAdapter dispAdapter = new RenewDisplayedDataAdapter(inputStackView, resultView, buttonProcessor);
        for (int i = 0; i < group.getChildCount(); i++) {
            v = group.getChildAt(i);
            if (v instanceof LinearLayout) {
                ViewGroup subGroup = ((LinearLayout) v);
                View subView;
                for (int j = 0; j < subGroup.getChildCount(); j++) {
                    subView = subGroup.getChildAt(j);
                    if (subView instanceof CalculatorButton) {
                        ((CalculatorButton) subView).setRenewDisplayedDataAdapter(
                                dispAdapter);
                        ((CalculatorButton) subView).setButtonProcessor(buttonProcessor);
                    }
                }
            }
        }
        dispAdapter.run();
    }

    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.radioButtonLight),
                DayStyle);
        initRadioButton(findViewById(R.id.radioButtonNight),
                NightStyle);

        RadioGroup rg = findViewById(R.id.radioButtons);
        RadioButton button2check = (RadioButton) rg.getChildAt(getCodeStyle(DayStyle));
        button2check.setChecked(true);
        ConstraintLayout view = findViewById(R.id.constraint_layout);
        if (button2check.getId() == R.id.radioButtonNight) {
            view.setBackgroundResource(R.drawable.thunder);
        } else {
            view.setBackgroundResource(R.drawable.multicolor);
        }

    }

    private void initRadioButton(View button, final int codeStyle) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppTheme(codeStyle);
                recreate();
            }
        });
    }

    private int getAppThemeMode(int codeStyle) {
        return codeStyleToNightMode(getCodeStyle(codeStyle));
    }

    private int getCodeStyle(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharedPref.getInt(AppTheme, codeStyle);
    }

    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(AppTheme, codeStyle);
        editor.apply();
    }

    private int codeStyleToNightMode(int codeStyle) {
        switch (codeStyle) {
            case DayStyle:
                return MODE_NIGHT_NO;
            case NightStyle:
                return MODE_NIGHT_YES;
        }
        return MODE_NIGHT_NO;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(STATE_BUTTON_PROCESSOR, buttonProcessor);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        buttonProcessor = savedInstanceState.getParcelable(STATE_BUTTON_PROCESSOR);
    }
}