package com.mindworkouts.org.appeltres.Controller;

import android.content.Context;
import android.graphics.Rect;

import com.mindworkouts.org.appeltres.Constants;
import com.mindworkouts.org.appeltres.Model.Card;
import com.mindworkouts.org.appeltres.Model.Player;

import java.util.ArrayList;
//TODO: Program responsible for dealing turn and assign it to the player

public class Controller {
    private Player mainPlayer;
    private int touchCardPointer = -1;
    private Context context;
    public Controller(Context context){
        this.context = context;
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
           this.mainPlayer.updateCardPosition(getTouchCardPointer(), x-card.getRect().width()/2, (int) y-card.getRect().height()/2,true);
        }
        else {
            ArrayList<Card> playerHand = mainPlayer.getHandCards();
            for (int i = playerHand.size()-1; i >= 0; i--) {
                Card card = playerHand.get(i);
                if (card.getRect().contains( x, y)) {
                    this.mainPlayer.updateCardPosition(i, (int) x-card.getRect().width()/2, (int) y-card.getRect().height()/2,true);
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
    public ArrayList<Card> getVisiblePlayerHand(){
        return mainPlayer.getHandCards();
    }
    public void scrollHand(int where){this.mainPlayer.scrollHand(where);}
}
