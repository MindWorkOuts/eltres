package com.mindworkouts.org.appeltres.Model;

import android.graphics.Point;
import android.graphics.Rect;

import com.mindworkouts.org.appeltres.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {//extends Entity{

    private ArrayList<Card> handCards = new ArrayList<Card>();
    private int [] targetCards;
    private int targetCardsSize = 0;


    public Player(/*int width, int height, int x, int y, */int[] vals) {
        //super(width, height, x, y);
        //TODO: iterate and set each final position, handCards position,
        //table cards position.
        int i;
        this.targetCards= new int[5];
        for(i = 0; i < Constants.MAX_CARDS_SEEN && i < vals.length; i++){
            targetCards[i]= i;
            this.targetCardsSize+=1;
            this.handCards.add(new Card(Constants.CARD_WIDTH,Constants.CARD_HEIGTH,Constants.HAND_CARDS_XY[i][0], Constants.HAND_CARDS_XY[i][1], vals[i]));
        }
        if(vals.length > Constants.MAX_CARDS_SEEN){
            for(i = Constants.MAX_CARDS_SEEN; i < vals.length; i++){
                this.handCards.add(new Card(Constants.CARD_WIDTH,Constants.CARD_HEIGTH,Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][0],Constants.CARD_HEIGTH+Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][1], vals[i]));
            }
        }
    }

    public void updateCardPosition(int index, int newx, int newy, boolean isFromUI){
        for (int i = 0; i < this.handCards.size(); i++){
            if (i==index){
                this.handCards.get(this.targetCards[index]).setFocus(true);
                this.handCards.get(this.targetCards[index]).setNextXPosition(newx);
                this.handCards.get(this.targetCards[index]).setNextYPosition(newy);
            }
            this.handCards.get(this.targetCards[index]).setFocus(false);
        }
    }
    public void resetCardsPosition() {
        for (int i = 0; i < this.getHandCards().size(); i++) {
            Card card = this.getHandCards().get(i);
            int triggerEpsilon = 10;
            if(!card.isFocusing()){
                if(card.getPositionX()!=card.getStaticX() || card.getPositionY()!=card.getStaticY()) {
                    Rect trigger = new Rect(card.getStaticX() - card.getWidth() / triggerEpsilon, card.getStaticY() - card.getHeight() / triggerEpsilon, card.getStaticX() + card.getWidth() / triggerEpsilon, card.getStaticY() + card.getHeight() / triggerEpsilon);
                    if (trigger.intersect(card.getPositionX() - card.getWidth() / triggerEpsilon, card.getPositionY() - card.getHeight() / triggerEpsilon, card.getPositionX() + card.getWidth() / triggerEpsilon, card.getPositionY() + card.getHeight() / triggerEpsilon)) {
                        card.setNextXPosition(card.getStaticX());
                        card.setNextYPosition(card.getStaticY());
                    } else {
                        double[] vector = Constants.normalize(card.getPositionX() - card.getStaticX(), card.getPositionY() - card.getStaticY());
                        card.setNextXPosition((int) (card.getPositionX() - vector[0] * card.getSpeed()));
                        card.setNextYPosition((int) (card.getPositionY() - vector[1] * card.getSpeed()));
                    }
                }
            }
        }
    }
    /*
      scrollea la mano
    * */

    public void scrollHand(int direction){
        //si hay mas cartas a la izquierda o a la derecha mueve
        //if evita retornar nulls
        int max = Math.min(handCards.size(), this.targetCardsSize-1);

        if(direction+targetCards[0]>=0 && direction+targetCards[max]<handCards.size()){
            //scrolling izquierda, last izq hide
            if(direction==1){
                this.handCards.get(targetCards[0]).setNextXPosition(Constants.HAND_CARDS_XY[0][0]);
                this.handCards.get(targetCards[0]).setNextYPosition(Constants.HAND_CARDS_XY[0][1]+this.handCards.get(targetCards[0]).getHeight());
            }
            else if (direction==-1){
                this.handCards.get(targetCards[targetCardsSize-1]).setNextXPosition(Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][0]);
                this.handCards.get(targetCards[targetCardsSize-1]).setNextYPosition(Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][1]+this.handCards.get(targetCards[targetCardsSize-1]).getHeight());
            }

            for (int i = 0 ; i < targetCardsSize; i++) {
                targetCards[i] = targetCards[i] + direction;
                this.handCards.get(targetCards[i]).setStaticX(Constants.HAND_CARDS_XY[i][0]);
                this.handCards.get(targetCards[i]).setStaticY(Constants.HAND_CARDS_XY[i][1]);
            }
        }
    }

    public ArrayList<Card> getHandCards() {
        ArrayList<Card> handVisible = new ArrayList<>();
        for (int i = 0 ; i < targetCardsSize; i++) {
            handVisible.add(handCards.get(targetCards[i]));
        }
        return handVisible;
    }
    public ArrayList<Card> getAllCards() {
        return handCards;
    }
}
