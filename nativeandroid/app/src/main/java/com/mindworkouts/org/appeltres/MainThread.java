package com.mindworkouts.org.appeltres;


import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.mindworkouts.org.appeltres.ActivityPlaying;
import com.mindworkouts.org.appeltres.Constants;


public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private Render render;
    private boolean running;
    public static Canvas canvas;
    private ActivityPlaying iga;

    public MainThread(SurfaceHolder surfaceHolder, Render render, ActivityPlaying iga){
        super();
        this.surfaceHolder = surfaceHolder;
        this.render = render;
        this.iga = iga;
    }

    public void setRunning(boolean running){
        this.running = running;
    }
   public void run(){
        long startTime;
        long timeMilliSeconds = 1000 / Constants.MAX_FPS; // 30 fps per second
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / Constants.MAX_FPS;
        Point p=new Point();
        while(running){
            startTime = System.nanoTime();
            this.canvas = null;
            try{
                this.canvas = this.surfaceHolder.lockCanvas();
                synchronized (this.surfaceHolder){
                    if (this.canvas != null) {
                        if (this.iga.touchDone()){
                            this.render.updateTouch(this.iga.getTouchX(),this.iga.getTouchY());
                        }
                        else
                            this.render.disableTouchCardPointer();
                        this.render.draw(this.canvas, p);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if (this.canvas != null){
                    try{
                        this.surfaceHolder.unlockCanvasAndPost(this.canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            timeMilliSeconds = (System.nanoTime() - startTime)/ 1000000; //miliseconds
            waitTime = targetTime - timeMilliSeconds;

            try{
                if(waitTime > 0)
                    this.sleep(waitTime);
            }catch(Exception e){
                e.printStackTrace();
            }

            totalTime = totalTime + System.nanoTime() - startTime;
            frameCount = frameCount + 1;
            if(frameCount == Constants.MAX_FPS){
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
}