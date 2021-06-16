package com.example.supercalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

public class SettingsActivity extends AppCompatActivity {
    private static final String NameSharedPreference = "PREF_NAME";
    private static final String AppTheme = "APP_THEME";
    private static final int DayStyle = 0;
    private static final int NightStyle = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initThemeChooser();
        initReturnButton();
    }

    private void initReturnButton() {
        Button btnReturn = findViewById(R.id.return_button);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.radioButtonLight),
                DayStyle);
        initRadioButton(findViewById(R.id.radioButtonNight),
                NightStyle);

        RadioGroup rg = findViewById(R.id.radioButtons);
        RadioButton button2check = (RadioButton) rg.getChildAt(getCodeStyle(DayStyle));
        button2check.setChecked(true);
    }

    private void initRadioButton(View button, final int codeStyle) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppTheme(codeStyle);
                int nightMode = getAppThemeMode(getAppThemeMode(DayStyle));
                if (AppCompatDelegate.getDefaultNightMode() != nightMode) {
                    AppCompatDelegate.setDefaultNightMode(nightMode);
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
}