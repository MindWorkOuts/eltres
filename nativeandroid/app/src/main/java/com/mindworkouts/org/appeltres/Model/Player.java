package com.mindworkouts.org.appeltres.Model;

import com.mindworkouts.org.appeltres.Constants;

import java.util.ArrayList;

public class Player {//extends Entity{

    private ArrayList<Card> handCards = new ArrayList<Card>();


    public Player(/*int width, int height, int x, int y, */int[] vals) {
        //super(width, height, x, y);
        //TODO: iterate and set each final position, handCards position,
        //table cards position.
        int i = 0;
        //for(i = 0; i < vals.length; i++){
            handCards.add(new Card(192,250,Constants.SCREEN_WIDTH_TOTAL/2, Constants.SCREEN_HEIGTH_TOTAL/2, vals[0]));
        //}

    }

    public void updateCardPosition(int index, int newx, int newy){
        this.handCards.get(index).setNextXPosition(newx);
        this.handCards.get(index).setNextYPosition(newy);
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }
}
