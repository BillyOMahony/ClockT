package com.example.billy.clockt;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import java.util.Calendar;

/**
 * Created by Billy on 29/11/2017.
 */

public class ClockView extends SurfaceView implements Runnable{

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Calendar calendar;

    private float length;
    private Thread thread = null;
    private SurfaceHolder surfaceHolder;
    private boolean running = false;
    Paint bgPaint;
    Paint secPaint;
    Paint minPaint;
    Paint hourPaint;
    Paint clockPaint;
    Context c;

    //The constructor can take extra inputs, however super(context) is required
    public ClockView(Context c) {
        super(c);
        this.c = c;
        surfaceHolder = getHolder();

        preferences = c.getSharedPreferences("Prefs", 0);

        bgPaint = new Paint();
        secPaint = new Paint();
        minPaint = new Paint();
        hourPaint = new Paint();
        clockPaint = new Paint();
    }

    public void onResumeClockView(){
        running = true;

        //getHolder().addCallback(this);
        thread = new Thread(this);
        thread.start();
    }

    public void onPauseClockView(){
        boolean retry = true;
        running = false;
        while(retry){
            try{
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(){
        while(running){
            if(surfaceHolder.getSurface().isValid()){

                try{
                    thread.sleep(1000);

                    Canvas canvas = surfaceHolder.lockCanvas();

                    setPaint();

                    drawBG(canvas);

                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void drawBG(Canvas canvas){
        canvas.drawPaint(bgPaint);

        length = (getWidth()/2) -20;

        RegPoly secMarks = new RegPoly(60, length, getWidth()/2, getHeight()/2, canvas, clockPaint);
        RegPoly hourMarks = new RegPoly(12, length - 50, getWidth()/2,getHeight()/2, canvas, clockPaint);

        secMarks.drawPoints();
        hourMarks.drawNumbers();

        RegPoly secHand = new RegPoly(60, length-90, getWidth()/2, getHeight()/2, canvas, secPaint);
        RegPoly minHand = new RegPoly(60, length -130, getWidth()/2, getHeight()/2, canvas, minPaint);
        RegPoly hourHand = new RegPoly(60, length -170, getWidth()/2, getHeight()/2, canvas, hourPaint);

        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        Log.i("Time", "" + hour + ":" + minute + ":" + second);

        //Deal with hours

        //Make sure hour is in 12 hours, not 24
        if(hour > 12)hour -= 12;
        else if(hour == 0)hour = 12;

        //Convert hour to seconds (move precise position)
        hour = (hour * 3600) + (minute * 60) + second;

        //Divide hours by seconds in 12 hours to get lerp percentage
        float alpha = hour / 43200f;

        float degree =lerp(0.0f, 360.0f, alpha);
        degree = 180-degree;

        hourHand.drawRadius(degree);

        //Deal with minutes

        minute = (minute * 60) + second;

        alpha = minute / 3600f;

        degree = lerp(0, 360, alpha);
        degree = 180-degree;

        Log.i("Minute", "" + degree);

        minHand.drawRadius(degree);

        //Deal with seconds

        alpha = second / 60f;

        degree = (lerp(0, 360, alpha));
        degree = 180-degree;

        Log.i("Second", "" + degree);

        secHand.drawRadius(degree);

        //Draws a circle in the center; looks better
        secHand.drawCircle();

    }

    void setPaint(){

        //final SharedPreferences.Editor editor = preferences.edit();

        //Try get preferences, else use default
        int bgColor = Color.BLACK;
        if(preferences.contains("BGColor")) {
            bgColor = preferences.getInt("BGColor", 0);
        }

        int clockColor = Color.WHITE;
        if(preferences.contains("ClockColor")){
            clockColor = preferences.getInt("ClockColor", 0);
        }

        int secondColor = Color.WHITE;
        if(preferences.contains("SecondColor")){
            secondColor = preferences.getInt("SecondColor", 0);
        }

        int minuteColor = Color.WHITE;
        if(preferences.contains("MinuteColor")){
            minuteColor = preferences.getInt("MinuteColor", 0);
        }

        int hourColor = Color.WHITE;
        if(preferences.contains("HourColor")){
            hourColor = preferences.getInt("HourColor", 0);
        }

        bgPaint.setColor(bgColor);

        clockPaint.setColor(clockColor);
        clockPaint.setTextSize(40);
        clockPaint.setStrokeWidth(10);
        clockPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        secPaint.setColor(secondColor);
        secPaint.setStrokeWidth(5);
        minPaint.setColor(minuteColor);
        minPaint.setStrokeWidth(5);
        hourPaint.setColor(hourColor);
        hourPaint.setStrokeWidth(5);
    }

    public float lerp(float point1, float point2, float alpha) {
        return point1 + alpha * (point2 - point1);
    }

}

