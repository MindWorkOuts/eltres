package com.mindworkouts.org.appeltres;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.mindworkouts.org.appeltres.Model.Card;
import com.mindworkouts.org.appeltres.Model.Player;

import java.util.ArrayList;

public class Render extends SurfaceView {

    private ArrayList<Bitmap> cardsBitmap = new ArrayList<Bitmap>();
    private Bitmap background;
    private Paint color = new Paint();
    private Player mainPlayer;
    private int touchCardPointer = -1;


    public Render(Context context) {
        super(context);
        this.initBitmaps();
        int [] values = {4};
        this.mainPlayer = new Player(values);
    }
    public int getTouchCardPointer(){return this.touchCardPointer;}
    public void disableTouchCardPointer(){touchCardPointer=-1;}
    public void updateTouch (float x, float y){
       /* if(getTouchCardPointer()!=-1){
            this.mainPlayer.updateCardPosition(getTouchCardPointer(), (int) x, (int) y);
        }
        else {*/
            ArrayList<Card> playerHand = mainPlayer.getHandCards();
            for (int i = 0; i < playerHand.size(); i++) {
                Rect cardRect = playerHand.get(i).getRect();
                if (cardRect.contains((int) x, (int) y)) {
                    this.mainPlayer.updateCardPosition(i, (int) x-cardRect.width()/2, (int) y-cardRect.height()/2);
                }

            }
       //   }


    }
    public void draw(Canvas canvas, Point p) {
        canvas.drawBitmap(this.background,new Rect(0,0,background.getWidth(),background.getHeight()),new Rect(0,0,Constants.SCREEN_WIDTH_TOTAL,Constants.SCREEN_HEIGTH_TOTAL),color);
        ArrayList<Card> playerHand = mainPlayer.getHandCards();
        for (int i = 0; i < playerHand.size(); i++){
            Card card = playerHand.get(i);
            canvas.drawBitmap(this.cardsBitmap.get(card.getValue()),new Rect(0,0,cardsBitmap.get(card.getValue()).getWidth(),cardsBitmap.get(card.getValue()).getHeight()),new Rect(card.getPositionX(),card.getPositionY(),card.getPositionX()+card.getWidth(),card.getPositionY()+card.getHeight()),color);
        }
    }
    private void initBitmaps(){
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.dorso01));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c1));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c2));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c3));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c4));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c5));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c6));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c7));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c8));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c9));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c10));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c11));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c12));
        this.cardsBitmap.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c13));
        this.background = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.background01);
    }
}
