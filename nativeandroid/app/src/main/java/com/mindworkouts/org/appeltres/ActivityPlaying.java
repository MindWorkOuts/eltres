package com.mindworkouts.org.appeltres;


import android.app.Activity;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
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
        private boolean isShowingHand = false;
        private boolean clickDone = false;
        private Controller controller;

    public NotificationManager notificationManager;
        public TelephonyManager telephonyManager;
        public PhoneStateListener listener;
        public boolean truca;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            initGameContent();
        }
        private void initGameContent(){

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int y = displayMetrics.heightPixels;
            int x = displayMetrics.widthPixels;
            Constants.SCREEN_HEIGTH_TOTAL = y;
            Constants.SCREEN_WIDTH_TOTAL = x;
            Constants.SCREEN_HEIGTH = y;
            Constants.SCREEN_WIDTH = x;
            Constants.CARD_HEIGTH = Constants.SCREEN_HEIGTH/3;
            Constants.CARD_WIDTH = (int)(Constants.CARD_HEIGTH/1.33333);


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
            Constants.HAND_CARDS_XY_SHOWING = new int[5][2];
            for(int i = 0; i < pos.length; i++){
                Constants.HAND_CARDS_XY_SHOWING[i][0]=pos[i][0];
                Constants.HAND_CARDS_XY_SHOWING[i][1]=pos[i][1]-Constants.CARD_HEIGTH/4;
            }

            Constants.HAND_CARDS_XY = pos;
            int posHidden[][] =   {
                    {Constants.SCREEN_WIDTH_TOTAL/2 - Constants.CARD_WIDTH/2 - (xTranslation)*2,
                            Constants.SCREEN_HEIGTH_TOTAL+Constants.CARD_HEIGTH},

                    {Constants.SCREEN_WIDTH_TOTAL/2 + Constants.CARD_WIDTH/2 + xTranslation,
                            Constants.SCREEN_HEIGTH_TOTAL+Constants.CARD_HEIGTH}};

            Constants.HAND_CARDS_XY_UNSEEN= posHidden;
            Constants.HAND_PANEL = new Rect(Constants.CARD_WIDTH,Constants.SCREEN_HEIGTH_TOTAL-Constants.CARD_HEIGTH/2,Constants.SCREEN_WIDTH_TOTAL-Constants.CARD_WIDTH,Constants.SCREEN_HEIGTH_TOTAL);
            Constants.HEAP_RECT = new Rect(Constants.CARD_WIDTH, 0, Constants.SCREEN_WIDTH_TOTAL-Constants.CARD_WIDTH, Constants.SCREEN_HEIGTH_TOTAL/2);

//            Constants.CARD_TABLE_WIDTH = 3*Constants.CARD_WIDTH/4;//75%
//            Constants.CARD_TABLE_HEIGTH= 3*Constants.CARD_HEIGTH/4;

            Constants.CARD_TABLE_WIDTH = 2*Constants.CARD_WIDTH/3;//75%
            Constants.CARD_TABLE_HEIGTH= 2*Constants.CARD_HEIGTH/3;

            Constants.CARD_FINAL_WIDTH= 2*Constants.CARD_WIDTH/3;//66%
            Constants.CARD_FINAL_HEIGTH= 2*Constants.CARD_HEIGTH/3;

            Constants.HEAP_CARDS_X = Constants.HEAP_RECT.centerX()+Constants.CARD_TABLE_WIDTH/6;
            Constants.HEAP_CARDS_Y = Constants.HEAP_RECT.centerY()-Constants.CARD_TABLE_HEIGTH/4;

            Constants.DRAW_CARDS_X = Constants.HEAP_RECT.centerX()-Constants.CARD_TABLE_WIDTH-Constants.CARD_TABLE_WIDTH/6;
            Constants.DRAW_CARDS_Y = Constants.HEAP_RECT.centerY()-Constants.CARD_TABLE_HEIGTH/4;
            initTableCardsPositions();
            initIconsPositions();
            initVariables();
            setContentView(render);
            controller.initMatrixs();
        }
        private void initIconsPositions(){
            Constants.USERS_ICON_RECT = new Rect[3];
            Constants.HAND_ICON_RECT = new Rect[3];
            Constants.USERS_ICON_WIDTH = Constants.SCREEN_HEIGTH_TOTAL/6;
            Constants.HAND_ICON_WIDTH = Constants.SCREEN_WIDTH_TOTAL/16;
            Constants.HAND_ICON_HEIGTH = Constants.SCREEN_HEIGTH_TOTAL/8;
            Constants.USERS_ICON_RECT[0] = new Rect(Constants.SCREEN_WIDTH_TOTAL/14,3*Constants.SCREEN_HEIGTH_TOTAL/4,Constants.SCREEN_WIDTH_TOTAL/14 + Constants.USERS_ICON_WIDTH,3*Constants.SCREEN_HEIGTH_TOTAL/4 + Constants.USERS_ICON_WIDTH);
            Constants.USERS_ICON_RECT[1] = new Rect(Constants.SCREEN_WIDTH_TOTAL/35,Constants.SCREEN_HEIGTH_TOTAL/4,Constants.SCREEN_WIDTH_TOTAL/35 + Constants.USERS_ICON_WIDTH ,Constants.SCREEN_HEIGTH_TOTAL/4+Constants.USERS_ICON_WIDTH );
            Constants.USERS_ICON_RECT[2] = new Rect(Constants.SCREEN_WIDTH_TOTAL-Constants.USERS_ICON_RECT[1].right,Constants.USERS_ICON_RECT[1].top,Constants.SCREEN_WIDTH_TOTAL-Constants.USERS_ICON_RECT[1].left,Constants.USERS_ICON_RECT[1].bottom);

            for(int i = 0 ; i < 3 ; i++){
                Constants.HAND_ICON_RECT[i] = new Rect(Constants.USERS_ICON_RECT[i].centerX()  ,Constants.USERS_ICON_RECT[i].top + 3*Constants.USERS_ICON_WIDTH/4 ,Constants.USERS_ICON_RECT[i].centerX() +Constants.HAND_ICON_WIDTH ,Constants.USERS_ICON_RECT[i].top + 3*Constants.USERS_ICON_WIDTH/4+Constants.HAND_ICON_HEIGTH);
            }


            Constants.OPT_BUTTON_INGAME_BITMAP = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.optbutton01);
            Constants.CHK_BUTTON_BITMAP= BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.checkbutton01);
            Constants.HAND_ICON_DEFAULT_BITMAP= BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.handicon01);
            Constants.USERS_ICON_DEFAULT_BITMAP= BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.avatar01);
            Constants.MONEY_BOX_BITMAP= BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.moneybox01);

            int optButtonW = 3*Constants.SCREEN_WIDTH_TOTAL/28;
            int optButtonH = Constants.SCREEN_HEIGTH_TOTAL/12;
            Constants.CHECK_ICON_RECT = new Rect(6*Constants.SCREEN_WIDTH_TOTAL/7,Constants.SCREEN_HEIGTH_TOTAL-Constants.SCREEN_HEIGTH_TOTAL/30-optButtonH,6*Constants.SCREEN_WIDTH_TOTAL/7+optButtonW,Constants.SCREEN_HEIGTH_TOTAL-Constants.SCREEN_HEIGTH_TOTAL/30);

            Constants.OPT_BUTTON_INGAME_RECT = new Rect(6*Constants.SCREEN_WIDTH_TOTAL/7,Constants.SCREEN_HEIGTH_TOTAL/30,6*Constants.SCREEN_WIDTH_TOTAL/7+optButtonW,Constants.SCREEN_HEIGTH_TOTAL/30+optButtonH);

            Constants.HAND_ICON_TEXT_SIZE = Constants.SCREEN_WIDTH_TOTAL/50;

            Constants.MONEY_BOX_RECT = new Rect(Constants.USERS_ICON_RECT[1].left, Constants.OPT_BUTTON_INGAME_RECT.top, 2*(Constants.OPT_BUTTON_INGAME_RECT.right - Constants.OPT_BUTTON_INGAME_RECT.left), Constants.OPT_BUTTON_INGAME_RECT.bottom );

        }
        private void initTableCardsPositions(){
            Constants.tableCards = new Point[3][3];
            Point point;
            int tabulate = (int)(Constants.CARD_WIDTH*0.1f);
            int staticAlignment = Constants.SCREEN_HEIGTH_TOTAL/2+Constants.CARD_FINAL_HEIGTH/2;


            //CENTER

            point = new Point();
            point.x = Constants.SCREEN_WIDTH_TOTAL/2-Constants.CARD_FINAL_WIDTH/2;
            point.y =  staticAlignment;
            Constants.tableCards[0][0] = point;//centercenter

            point = new Point();
            point.x = Constants.tableCards[0][0].x -Constants.CARD_FINAL_WIDTH -tabulate;
            point.y =  staticAlignment;
            Constants.tableCards[0][1] = point;//centerleft

            point = new Point();
            point.x = Constants.SCREEN_WIDTH_TOTAL/2+Constants.CARD_FINAL_WIDTH/2+tabulate;
            point.y =  staticAlignment;
            Constants.tableCards[0][2] = point;//centerright

            //LEFT

            staticAlignment = Constants.tableCards[0][2].y-tabulate;

            point = new Point();
            point.y =  staticAlignment+Constants.CARD_FINAL_WIDTH;
            point.x = (int)(Constants.CARD_FINAL_WIDTH*3);
            Constants.tableCards[1][0] = point;//rightright

            point = new Point();
            point.y =  Constants.tableCards[1][0].y-Constants.CARD_FINAL_WIDTH-tabulate;
            point.x =Constants.tableCards[1][0].x;
            Constants.tableCards[1][1] = point;//rightright

            point = new Point();
            point.x =Constants.tableCards[1][1].x;
            point.y =  Constants.tableCards[1][1].y-Constants.CARD_FINAL_WIDTH-tabulate;
            Constants.tableCards[1][2] = point;//rightright
            //RIGHT
            point = new Point();
            point.x = Constants.SCREEN_WIDTH_TOTAL-Constants.tableCards[1][2].x+Constants.CARD_TABLE_HEIGTH;
            point.y = Constants.tableCards[1][0].y;
            Constants.tableCards[2][0] = point;//rightright

            point = new Point();
            point.x = Constants.SCREEN_WIDTH_TOTAL-Constants.tableCards[1][2].x+Constants.CARD_TABLE_HEIGTH;
            point.y = Constants.tableCards[1][1].y;
            Constants.tableCards[2][1] = point;//centerright

            point = new Point();
            point.x = Constants.SCREEN_WIDTH_TOTAL-Constants.tableCards[1][2].x+Constants.CARD_TABLE_HEIGTH;
            point.y = Constants.tableCards[1][2].y;
            Constants.tableCards[2][2] = point;//centerright
        }

        private void initVariables() {
            thread=null;
            render=null;
            System.gc();
            render = new Render(this.getApplicationContext());
            controller = new Controller(getApplicationContext(), render);
            render.setController(controller);
            this.thread = new MainThread(this.render.getHolder(), this.render, this, controller);
            this.thread.setRunning(true);
            this.thread.start();
        }
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
          initGameContent();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            initGameContent();
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
    public boolean isClickDone(){return this.clickDone;}
    public boolean isOptionsClicked(){
        if (!this.clickDone)return false;
        if (Constants.OPT_BUTTON_INGAME_RECT.contains((int)currentX,(int)currentY))
            return true;
        return false;
    }
    public boolean isProfileClicked(){
        if (!this.clickDone)return false;
        if (Constants.USERS_ICON_RECT[0].contains((int)currentX,(int)currentY))
            return true;
        return false;
    }
    public int isEnemyProfileClicked(){
        if (!this.clickDone)return -1;
        for(int i = 1; i < Constants.NUM_PLAYERS; i++)
            if (Constants.USERS_ICON_RECT[i].contains((int)currentX,(int)currentY))
                return i;
        return -1;
    }

    public boolean isTouchingHandPanel(){
            boolean isTouching = Constants.HAND_PANEL.contains((int)this.lastDownX,(int)this.lastDownY);
            if(isTouching)this.isShowingHand=true;
            return isTouching;
        }
        public boolean hideHand(){return !this.isShowingHand;}
        public boolean touchDone(){
            return this.beenTouched;
        }
        public boolean doubleTouchUp(){
            return this.touchUp;
        }
        public int isScrolling(){
            if(!touchUp && Constants.HAND_PANEL.contains((int)this.currentX,(int)this.currentY)){
                if (Math.abs(downX - currentX) > Math.abs(downY- currentY)&&
                        Math.abs(downX-currentX)>Constants.SCREEN_WIDTH_TOTAL/14) {
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
                isShowingHand = false;
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

