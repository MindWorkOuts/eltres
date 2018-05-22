package com.mindworkouts.org.appeltres;


import android.app.Activity;
import android.app.NotificationManager;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;

import com.mindworkouts.org.appeltres.Constants;
import com.mindworkouts.org.appeltres.Controller.Controller;
import com.mindworkouts.org.appeltres.Controller.MainThread;
import com.mindworkouts.org.appeltres.Model.Player;
import com.mindworkouts.org.appeltres.View.Render;


public class ActivityPlaying extends Activity implements
            GestureDetector.OnGestureListener,
            SurfaceHolder.Callback,
            View.OnClickListener{
        private Button btnPause;
        private Render render;
        private MainThread thread;
        private Canvas c;
        private boolean beenTouched = false;
        private float currentX = 0;
        private float currentY = 0;
        private float downX = 0;
        private float downY = 0;
        private float lastDownX = 0;
        private float lastDownY = 0;
        private boolean touchUp = true;
        private boolean clickDone = false;
        private Controller controller;


    public NotificationManager notificationManager;
        public TelephonyManager telephonyManager;
        public PhoneStateListener listener;
        public boolean truca;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int y = displayMetrics.heightPixels;
            int x = displayMetrics.widthPixels;
            Constants.SCREEN_HEIGTH_TOTAL = y;
            Constants.SCREEN_WIDTH_TOTAL = x;
            Constants.SCREEN_HEIGTH = y;
            Constants.SCREEN_WIDTH = x;
            int xTranslation = (int)(0.8*Constants.CARD_WIDTH);
            int yTranslation = (int)(0.07*Constants.CARD_HEIGTH);
            int [][] pos= {
                    //IZQUIERDA MAX
                    {Constants.SCREEN_WIDTH_TOTAL/2 - Constants.CARD_WIDTH/2 - (xTranslation)*2,(int)(
                            Constants.SCREEN_HEIGTH_TOTAL-Constants.CARD_HEIGTH*Constants.HIDDING_CARDS_FACTOR)},

                    //CENTRO IZQUIERDA
                    {Constants.SCREEN_WIDTH_TOTAL/2 - Constants.CARD_WIDTH/2 - (xTranslation) ,(int)(
                            Constants.SCREEN_HEIGTH_TOTAL-Constants.CARD_HEIGTH*Constants.HIDDING_CARDS_FACTOR)},

                    //CENTRO
                    {Constants.SCREEN_WIDTH_TOTAL/2-Constants.CARD_WIDTH/2,(int)(
                            Constants.SCREEN_HEIGTH_TOTAL-Constants.CARD_HEIGTH*Constants.HIDDING_CARDS_FACTOR)},

                    //CENTRO DERECHA
                    {Constants.SCREEN_WIDTH_TOTAL/2 + Constants.CARD_WIDTH/2,(int)(
                            Constants.SCREEN_HEIGTH_TOTAL-Constants.CARD_HEIGTH*Constants.HIDDING_CARDS_FACTOR-yTranslation)},

                    //DERECHA MAX, SI HAY MÁS DE 4 CARTAS
                    {Constants.SCREEN_WIDTH_TOTAL/2 + Constants.CARD_WIDTH/2 + xTranslation,(int)(
                            Constants.SCREEN_HEIGTH_TOTAL-Constants.CARD_HEIGTH*Constants.HIDDING_CARDS_FACTOR-yTranslation/2)}
            };

            Constants.HAND_CARDS_XY = pos;
            int posHidden[][] =   {
                    {Constants.SCREEN_WIDTH_TOTAL/2 - Constants.CARD_WIDTH/2 - (xTranslation)*2,
                    Constants.SCREEN_HEIGTH_TOTAL+Constants.CARD_HEIGTH},

                    {Constants.SCREEN_WIDTH_TOTAL/2 + Constants.CARD_WIDTH/2 + xTranslation,
                    Constants.SCREEN_HEIGTH_TOTAL+Constants.CARD_HEIGTH}};

            Constants.HAND_CARDS_XY_UNSEEN= posHidden;
            Constants.HAND_PANEL = new Rect(Constants.CARD_WIDTH,Constants.SCREEN_HEIGTH_TOTAL-Constants.CARD_HEIGTH/2,Constants.SCREEN_WIDTH_TOTAL-Constants.CARD_WIDTH,Constants.SCREEN_HEIGTH_TOTAL);

            initVariables();
            setContentView(render);
        }

        private void initVariables() {
            thread=null;
            render=null;
            System.gc();
            controller = new Controller(getApplicationContext());
            render = new Render(this.getApplicationContext(), controller);
            controller.setRender(render);
            controller.createPlayer();
            this.thread = new MainThread(this.render.getHolder(), this.render, this, controller);
            this.thread.setRunning(true);
            this.thread.start();
        }
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            this.thread = new MainThread(this.render.getHolder(), this.render, this, controller);
            this.thread.setRunning(true);
            this.thread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            while (true) {
                try {
                    this.thread.setRunning(false);
                    this.thread.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        public boolean isTouchingHandPanel(){return clickDone&&Constants.HAND_PANEL.contains((int)this.lastDownX,(int)this.lastDownY);}
        public boolean touchDone(){
            return this.beenTouched;
        }
        public boolean doubleTouchUp(){
            return this.touchUp;
        }
        public int isScrolling(){
            if(!touchUp && Constants.HAND_PANEL.contains((int)this.currentX,(int)this.currentY)){
                if (Math.abs(downX - currentX) > Math.abs(downY- currentY)
                        &&
                        Math.abs(downX-currentX)>Constants.SCREEN_WIDTH_TOTAL/7) {
                    //RIGHT
                    if (downX < currentX) {
                        return -1;
                    }
                    //LEFT
                    if (downX > currentX) {
                        return 1;
                    }
                    this.beenTouched = true;

                /* else {
                    Log.v("", "y ");

                    if (downYValue < currentY) {
                        Log.v("", "down");

                    }
                    if (downYValue > currentY) {
                        Log.v("", "up");

                    }
                }
                break;
            }*/
                }
            }
            return 0;
        }
        public float getTouchX(){return this.currentX;}
        public float getTouchY(){return this.currentY;}

        public boolean onTouchEvent(MotionEvent event) {
            this.currentX = event.getX();
            this.currentY = event.getY();
            this.beenTouched = true;
            if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
                touchUp=true;
                if(event.getEventTime() -event.getDownTime() <200)clickDone=true;
                else clickDone=false;
                beenTouched = false;
                downX=0;
                downY=0;
            }
            else {
                touchUp = false;
            }
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                lastDownX =currentX;
                lastDownY=currentY;
                downX=currentX;
                downY=currentY;
            }
            render.invalidate();
            return super.onTouchEvent(event);
        }

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent event) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }


        /**
         * "Disables" back button.
         */
        @Override
        public void onBackPressed() {
        }

        @Override
        protected void onPause() {
            super.onPause();
        }

        @Override
        protected void onResume() {
            super.onResume();
        }

        @Override
        public void onClick(View v) {
        }

    }

