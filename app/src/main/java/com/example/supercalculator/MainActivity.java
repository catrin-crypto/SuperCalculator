package com.example.supercalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private InputStackProcessor inputStackProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTheme();
        initSettingsButton();

        inputStackView = findViewById(R.id.operationField);
        resultView = findViewById(R.id.result);

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        if (inputStackProcessor == null) {
            inputStackProcessor = new InputStackProcessor();
        }

        initCalculatorMechanics();
    }

    private void initCalculatorMechanics() {
        ViewGroup group = (ViewGroup) findViewById(R.id.include);
        View v;
        RenewDisplayedDataAdapter dispAdapter = new RenewDisplayedDataAdapter(inputStackView,
                resultView, inputStackProcessor);
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
                        ((CalculatorButton) subView).setButtonProcessor(inputStackProcessor);
                    }
                }
            }
        }
        dispAdapter.run();
    }

    private void initTheme() {
        int mode = getAppThemeMode(MODE_NIGHT_NO);
        AppCompatDelegate.setDefaultNightMode(mode);
        ConstraintLayout view = findViewById(R.id.constraint_layout);
        if (mode != MODE_NIGHT_NO) {
            view.setBackgroundResource(R.drawable.thunder);
        } else {
            view.setBackgroundResource(R.drawable.multicolor);
        }
    }

    private void initSettingsButton() {
        Button btnSettings = findViewById(R.id.settings_button);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent runSettings = new Intent(MainActivity.this, SettingsActivity.class);
                ActivityInfo activityInfo =
                        runSettings.resolveActivityInfo(getPackageManager(),
                                runSettings.getFlags());
                if (activityInfo != null) {
                    startActivity(runSettings);
                }

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
        outState.putParcelable(STATE_BUTTON_PROCESSOR, inputStackProcessor);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        inputStackProcessor = savedInstanceState.getParcelable(STATE_BUTTON_PROCESSOR);
    }
}