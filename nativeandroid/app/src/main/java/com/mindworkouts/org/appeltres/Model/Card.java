package com.mindworkouts.org.appeltres.Model;

import android.graphics.Rect;

import com.mindworkouts.org.appeltres.Constants;

    public class Card extends Entity{
        private int value = 0;
        public Card(int width, int height, int x, int y, int value) {
            super(width, height, x, y);
            super.setSpeed(Constants.CARD_SPEED);
            this.value = value;
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

        public void setNextXPosition(int newX) {
            super.setPositionX(newX);
        }

        public void setNextYPosition(int newY) {
            super.setPositionY(newY);
        }
        public int getValue(){return this.value;}
}

