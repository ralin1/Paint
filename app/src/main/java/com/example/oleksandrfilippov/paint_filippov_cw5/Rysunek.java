package com.example.oleksandrfilippov.paint_filippov_cw5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by oleksandrfilippov on 11.05.2018.
 */

public class Rysunek extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Bitmap Bitmapa = null;
    private Canvas Kanwa = null;

    // monitorowanie powierzchnię
    private SurfaceHolder Pojemnik;
    private Thread WatekRysujacy;
    private boolean WatekPracuje = false;
    // obiekt do tworzenia sekcji krytycznych
    private Object Blokada=new Object();

    public static Paint farba;
    public static boolean czysc = false;
    static float startX=-1;
    static float startY=-1;
    static float endY=-1;
    static float endX=-1;

    public Rysunek(Context context, AttributeSet attrs) {
        super(context, attrs);
        Pojemnik = getHolder();//  monitorowanie powierzchnie
        Pojemnik.addCallback(this);
        farba = new Paint();

        farba.setColor(Color.BLACK);//startowy kolor
        farba.setStrokeWidth(5);// ustawienie szerokości linii
    }
    public void wznowRysowanie() {
        WatekRysujacy = new Thread(this);
        WatekPracuje = true;
        WatekRysujacy.start();
    }

    public static void czysc(){
        czysc=true;
    }
    public void pauzaRysowania() {
        WatekPracuje = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        //moment krytyczny - unikalny dostęp
        synchronized (Blokada) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //pauzaRysowania();
                    wznowRysowanie();

                    startX=event.getX();
                    startY=event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //ruch po ekranie
                    //pauzaRysowania();
                    //wznowRysowanie();

                    endX=event.getX();
                    endY=event.getY();

                    Kanwa.drawLine(startX, startY, endX, endY, farba);
                    startX=endX;
                    startY=endY;
                    break;
                case MotionEvent.ACTION_UP:

                    pauzaRysowania();
                    //wznowRysowanie();

                    break;
            }
        }
        return true;
    }
    public boolean performClick()
    {
        return super.performClick();
    }
    @Override
    public void run() {
        while (WatekPracuje) {
            Canvas kanwa = null;
            try {
                synchronized (Pojemnik) {
                    if (!Pojemnik.getSurface().isValid()) continue;
                    kanwa = Pojemnik.lockCanvas(null);
                    synchronized (Blokada) {
                        if (WatekPracuje) {
                            if(czysc){
                                Kanwa.drawARGB(255, 255, 255, 255);
                                czysc = false;
                            }
                        }
                        if(kanwa!=null) {
                            kanwa.drawBitmap(Bitmapa, 0, 0, null);
                        }
                    }
                }
            } finally {
                if (kanwa != null) {
                    Pojemnik.unlockCanvasAndPost(kanwa);
                }
            }
            try {
                Thread.sleep(1000 / 25);
            } catch (InterruptedException e) {
            }
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        czysc();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

        Bitmapa = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Kanwa = new Canvas(Bitmapa);
        Kanwa.drawARGB(255, 255, 255, 255);

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        WatekPracuje = false;
    }
}
