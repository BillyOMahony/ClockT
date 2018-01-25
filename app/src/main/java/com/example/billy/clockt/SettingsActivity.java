package com.example.billy.clockt;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    Spinner spinnerBackground = null;
    Spinner spinnerClock = null;
    Spinner spinnerSecond = null;
    Spinner spinnerMinute = null;
    Spinner spinnerHour = null;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Get Sharedpreferences
        preferences = getApplicationContext().getSharedPreferences("Prefs", 0);
        final SharedPreferences.Editor editor = preferences.edit();

        spinnerBackground = (Spinner) findViewById(R.id.spinnerBG);
        spinnerClock = (Spinner) findViewById(R.id.spinnerClock);
        spinnerSecond = (Spinner) findViewById(R.id.spinnerSecond);
        spinnerMinute = (Spinner) findViewById(R.id.spinnerMinute);
        spinnerHour = (Spinner) findViewById(R.id.spinnerHour);

        String[] colourStrings = {"Black", "White", "Red", "Green", "Blue", "Grey"};
        // This is an array of colour ids. colours[0] can be used instead of Color.Black for example.
        final int[] colours = {Color.BLACK, Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.GRAY};

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, colourStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBackground.setAdapter(adapter);
        spinnerClock.setAdapter(adapter);
        spinnerHour.setAdapter(adapter);
        spinnerMinute.setAdapter(adapter);
        spinnerSecond.setAdapter(adapter);

        int colorBG;
        int colorClock;
        int colorSec;
        int colorMin;
        int colorHour;

        int indexBG = 0;
        int indexClock = 0;
        int indexSec = 0;
        int indexMin = 0;
        int indexHour = 0;

        if(preferences.contains("BGColor")){
            colorBG = preferences.getInt("BGColor", 0);
            indexBG = getIndex(colorBG, colours);
            Log.i("Selection", "" + colorBG + ": Index of " + indexBG);
        }

        if(preferences.contains("ClockColor")){
            colorClock = preferences.getInt("ClockColor", 0);
            indexClock = getIndex(colorClock, colours);
            Log.i("Selection", "" + colorClock + ": Index of " + indexClock);
        }

        if(preferences.contains("SecondColor")){
            colorSec = preferences.getInt("SecondColor", 0);
            indexSec = getIndex(colorSec, colours);
            Log.i("Selection", "" + colorSec + ": Index of " + indexSec);
        }

        if(preferences.contains("MinuteColor")){
            colorMin = preferences.getInt("SecondColor", 0);
            indexMin = getIndex(colorMin, colours);
            Log.i("Selection", "" + colorMin + ": Index of " + indexMin);
        }

        if(preferences.contains("HourColor")){
            colorHour = preferences.getInt("SecondColor", 0);
            indexHour = getIndex(colorHour, colours);
            Log.i("Selection", "" + colorHour + ": Index of " + indexHour);
        }


        spinnerBackground.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("BGColor", colours[i]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerClock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Putting Int", "" + colours[i]);
                editor.putInt("ClockColor", colours[i]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("HourColor", colours[i]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerMinute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("MinuteColor", colours[i]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSecond.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editor.putInt("SecondColor", colours[i]);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Set Spinner to value from prefs.
        spinnerBackground.setSelection(indexBG, false);
        spinnerClock.setSelection(indexClock, false);
        spinnerSecond.setSelection(indexSec, false);
        spinnerSecond.setSelection(indexMin, false);
        spinnerSecond.setSelection(indexHour, false);

    }

    private int getIndex(int color, int[] colorArray){
        for (int i = 0; i < colorArray.length; i++){
            Log.i("Checking: ", color + " against " + colorArray[i]);
            if(color == colorArray[i]){
                return i;
            }
        }

        return -1;
    }
}
