package com.mindworkouts.org.appeltres.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceView;

import com.mindworkouts.org.appeltres.Constants;
import com.mindworkouts.org.appeltres.Controller.Controller;
import com.mindworkouts.org.appeltres.Model.Card;
import com.mindworkouts.org.appeltres.Model.Player;
import com.mindworkouts.org.appeltres.R;

import java.util.ArrayList;

public class Render extends SurfaceView {

    private ArrayList<Bitmap> cardsBitmap = new ArrayList<Bitmap>();
    private Bitmap background;
    private Paint color = new Paint();
    private Controller controller;


    public Render(Context context, Controller controller) {
        super(context);
        this.initBitmaps();
        this.controller = controller;
    }
    public void draw(Canvas canvas, Point p) {
        canvas.drawBitmap(this.background,new Rect(0,0,background.getWidth(),background.getHeight()),new Rect(0,0, Constants.SCREEN_WIDTH_TOTAL,Constants.SCREEN_HEIGTH_TOTAL),color);
        ArrayList<Card> playerHand = controller.getPlayerHand();
        for (int i = 0; i < playerHand.size(); i++){
            Card card = playerHand.get(i);
            canvas.drawBitmap(this.cardsBitmap.get(card.getValue()),new Rect(0,0,cardsBitmap.get(card.getValue()).getWidth(),cardsBitmap.get(card.getValue()).getHeight()),new Rect(card.getPositionX(),card.getPositionY(),card.getPositionX()+card.getWidth(),card.getPositionY()+card.getHeight()),color);
            canvas.drawText(card.getStaticX()+","+card.getStaticY(),Constants.SCREEN_WIDTH_TOTAL/2,Constants.SCREEN_HEIGTH_TOTAL/2,color);
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
