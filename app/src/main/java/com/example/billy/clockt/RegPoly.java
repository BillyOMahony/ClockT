package com.example.billy.clockt;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Billy on 29/11/2017.
 */

public class RegPoly {

    private float x0, y0, r;
    private int n;
    private float x[], y[];

    private Canvas c = null;
    private Paint p = null;

    public RegPoly(int n, float r, float x0, float y0, Canvas canvas, Paint paint){
        this.n = n;
        this.r= r;
        this.x0 = x0;
        this.y0 = y0;
        this.c = canvas;
        this.p = paint;

        x = new float[n];
        y = new float[n];

        for(int i = 0; i < n; i++){
            x[i] = (float)(x0 + r * Math.cos(2 * Math.PI * i/n));
            y[i] = (float)(y0 + r * Math.sin(2 * Math.PI * i/n));
        }

    }

    public float getX(int i){
        return x[i%n];
    }

    public float getY(int i){
        return y[i%n];
    }

    public void drawRegPoly(){
        for(int i = 0; i < n; i++){
            c.drawLine(x[i], y[i], x[(i+1)%n], y[(i+1)%n], p);

        }
    }

    public void drawCircle(){
        c.drawCircle(x0, y0, 10, p);
    }

    public void drawPoints(){
        for(int i = 0; i < n; i++){
            c.drawCircle(x[i], y[i], 4, p);
        }
    }

    public void drawNumbers(){

        int num = 2;
        for(int i = 0; i < n; i++){

            num += 1;
            if(num > 12){
                num = 1;
            }

            //Need offset to center text
            float offset = p.getTextSize()/2;

            String text = num + "";
            c.drawText(text, x[i] - offset, y[i] + offset, p);
        }
    }

    public void drawRadius(float i){
        i = (float) (i * Math.PI/180);

        float endx = (float) (x0 + r * Math.sin(i));

        float endy = (float) (y0 + r * Math.cos(i));

        c.drawLine(x0, y0, endx, endy, p);
    }

}
