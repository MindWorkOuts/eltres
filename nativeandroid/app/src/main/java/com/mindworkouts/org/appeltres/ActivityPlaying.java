package com.mindworkouts.org.appeltres;


import android.app.Activity;
import android.app.NotificationManager;
import android.graphics.Canvas;
import android.graphics.Point;
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
import com.mindworkouts.org.appeltres.MainThread;
import com.mindworkouts.org.appeltres.Render;


public class ActivityPlaying extends Activity implements
            GestureDetector.OnGestureListener,
            SurfaceHolder.Callback,
            View.OnClickListener{
        private Button btnPause;
        private Render render;
        private MainThread thread;
        private Canvas c;
        private boolean beenTouched = false;
        private float touchX = 0;
        private float touchY = 0;


    public NotificationManager notificationManager;
        public TelephonyManager telephonyManager;
        public PhoneStateListener listener;
        public boolean truca;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            initVariables();
            setContentView(render);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int y = displayMetrics.heightPixels;
            int x = displayMetrics.widthPixels;
            Constants.SCREEN_HEIGTH_TOTAL = y;
            Constants.SCREEN_WIDTH_TOTAL = x;
        }

        private void initVariables() {
            thread=null;
            render=null;
            System.gc();
            render = new Render(this.getApplicationContext());
            this.thread = new MainThread(this.render.getHolder(), this.render, this);
            this.thread.setRunning(true);
            this.thread.start();
        }
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            this.thread = new MainThread(this.render.getHolder(), this.render, this);
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

        public boolean touchDone(){
            return this.beenTouched;
        }
        public float getTouchX(){return this.touchX;}
        public float getTouchY(){return this.touchY;}

        public boolean onTouchEvent(MotionEvent event) {
            this.touchX = event.getX();
            this.touchY = event.getY();
            System.out.println(event.getX());
            this.beenTouched = true;
            if(event.getAction()==MotionEvent.ACTION_UP)
                beenTouched = false;
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

