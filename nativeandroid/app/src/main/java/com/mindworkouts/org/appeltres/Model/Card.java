package com.mindworkouts.org.appeltres.Model;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import com.mindworkouts.org.appeltres.Constants;

    public class Card extends Entity{
        private int value = 0;
        private int staticX = 0;
        private int staticY = 0;
        private boolean focusing = false;
        private int degrees;
        private Matrix cardMatrix;
        private int rotated;
        private Paint paint;
        public Card(int width, int height, int x, int y, int value) {
            super(width, height, x, y);
            this.staticX = x;
            this.staticY = y;
            this.setNextXPosition(staticX);
            this.setNextYPosition(staticY);
            super.setSpeed(Constants.INITIAL_CARD_SPEED);
            this.value = value;
            cardMatrix = new Matrix();
            degrees = 0;
            rotated = 0;
        }

        public void setCardMatrix(Matrix cardMatrix) {
            this.cardMatrix = cardMatrix;
        }
        public void rotateMatrix(int degrees) {
            this.cardMatrix = cardMatrix;
        }
        public void resetMatrixRotation() {
            this.cardMatrix = cardMatrix;
        }
        public void translateMatrix(int x, int y) {
            this.cardMatrix = cardMatrix;
        }
        public void scaleMatrix(int dim) {
            this.cardMatrix = cardMatrix;
        }
        public Matrix getCardMatrix() {return cardMatrix;        }
        public void showPlayable(boolean playable){
            if(!playable) {
                this.paint = new Paint(Color.YELLOW);
                this.paint.setAlpha(90);
            }
            else
                this.paint=null;
        }
        public Paint getPaint() {
            return paint;
        }

        public Rect getRectNextYPosition(double angle, double restitution) {
            int newY;
            newY = (int) (super.getPositionY() - restitution * super.getSpeed() * Math.sin(angle));
            Rect pos = new Rect(getPositionX(), newY, getPositionX() + getWidth(), newY + getHeight());
            return pos;
        }

        public Rect getRectNextXPosition(double angle, double restitution) {
            int newX;
            newX = (int) (super.getPositionX() - restitution * super.getSpeed() * Math.cos(angle));
            Rect pos = new Rect(newX, getPositionY(), newX + getWidth(), getPositionY() + getHeight());
            return pos;
        }
        public void addDegrees(int deg){this.degrees+=deg;}

        public void setDegrees(int deg){this.degrees=deg;}
        public int getStaticY() {return this.staticY;}
        public int getStaticX() {return this.staticX;}

        public void setNextXPosition(int newX) {
            super.setPositionX(newX);
        }
        public void setFocus(boolean is) {
            this.focusing=is;
        }
        public boolean isFocusing() {
            return focusing;
        }
        public void setValue(int value){
            this.value = value;
        }

        public void updateMatrix(){
            int positionX = this.getPositionX();
            int positionY = this.getPositionY();
            int bWidth = Constants.BITMAP_CARDS_SIZE[this.getValue()][0];
            int bHeight = Constants.BITMAP_CARDS_SIZE[this.getValue()][1];
            float scaleWidth = ((float) this.getWidth()) / bWidth;
            float scaleHeight = ((float) this.getHeight()) / bHeight;
            this.cardMatrix = new Matrix();
            cardMatrix.setScale(scaleWidth, scaleHeight);
            cardMatrix.postTranslate(positionX, positionY);
            if(getPositionX()==getStaticX() && getPositionY()==getStaticY()) {
                cardMatrix.postTranslate(0, +((float) Math.sin((double) degrees * Math.PI / 180) * Constants.CARD_WIDTH));
                cardMatrix.preRotate(-degrees);
            }
        }

        public void setNextYPosition(int newY) {
            super.setPositionY(newY);
        }
        public void setStaticX(int newX) {
            this.staticX=newX;
        }
        public void setStaticY(int newY) {
            this.staticY=newY;
        }public int getValue(){return this.value;}
}

