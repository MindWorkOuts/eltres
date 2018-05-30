package com.mindworkouts.org.appeltres;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.sqrt;

public class Constants {
    public static final int MAX_FPS = 30;
    public static int SCREEN_MARGIN_WIDTH;
    public static int SCREEN_MARGIN_HEIGTH;
    public static int SCREEN_WIDTH_TOTAL;
    public static int SCREEN_HEIGTH_TOTAL;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGTH;
    public static Rect HEAP_RECT;
    /*Constants.CARD_HEIGTH = Constants.SCREEN_HEIGTH/3;
    Constants.CARD_WIDTH = (int)(Constants.CARD_HEIGTH/1.33333);*/
    public static int CARD_HEIGTH;
    public static int CARD_WIDTH;
    public static int CARD_TABLE_HEIGTH;
    public static int CARD_TABLE_WIDTH ;
    public static int CARD_FINAL_HEIGTH;
    public static int CARD_FINAL_WIDTH;
    public static int HEAP_DEGREES= 0;
    public static final int HEAP_MAX_DEGREES= 30;
    public static final int HEAP_MIN_DEGREES= 0;
    public static int HEAP_DEGREES_DIRECTION= 1;
    public static final int FINAL_CARD_SPEED = 50;
    public static final int INITIAL_CARD_SPEED = 10;
    public static int ACTUAL_CARD_SPEED = 40;
    public static Matrix LEFT_CARD_MATRIX;
    public static Matrix RIGHT_CARD_MATRIX;
    public static Matrix CENTER_CARD_MATRIX;
    //% hidding of height card
    public static final float HIDDING_CARDS_FACTOR = 0.33f;
    public static int CELDA_HEIGTH;
    public static float SCREEN_MARGIN_SCALE = 0.2f;
    public static final int BULLET_SPEED_HERO = 30;
    public static final int BULLET_SPEED_TURRET = 20;
    public static final int TURRET_SHOOT_DELAY = 30;
    public static final int HERO_DEFAULT_SHOOT_DELAY = 10;
    public static int HERO_SHOOT_DELAY = 8;
    public static final int BULLET_DAMAGE = 20;
    public static final int NUM_CELDAS_HORIZONTAL = 9;
    public static final int NUM_CELDAS_VERTICAL = 16;
    public static final char SIMBOLO_SUELO = '0';
    public static final char SIMBOLO_MURO= 'x';
    public static final short DIRECCION_NULL = 0;
    public static final short DIRECCION_NORTH = 1;
    public static final short DIRECCION_NORTH_EAST = 2;
    public static final short DIRECCION_EAST = 3;
    public static final short DIRECCION_SOUTH_EAST = 4;
    public static final short DIRECCION_SOUTH = 5;
    public static final short DIRECCION_SOUTH_WEST= 6;
    public static final short DIRECCION_WEST= 7;
    public static final short DIRECCION_WEST_NORTH = 8;
    public static final int DOOR_EAST = 0;
    public static final int DOOR_NORTH = 1;
    public static final int DOOR_WEST = 2;
    public static final int DOOR_SOUTH = 3;
    public static final int DROP_HP = 0;
    public static final int DROP_DMG = 1;
    public static final int DROP_FIRERATE = 2;
    public static final int DROP_SPEED = 3;
    public static final int DROP_LAPTOP = 4;
    public static final int BUFF_DMG = 20;
    public static final int BUFF_HEALTH = 10;
    public static final int BUFF_SPEED = 10;
    public static final int BUFF_FIRERATE = -2;
    public static final int MAX_DMG = 100;
    public static final int MAX_HEALTH = 100;
    public static final int MAX_SPEED = 100;
    public static final int MAX_FIRERATE = 5;
    public static Matrix[] HAND_CARD_MATRIX;
    public static int HAND_CARDS_XY[][] = new int[5][2];
    //unseen hand cards, left = 0 &&right = 1
    public static int HAND_CARDS_XY_UNSEEN[][] = new int[2][2];
    public static Matrix[] HAND_CARD_MATRIX_UNSEEN = new Matrix[2];
    public static int HAND_CARDS_XY_SHOWING[][] = new int[5][2];
    public static Matrix[] HAND_CARD_MATRIX_SHOWING= new Matrix[5];
    public static int BITMAP_CARDS_SIZE[][];
    public static int MAX_CARDS_SEEN = 5;


        public static double[] normalize(int x, int y){
        double len = sqrt(x*x+y*y);
        if (len>0){
            double auxx = (double) (x)/len;
            double auxy = (double) (y)/len;
            double [] norm = {auxx,auxy};
            return norm;
        }
        double [] norm = {x,y};
        return norm;
    }
    //faster than sqrts
    public static double speedRatio(int x, int y){
        int xabs = abs(x);
        int yabs = abs(y);
        double ratio = 1 / max(xabs, yabs);
        ratio = ratio * (1.29289 - (xabs+yabs) * ratio * 0.29289);
        return ratio;
    }
    public static Rect HAND_PANEL;
    public static int DRAW_CARDS_X;
    public static int DRAW_CARDS_Y;
    public static int HEAP_CARDS_X;
    public static int HEAP_CARDS_Y;
    //[0][0] 0 center carta 1
    //[0][1] 0 center carta 2
    //[0][2] 0 center carta 3
    //[1][0] 0 left carta 1
    //[1][1] 0 left carta 2
    //[1][2] 0 left carta 3
    //[2][0] 0 right carta 1
    //[2][1] 0 right carta 2
    //[2][2] 0 right carta 3
    public static Point[][] tableCards;
}