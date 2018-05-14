package com.mindworkouts.org.appeltres.Model;

import android.graphics.Rect;

import com.mindworkouts.org.appeltres.Constants;

public class Entity {
    private int width; //width del personaje
    private int height; //height del personaje

    private int positionX; // actual position x
    private int positionY; //actual position y

    private float speed = 0; //speed
    private Rect rect;

    public Entity(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;//(int)(height*(1-Constants.SCREEN_MARGIN_SCALE))+Constants.SCREEN_MARGIN_HEIGTH/2;
        this.positionX = x+ Constants.SCREEN_MARGIN_WIDTH/2;
        this.positionY = y+Constants.SCREEN_MARGIN_HEIGTH/2;
        rect = new Rect(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight());
    }
    //si esa posición x, teniendo en cuenta el ancho está dentro de la pantalla retorna true
    public boolean actorInScreenXRange(int newX){
        if (newX > Constants.SCREEN_MARGIN_WIDTH/2 && newX < Constants.SCREEN_WIDTH-getWidth()) {
            return true;
        }
        return false;
    }
    //si esa posición y, teniendo en cuenta el alto está dentro de la pantalla retorna true
    public boolean actorInScreenYRange(int newY){
        if (newY > Constants.SCREEN_MARGIN_HEIGTH/2 - this.getRect().centerY()*0.6  && newY < Constants.SCREEN_HEIGTH-getHeight()) {
            return true;
        }
        return false;
    }
    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
    public int[] getCenterPoint() {
        int[] pos ={this.getPositionX() + getWidth() / 2, this.getPositionY() + getHeight() / 2};
        return pos;
    }

    //Mètode que retorna true si el collider d'aquest actor intersecta amb el rebut per paremetre
    public boolean getCollision(Rect r) {
        return this.getRect().intersect(r);
    }

    //Mètode que actualitza i retorna el rectangle del collider d'aquest actor
    public Rect getRect() {
        rect.set(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight());
        return rect;
    }
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setWidth(int width) {
        this.width= width;
    }

    public void setHeight(int height) {
        this.height= height;
    }

    public float getSpeed() {
        return speed;
    }
}