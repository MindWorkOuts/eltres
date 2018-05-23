package com.mindworkouts.org.appeltres;

import android.graphics.Matrix;
import android.graphics.Rect;

import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class Constants {
    public static final int MAX_FPS = 30;
    public static int SCREEN_MARGIN_WIDTH;
    public static int SCREEN_MARGIN_HEIGTH;
    public static int SCREEN_WIDTH_TOTAL;
    public static int SCREEN_HEIGTH_TOTAL;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGTH;
    public static int CARD_HEIGTH = 250;
    public static int CARD_WIDTH = 192;
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
    public static final int CARD_SPEED = 40;
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
    public static Rect HAND_PANEL;
}