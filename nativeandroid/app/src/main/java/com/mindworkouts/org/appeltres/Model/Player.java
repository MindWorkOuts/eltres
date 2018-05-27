package com.mindworkouts.org.appeltres.Model;

import android.graphics.Point;
import android.graphics.Rect;

import com.mindworkouts.org.appeltres.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentNavigableMap;

public class Player {//extends Entity{

    private ArrayList<Card> handCards = new ArrayList<Card>();
    private int [] targetCards;
    private int targetCardsSize = 0;
    private boolean isTurn;


    public Player(/*int width, int height, int x, int y, */ArrayList<Integer> vals) {
        //super(width, height, x, y);
        //TODO: iterate and set each final position, handCards position,
        //table cards position.
        this.createHandFromValues(vals);
        isTurn = false;
    }
    public void createHandFromValues(ArrayList<Integer> vals){
        //table cards position.
        targetCardsSize = 0;
        this.handCards.clear();
        int i;
        this.targetCards= new int[Constants.MAX_CARDS_SEEN];
        for(i = 0; i < Constants.MAX_CARDS_SEEN && i < vals.size(); i++){
            targetCards[i]= i;
            this.targetCardsSize+=1;
            this.handCards.add(new Card(Constants.CARD_WIDTH,Constants.CARD_HEIGTH,Constants.HAND_CARDS_XY[i][0], Constants.HAND_CARDS_XY[i][1], vals.get(i)));
        }
        if(vals.size() > Constants.MAX_CARDS_SEEN){
            for(i = Constants.MAX_CARDS_SEEN; i < vals.size(); i++){
                this.handCards.add(new Card(Constants.CARD_WIDTH,Constants.CARD_HEIGTH,Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][0],Constants.CARD_HEIGTH+Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][1], vals.get(i)));
            }
        }
    }
    public void updateCardPosition(int index, int newx, int newy, boolean isFromUI){
        for (int i = 0; i < this.handCards.size(); i++){
            if (i==index){
                this.handCards.get(this.targetCards[index]).setFocus(true);
                this.handCards.get(this.targetCards[index]).setNextXPosition(newx);
                this.handCards.get(this.targetCards[index]).setNextYPosition(newy);
                this.updateMatrix(this.targetCards[index]);
            }
            this.handCards.get(this.targetCards[index]).setFocus(false);
        }
    }
    public boolean isCardInHeapRange(int index){return Constants.HEAP_RECT.contains(this.getHandCards().get(index).getCenterPoint()[0],getHandCards().get(index).getCenterPoint()[1]);}
    public void setTurn(boolean is){isTurn=is;}
    public boolean isTurn(){return isTurn;}
    public void removeHand(){this.handCards.clear();}
    public void addCard(int value){
        if(handCards.size() < Constants.MAX_CARDS_SEEN){
            Card card = new Card(Constants.CARD_WIDTH,Constants.CARD_HEIGTH,Constants.HAND_CARDS_XY[handCards.size()][0], Constants.HAND_CARDS_XY[handCards.size()][1], value);
            targetCards[targetCardsSize] = handCards.size();
            targetCardsSize+=1;
            handCards.add(card);
        }else{
            Card card = new Card(Constants.CARD_WIDTH,Constants.CARD_HEIGTH,Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][0],Constants.CARD_HEIGTH+Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][1], value);
            handCards.add(card);
        }
    }/*
    public void sortHand(){
        ArrayList<Integer> sortedValues = new ArrayList<Integer>();
        for (Card card : handCards) {
            sortedValues.add(card.getValue());
        }
        Collections.sort(sortedValues);
        for (int i = 0; i < handCards.size(); i++){
            handCards.get(i).setValue(sortedValues.get(i));
        }
    }*/
    public void addCard(Card card){
        if(handCards.size() < Constants.MAX_CARDS_SEEN){
            targetCards[targetCardsSize] = handCards.size();
            targetCardsSize+=1;
            handCards.add(card);
        }else{
            handCards.add(card);
        }
    }
    public void removeCard(int index){
        this.handCards.remove(targetCards[index]);
    }
    /*
      scrollea la mano
    * */

    public void scrollHand(int direction){
        //si hay mas cartas a la izquierda o a la derecha mueve
        //if evita retornar nulls
        int max = Math.min(handCards.size(), this.targetCardsSize-1);

        if(direction+targetCards[0]>=0 && direction+targetCards[max]<handCards.size()){
            if(direction==1){
                this.handCards.get(targetCards[0]).setNextXPosition(Constants.HAND_CARDS_XY[0][0]);
                this.handCards.get(targetCards[0]).setNextYPosition(Constants.HAND_CARDS_XY[0][1]+this.handCards.get(targetCards[0]).getHeight());
                updateMatrix(targetCards[0]);
            }
            else if (direction==0){
                this.handCards.get(targetCards[targetCardsSize-1]).setNextXPosition(Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][0]);
                this.handCards.get(targetCards[targetCardsSize-1]).setNextYPosition(Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][1]+this.handCards.get(targetCards[targetCardsSize-1]).getHeight());
                updateMatrix(targetCards[targetCardsSize-1]);
            }

            for (int i = 0 ; i < targetCardsSize; i++) {
                targetCards[i] = targetCards[i] + direction;
                this.handCards.get(targetCards[i]).setStaticX(Constants.HAND_CARDS_XY[i][0]);
                this.handCards.get(targetCards[i]).setStaticY(Constants.HAND_CARDS_XY[i][1]);
            }
        }
    }
    public void showHand(){
        for (int i = 0; i < getHandCards().size(); i++){
            Card card = getHandCards().get(i);
            card.setStaticY(Constants.HAND_CARDS_XY_SHOWING[i][1]);
        }
    }
    public void hideHand(){
        for (int i = 0; i < getHandCards().size(); i++){
            Card card = getHandCards().get(i);
            card.setStaticY(Constants.HAND_CARDS_XY[i][1]);
        }
    }
    public void updateMatrix(int index){this.handCards.get(index).updateMatrix();}

    public ArrayList<Card> getHandCards() {
        ArrayList<Card> handVisible = new ArrayList<Card>();
        for (int i = 0 ; i < targetCardsSize; i++) {
            handVisible.add(handCards.get(targetCards[i]));
        }
        return handVisible;
    }
    public ArrayList<Card> getAllCards() {
        return handCards;
    }
    public void setHandsInHeap(){
        for (Card card : handCards){
            card.setNextXPosition(Constants.HEAP_RECT.centerX()-card.getWidth()/2);
            card.setNextYPosition(Constants.HEAP_RECT.centerY()-card.getHeight()/2);
        }
        for(Card card: getNotVisibleCards()){
            card.setNextXPosition(Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][0]);
            card.setNextYPosition(Constants.HAND_CARDS_XY[Constants.MAX_CARDS_SEEN-1][1]+Constants.CARD_HEIGTH);
        }
    }
    public ArrayList<Card> getNotVisibleCards() {
        ArrayList<Card> handNotVisible = new ArrayList<Card>();
        handNotVisible.addAll(getAllCards());
        for (int i = 0 ; i < targetCardsSize; i++) {
            handNotVisible.remove(handCards.get(targetCards[i]));
        }
        return handNotVisible ;
    }
}
