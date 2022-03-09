package ru.samsung.itschool.mdev.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class MyThread extends Thread {

    private Paint paint;
    // "держатель" SurfaceView
    private SurfaceHolder surfaceHolder;
    public boolean flag;

    MyThread(SurfaceHolder holder) {
        this.flag = false;
        this.surfaceHolder = holder;
        paint = new Paint();
        paint.setAntiAlias(true); // сглаживание
        paint.setStyle(Paint.Style.FILL); // заливка
    }

    public long getTime() {
        return System.nanoTime()/1000;
    }

    private long redrawTime; // 0

    @Override
    public void run() {
        Canvas canvas;
        while(flag) {
            long currentTime = getTime();
            long elapsedTime = currentTime - redrawTime;
            if(elapsedTime < 500000) {
                continue;
            }
            // блокировка Canvas
            canvas = surfaceHolder.lockCanvas();
            // логика отрисовки
            drawCircle(canvas);
            // показать отрисованное
            surfaceHolder.unlockCanvasAndPost(canvas);
            // обновление времени
            redrawTime = getTime();
        }
    }

    public void drawCircle(Canvas canvas) {
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        float maxRadius = (float)Math.min(height, width)/2;
        // Фон
        canvas.drawColor(Color.BLACK);
        int centerX = width/2;
        int centerY = height/2;
        paint.setColor(Color.RED);
        float koeff = (float)Math.random();
        canvas.drawCircle(centerX,centerY,maxRadius*koeff,paint);
    }
}
