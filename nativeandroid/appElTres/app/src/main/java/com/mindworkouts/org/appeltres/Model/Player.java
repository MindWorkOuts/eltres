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
            handCards.add(new Card(Constants.CARD_WIDTH,Constants.CARD_HEIGTH,Constants.THREE_CARDS_XY[1][0], Constants.THREE_CARDS_XY[1][1], vals[0]));
        //}

    }

    public void updateCardPosition(int index, int newx, int newy){
        this.handCards.get(index).setNextXPosition(newx);
        this.handCards.get(index).setNextYPosition(newy);
    }
    public void resetCardsPosition() {
        for (int i = 0; i < this.getHandCards().size(); i++) {
            Card card = this.getHandCards().get(i);
            if(card.getPositionX()!=card.getStaticX() && card.getPositionY()!=card.getStaticY()){
                if ((card.getPositionX() % card.getStaticX() < 100 && card.getPositionY() % card.getStaticY() < 100)||
                    (card.getStaticX() % card.getPositionX()  < 100 && card.getStaticY() % card.getPositionY() < 100)) {
                    card.setNextXPosition(card.getStaticX());
                    card.setNextYPosition(card.getStaticY());
                }
                double[] vector = Constants.normalize(card.getPositionX() - card.getStaticX(), card.getPositionY() - card.getStaticY());
                card.setNextXPosition((int) (card.getPositionX() - vector[0] * card.getSpeed()));
                card.setNextYPosition((int) (card.getPositionY() - vector[1] * card.getSpeed()));
            }
        }
    }

    public ArrayList<Card> getHandCards() {
        return handCards;
    }
}
