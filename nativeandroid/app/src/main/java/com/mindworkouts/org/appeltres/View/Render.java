package com.mindworkouts.org.appeltres.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
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
    private Paint color, opac;
    private Controller controller;


    public Render(Context context) {
        super(context);
        opac = new Paint(Color.BLACK);
        opac.setAlpha(100);
        color = new Paint();
        this.initBitmaps();
        this.setBitmapDims();
    }
    public void setController(Controller controller){
        this.controller = controller;
    }
    public void draw(Canvas canvas, Point p) {
        canvas.drawBitmap(this.background,new Rect(0,0,background.getWidth(),background.getHeight()),new Rect(0,0, Constants.SCREEN_WIDTH_TOTAL,Constants.SCREEN_HEIGTH_TOTAL),color);
        canvas.drawText(""+p.x+":"+p.y,Constants.SCREEN_WIDTH_TOTAL/2,Constants.SCREEN_HEIGTH_TOTAL/2,color);
        canvas.drawLine(Constants.HEAP_RECT.left,Constants.HEAP_RECT.top,Constants.HEAP_RECT.right,Constants.HEAP_RECT.top, color);
        canvas.drawLine(Constants.HEAP_RECT.left,Constants.HEAP_RECT.top,Constants.HEAP_RECT.left,Constants.HEAP_RECT.bottom, color);
        canvas.drawLine(Constants.HEAP_RECT.right,Constants.HEAP_RECT.top,Constants.HEAP_RECT.right,Constants.HEAP_RECT.bottom, color);
        canvas.drawLine(Constants.HEAP_RECT.left,Constants.HEAP_RECT.bottom,Constants.HEAP_RECT.right,Constants.HEAP_RECT.bottom, color);

        canvas.drawLine(Constants.HEAP_RECT.centerX(),0,Constants.HEAP_RECT.centerX(),Constants.HEAP_RECT.bottom, color);
/*

        int x = Constants.SCREEN_WIDTH_TOTAL/2-Constants.CARD_FINAL_WIDTH/2;
        int y = Constants.SCREEN_HEIGTH_TOTAL/2+Constants.CARD_FINAL_HEIGTH/2;
        int r = Constants.SCREEN_WIDTH_TOTAL/2+Constants.CARD_FINAL_WIDTH/2;
        int b = y + Constants.CARD_FINAL_HEIGTH;
        Rect rect = new Rect(x,y,r,b);
        int separacion = (int)(Constants.CARD_TABLE_WIDTH*0.1f);
        canvas.drawBitmap(cardsBitmap.get(1),new Rect(0,0,cardsBitmap.get(1).getWidth(),cardsBitmap.get(1).getHeight()),rect, color);
        x = Constants.SCREEN_WIDTH_TOTAL/2-Constants.CARD_FINAL_WIDTH-Constants.CARD_FINAL_WIDTH/2-separacion;
        y = Constants.SCREEN_HEIGTH_TOTAL/2+Constants.CARD_FINAL_HEIGTH/2;
        r = x+Constants.CARD_FINAL_WIDTH;
        b = y + Constants.CARD_FINAL_HEIGTH;
        rect = new Rect(x,y,r,b);
        canvas.drawBitmap(cardsBitmap.get(1),new Rect(0,0,cardsBitmap.get(1).getWidth(),cardsBitmap.get(1).getHeight()),rect, color);

        x = Constants.SCREEN_WIDTH_TOTAL/2+Constants.CARD_FINAL_WIDTH/2+separacion;
        y = Constants.SCREEN_HEIGTH_TOTAL/2+Constants.CARD_FINAL_HEIGTH/2;
        r = x+Constants.CARD_FINAL_WIDTH;
        b = y + Constants.CARD_FINAL_HEIGTH;
        rect = new Rect(x,y,r,b);
        canvas.drawBitmap(cardsBitmap.get(1),new Rect(0,0,cardsBitmap.get(1).getWidth(),cardsBitmap.get(1).getHeight()),rect, color);

        //LEFT
        x = Constants.HEAP_RECT.left-Constants.CARD_FINAL_HEIGTH;
        y = Constants.SCREEN_HEIGTH_TOTAL/2-Constants.CARD_FINAL_WIDTH - Constants.CARD_FINAL_WIDTH/2 -separacion;
        r = x+Constants.CARD_FINAL_HEIGTH;
        b = y + Constants.CARD_FINAL_WIDTH;
        rect = new Rect(x,y,r,b);
        canvas.drawRect(rect, color);

        x = Constants.HEAP_RECT.left-Constants.CARD_FINAL_HEIGTH;
        y = Constants.SCREEN_HEIGTH_TOTAL/2 -Constants.CARD_FINAL_WIDTH/2;
        r = x+Constants.CARD_FINAL_HEIGTH;
        b = y + Constants.CARD_FINAL_WIDTH;
        rect = new Rect(x,y,r,b);
        canvas.drawRect(rect, color);

        x = Constants.HEAP_RECT.left-Constants.CARD_FINAL_HEIGTH;
        y = Constants.SCREEN_HEIGTH_TOTAL/2 + Constants.CARD_FINAL_WIDTH/2 + separacion;
        r = x+Constants.CARD_FINAL_HEIGTH;
        b = y + Constants.CARD_FINAL_WIDTH;
        rect = new Rect(x,y,r,b);
        canvas.drawRect(rect, color);
*/
        ArrayList<Card> cards;
        Card card;
        Bitmap bitmapCard;
        int i;
        cards = controller.getTableCards();
        for (i = 0; i < cards.size(); i++){
            card = cards.get(i);
            bitmapCard = this.cardsBitmap.get(card.getValue());
            canvas.drawBitmap(bitmapCard,card.getCardMatrix(), card.getPaint());
        }

        cards = controller.getDrawingCards();
        for(i = 0; i < cards.size(); i++){
            card = cards.get(i);
            bitmapCard = this.cardsBitmap.get(card.getValue());
            canvas.drawBitmap(bitmapCard,card.getCardMatrix(), card.getPaint());
        }
        cards = controller.getHeap();

        for (i = 0; i < cards.size() ; i++){
            card = cards.get(i);
            bitmapCard = this.cardsBitmap.get(card.getValue());
            canvas.drawBitmap(bitmapCard,card.getCardMatrix(), card.getPaint());
        }

        cards = controller.getVisiblePlayerHand();
        for (i = 0; i < cards.size() && i < Constants.MAX_CARDS_SEEN; i++){
            card = cards.get(i);
            bitmapCard = this.cardsBitmap.get(card.getValue());
            canvas.drawBitmap(bitmapCard,card.getCardMatrix(), card.getPaint());
        }

    }
    public void setBitmapDims(){
        Constants.BITMAP_CARDS_SIZE = new int[cardsBitmap.size()][2];
        for(int i = 0 ; i < this.cardsBitmap.size(); i++) {
            Constants.BITMAP_CARDS_SIZE[i][0] = this.cardsBitmap.get(i).getWidth();
            Constants.BITMAP_CARDS_SIZE[i][1] = this.cardsBitmap.get(i).getHeight();
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
