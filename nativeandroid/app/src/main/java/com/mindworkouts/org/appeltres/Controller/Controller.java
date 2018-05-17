package com.mindworkouts.org.appeltres.Controller;

import android.graphics.Rect;

import com.mindworkouts.org.appeltres.Constants;
import com.mindworkouts.org.appeltres.Model.Card;
import com.mindworkouts.org.appeltres.Model.Player;

import java.util.ArrayList;

public class Controller {
    private Player mainPlayer;
    private int touchCardPointer = -1;
    public Controller(){
    }
    public void initGame(){
    }
    public void createPlayer(Player player){
        this.mainPlayer = player;
    }

    public int getTouchCardPointer(){return this.touchCardPointer;}
    public void cardTouchReleased(){
        this.touchCardPointer=-1;
        this.mainPlayer.resetCardsPosition();
    }
    public void updateTouch (int x, int y){

       if(getTouchCardPointer()!=-1){
           Card card = this.mainPlayer.getHandCards().get(getTouchCardPointer());
            this.mainPlayer.updateCardPosition(getTouchCardPointer(), x-card.getRect().width()/2, (int) y-card.getRect().height()/2);
        }
        else {
            ArrayList<Card> playerHand = mainPlayer.getHandCards();
            for (int i = playerHand.size()-1; i >= 0; i--) {
                Card card = playerHand.get(i);
                if (card.getRect().contains( x, y)) {
                    this.mainPlayer.updateCardPosition(i, (int) x-card.getRect().width()/2, (int) y-card.getRect().height()/2);
                    this.touchCardPointer = i;
                }
                else{
                    this.mainPlayer.resetCardsPosition();
                    //this.mainPlayer.updateCardPosition(i,card.getStaticX(),card.getStaticY());
                }

                }

        }
        //   }


    }
    public ArrayList<Card> getPlayerHand(){
        return mainPlayer.getHandCards();
    }
    public void swapHand(int where){this.mainPlayer.swapHand(where);}
}
