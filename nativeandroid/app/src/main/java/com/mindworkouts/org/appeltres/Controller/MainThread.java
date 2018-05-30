package com.mindworkouts.org.appeltres.Controller;


import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.mindworkouts.org.appeltres.Constants;
import com.mindworkouts.org.appeltres.ActivityPlaying;
import com.mindworkouts.org.appeltres.Model.Card;
import com.mindworkouts.org.appeltres.View.Render;


public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private Render render;
    private boolean running;
    public static Canvas canvas;
    private ActivityPlaying iga;
    private Controller controller;
    private boolean isShowingHand;

    public MainThread(SurfaceHolder surfaceHolder, Render render, ActivityPlaying iga, Controller controller){
        super();
        this.surfaceHolder = surfaceHolder;
        this.render = render;
        this.iga = iga;
        this.controller = controller;
        isShowingHand = false;
     }

    public void setRunning(boolean running){
        this.running = running;
    }

    public void onResume(){
        this.run();
    }

    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    public void run(){
        long startTime;
        long timeMilliSeconds = 1000 / Constants.MAX_FPS; // 30 fps per second
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / Constants.MAX_FPS;
        long scrollTimeout = 3;
        long scrollCount= 0;
        boolean reachedTimeoutScroll = false;
        Point p=new Point();
        p.x=0;
        p.y=0;
        while(running){
            startTime = System.nanoTime();
            this.canvas = null;
            try{
                this.canvas = this.surfaceHolder.lockCanvas();
                synchronized (this.surfaceHolder){
                    if (this.canvas != null) {
                        if(this.iga.isTouchingHandPanel() )
                            this.controller.showHand();
                        else
                            this.controller.hideHand();
                        //scrolling
                        if(controller.isShowingHand()) {
                            if (reachedTimeoutScroll) {
                                reachedTimeoutScroll = false;
                                this.controller.scrollHand(iga.isScrolling());
                            }
                            //moving a card
                            else if (this.iga.touchDone() && iga.isScrolling() == 0) {
                                this.controller.updateTouch((int) this.iga.getTouchX(), (int) this.iga.getTouchY());
                            }else {
                                this.controller.cardTouchReleased();
                            }
                            //reset cards position
                        }else {
                                this.controller.cardTouchReleased();
                            }
                        this.controller.resetMatrixs();
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
            if(frameCount==Constants.MAX_FPS){p.y=p.y+1;}
            if(iga.isScrolling()!=0) {
                scrollCount += 1;
                if (scrollCount >= scrollTimeout) {
                    scrollCount =-scrollTimeout;
                    reachedTimeoutScroll = true;
                }
            }else scrollCount=0;

            if(frameCount == Constants.MAX_FPS){
                frameCount = 0;
                totalTime = 0;
            }
        }
    }
    //where = -1 <---- left
    //where = +1 <---- right
    public void swapHand(int where){

    }
}