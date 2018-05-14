package com.mindworkouts.org.appeltres;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceView;

import java.util.ArrayList;

public class Render extends SurfaceView {

    private ArrayList<Bitmap> cards = new ArrayList<Bitmap>();
    private Bitmap dorso;
    private Bitmap background;


    public Render(Context context) {
        super(context);
        this.initBitmaps();
    }
    public void draw(Canvas canvas, Point p) {


    }
    private void initBitmaps(){
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c1));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c2));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c3));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c4));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c5));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c6));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c7));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c8));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c9));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c10));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c11));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c12));
        this.cards.add(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.c13));
        this.dorso = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.dorso01);
        this.background = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.background01);
    }

}
