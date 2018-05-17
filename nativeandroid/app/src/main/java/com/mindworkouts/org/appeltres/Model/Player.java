package com.mindworkouts.org.appeltres.Model;

import android.graphics.Point;
import android.graphics.Rect;

import com.mindworkouts.org.appeltres.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {//extends Entity{

    private ArrayList<Card> handCards = new ArrayList<Card>();
    private int [] targetCards = {0,1,2};


    public Player(/*int width, int height, int x, int y, */int[] vals) {
        //super(width, height, x, y);
        //TODO: iterate and set each final position, handCards position,
        //table cards position.
        int i;
        for(i = 0; i < 3 && i < vals.length; i++){
            this.handCards.add(new Card(Constants.CARD_WIDTH,Constants.CARD_HEIGTH,Constants.THREE_CARDS_XY[i][0], Constants.THREE_CARDS_XY[i][1], vals[i]));
        }
        if(vals.length > 3){
            for(i = 3; i < vals.length; i++){
                this.handCards.add(new Card(Constants.CARD_WIDTH,Constants.CARD_HEIGTH,0,0, vals[i]));
            }
        }
    }

    public void updateCardPosition(int index, int newx, int newy){
        this.handCards.get(this.targetCards[index]).setNextXPosition(newx);
        this.handCards.get(this.targetCards[index]).setNextYPosition(newy);
    }
    public void resetCardsPosition() {
        for (int i = 0; i < this.getHandCards().size(); i++) {
            Card card = this.getHandCards().get(i);
            Rect trigger = new Rect(card.getStaticX()-2,card.getStaticY()-2,card.getStaticX()+card.getWidth()+2,card.getStaticY()+card.getHeight());
            if(card.getPositionX()!=card.getStaticX() || card.getPositionY()!=card.getStaticY()){
                if (trigger.intersect(card.getRect().left,card.getRect().top,card.getRect().centerX(),card.getRect().centerY())) {
                    card.setNextXPosition(card.getStaticX());
                    card.setNextYPosition(card.getStaticY());
                }else {
                    double[] vector = Constants.normalize(card.getPositionX() - card.getStaticX(), card.getPositionY() - card.getStaticY());
                    card.setNextXPosition((int) (card.getPositionX() - vector[0] * card.getSpeed()));
                    card.setNextYPosition((int) (card.getPositionY() - vector[1] * card.getSpeed()));
                }
            }
        }
    }

    public void swapHand(int direction){
        //si hay mas cartas a la izquierda o a la derecha mueve
        //if evita retornar nulls
        if(direction+targetCards[0]>=0 && direction+targetCards[2]<handCards.size()){
            for (int i = 0 ; i < this.targetCards.length; i++) {
                targetCards[i] = targetCards[i] + direction;
                this.handCards.get(targetCards[i]).setNextXPosition(Constants.THREE_CARDS_XY[i][0]);
                this.handCards.get(targetCards[i]).setNextYPosition(Constants.THREE_CARDS_XY[i][1]);
                this.handCards.get(targetCards[i]).setStaticX(Constants.THREE_CARDS_XY[i][0]);
                this.handCards.get(targetCards[i]).setStaticY(Constants.THREE_CARDS_XY[i][1]);

            }
        }
    }

    public ArrayList<Card> getHandCards() {
        ArrayList<Card> handVisible = new ArrayList<>();
        for (int i = 0 ; i < this.targetCards.length; i++) {
            handVisible.add(handCards.get(targetCards[i]));
        }
        return handVisible;
    }
    public ArrayList<Card> getAllCards() {
        return handCards;
    }
}
