package com.example.billy.clockt;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.net.Uri;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class AlarmNoiseActivity extends AppCompatActivity {

    Uri alert;
    Button button;
    Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_noise);

        alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(getApplicationContext(), alert);

        r.play();

        button = (Button) findViewById(R.id.acknowledgeButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r.stop();

                Intent intent = new Intent(getApplicationContext(), ClockActivity.class);
                startActivity(intent);

            }
        });

    }
}
