package com.example.billy.clockt;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    AlarmManager alarmManager = null;

    Button dateButton = null;
    Button timeButton = null;
    Button confirmButton = null;

    Calendar calendar;

    DatePicker datePicker = null;
    TimePicker timePicker = null;


    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        calendar = Calendar.getInstance();

        dateButton = (Button) findViewById(R.id.dateButton);
        timeButton = (Button) findViewById(R.id.timeButton);
        confirmButton = (Button) findViewById(R.id.confirmButton);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        //Hide the date and time pickers
        datePicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.GONE);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.GONE);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.setVisibility(View.GONE);
                timePicker.setVisibility(View.VISIBLE);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setAlarm();

                Toast toast = Toast.makeText(getApplicationContext(), "Alarm set for " + calendar.get(Calendar.YEAR)
                        + ":" + calendar.get(Calendar.MONTH) + 1 + ":" + calendar.get(Calendar.DAY_OF_MONTH) + " at " +
                        calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE), Toast.LENGTH_LONG);
                toast.show();
            }
        });

        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year, month, day);

                Log.i("Date", calendar.get(Calendar.YEAR) + ":" + calendar.get(Calendar.MONTH) + 1 + ":" +
                        calendar.get(Calendar.DAY_OF_MONTH));

                dateButton.setText(calendar.get(Calendar.YEAR) + ":" + calendar.get(Calendar.MONTH) + ":" + calendar.get(Calendar.DAY_OF_MONTH));
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                Log.i("Time", calendar.get(Calendar.DAY_OF_MONTH) + ": " + calendar.get(Calendar.HOUR_OF_DAY) +
                        ":" + calendar.get(Calendar.MINUTE));

                timeButton.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
            }
        });

    }

    protected void setAlarm(){

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(AlarmActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.i("Will Trigger at", "" + calendar.getTimeInMillis());

        calendar = Calendar.getInstance();
        Log.i("Current Millis", calendar.getTimeInMillis() + "");
    }
}
