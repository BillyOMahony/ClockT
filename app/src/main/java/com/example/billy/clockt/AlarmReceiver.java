package com.example.billy.clockt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Billy on 01/12/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Alarm Worked", Toast.LENGTH_LONG);

        Log.i("ALARM! ALARM!", "IT'S WORKING");

        Intent alarmIntent = new Intent(context, AlarmNoiseActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);

    }
}
