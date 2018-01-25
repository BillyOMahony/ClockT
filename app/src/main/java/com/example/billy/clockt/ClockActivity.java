package com.example.billy.clockt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class ClockActivity extends AppCompatActivity {

    ClockView cv = null;
    FrameLayout view = null;

    Button settingsButton;
    Button alarmsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        view = (FrameLayout) findViewById(R.id.frameLayout);

        cv = new ClockView(this);

        view.addView(cv);

        settingsButton = (Button) findViewById(R.id.settingsButton);
        alarmsButton = (Button) findViewById(R.id.alarmsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClockActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        alarmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClockActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        cv.onResumeClockView();
    }

    @Override
    protected void onPause(){
        super.onPause();
        cv.onPauseClockView();
    }

}
