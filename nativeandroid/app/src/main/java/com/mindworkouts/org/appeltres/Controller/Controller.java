package com.mindworkouts.org.appeltres.Controller;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.mindworkouts.org.appeltres.Constants;
import com.mindworkouts.org.appeltres.Model.Card;
import com.mindworkouts.org.appeltres.Model.Player;
import com.mindworkouts.org.appeltres.View.Render;

import java.util.ArrayList;
//TODO: Program responsible for dealing turn and assign it to the player

public class Controller {
    private Player mainPlayer;
    private int touchCardPointer = -1;
    private Context context;
    private Render render;
    private boolean showingHand;
    public Controller(Context context){
        this.context = context;
        render=null;
        showingHand = false;
    }
    public void initGame(){
    }
    public void createPlayer(){
        int [] values = {7,2,1,4,6,12,8};
        this.mainPlayer = new Player(values);
        initMatrixs();
    }
    public void setRender(Render render){
        this.render= render;
    }
    public void initMatrixs(){
        ArrayList <Card> playerHand = getVisiblePlayerHand();
        int degrees [] = {340,350,0,10,20};
        Constants.HAND_CARD_MATRIX = new Matrix[Constants.MAX_CARDS_SEEN];
        Constants.HAND_CARD_MATRIX_UNSEEN = new Matrix[2];
        Constants.HAND_CARD_MATRIX_SHOWING = new Matrix[Constants.MAX_CARDS_SEEN];
        Matrix matrix, matrix2;
        Card card;
        for (int i = 0; i < Constants.MAX_CARDS_SEEN && i < playerHand.size(); i++){
            card = playerHand.get(i);
            int positionX = card.getPositionX();
            int positionY = card.getPositionY();
            int bWidth = Constants.BITMAP_CARDS_SIZE[card.getValue()][0];
            int bHeight = Constants.BITMAP_CARDS_SIZE[card.getValue()][1];
            float scaleWidth = ((float) card.getWidth()) / bWidth;
            float scaleHeight = ((float) card.getHeight()) / bHeight;
            matrix = new Matrix();
            matrix2 = new Matrix();
            matrix.setScale(scaleWidth, scaleHeight);
            matrix.postTranslate(positionX, positionY);
            matrix2.setScale(scaleWidth, scaleHeight);
            matrix2.postTranslate(positionX, positionY);
            if (degrees[i] == degrees[0]) {
                matrix.postTranslate(0, -((float) Math.sin((double) degrees[i] * Math.PI / 180) * 0.8f * card.getWidth()));
                matrix2.postTranslate(0, -((float) Math.sin((double) degrees[i] * Math.PI / 180) * 0.8f * card.getWidth()));
            } else if (degrees[i] == degrees[1]) {
                matrix.postTranslate(0, -(float) Math.sin((double) degrees[i] * Math.PI / 180) * 0.9f * card.getWidth() / 2);
                matrix2.postTranslate(0, -(float) Math.sin((double) degrees[i] * Math.PI / 180) * 0.9f * card.getWidth() / 2);
            }
            matrix.preRotate(degrees[i]);
            matrix2.preRotate(degrees[i]);
            card.setCardMatrix(matrix);
            Constants.HAND_CARD_MATRIX[i] = matrix;
            matrix2.postTranslate(0,-Constants.CARD_HEIGTH/4);
            Constants.HAND_CARD_MATRIX_SHOWING[i] = matrix2;
         }
        for (int i = playerHand.size(); i < Constants.MAX_CARDS_SEEN; i++){
            int positionX = Constants.HAND_CARDS_XY[i][0];
            int positionY = Constants.HAND_CARDS_XY[i][1];
            int bWidth = Constants.BITMAP_CARDS_SIZE[1][0];
            int bHeight = Constants.BITMAP_CARDS_SIZE[1][1];
            float scaleWidth = ((float) Constants.CARD_WIDTH) / bWidth;
            float scaleHeight = ((float) Constants.CARD_HEIGTH) / bHeight;
            matrix = new Matrix();
            matrix.setScale(scaleWidth, scaleHeight);
            matrix.postTranslate(positionX, positionY);
            matrix.preRotate(degrees[i]);
            Constants.HAND_CARD_MATRIX[i] = matrix;
            matrix2 = new Matrix();
            matrix2.setScale(scaleWidth, scaleHeight);
            matrix2.postTranslate(positionX, positionY);
            matrix2.preRotate(degrees[i]);
            matrix2.postTranslate(0,-Constants.CARD_HEIGTH/4);
            Constants.HAND_CARD_MATRIX_SHOWING[i] = matrix2;

        }
        for (int i = 0; i < 2 ; i++){
            int positionX = Constants.HAND_CARDS_XY_UNSEEN[i][0];
            int positionY = Constants.HAND_CARDS_XY_UNSEEN[i][0];
            int bWidth = Constants.BITMAP_CARDS_SIZE[1][0];
            int bHeight = Constants.BITMAP_CARDS_SIZE[1][1];
            float scaleWidth = ((float) Constants.CARD_WIDTH/ bWidth);
            float scaleHeight = ((float) Constants.CARD_HEIGTH/ bHeight);
            matrix = new Matrix();
            matrix.setScale(scaleWidth, scaleHeight);
            matrix.postTranslate(positionX, positionY);
            if (i == 0) {
                matrix.postTranslate(0, -((float) Math.sin((double) degrees[i] * Math.PI / 180) * 0.8f * Constants.CARD_WIDTH));
                matrix.preRotate(340);
            }else
                matrix.preRotate(20);
            Constants.HAND_CARD_MATRIX_UNSEEN[i]=matrix;
        }
        playerHand = getNotVisibleHand();
        for (int i = 0; i < playerHand.size() ; i++) {
            card = playerHand.get(i);
            card.setCardMatrix(Constants.HAND_CARD_MATRIX_UNSEEN[1]);
        }
    }
    public void resetMatrixs(){
        ArrayList <Card> playerHand = getVisiblePlayerHand();
        for (int i = 0; i < Constants.MAX_CARDS_SEEN && i < playerHand.size(); i++){
            Card card = playerHand.get(i);
            if((!showingHand) && (card.getPositionX() == Constants.HAND_CARDS_XY[i][0] && card.getPositionY() == Constants.HAND_CARDS_XY[i][1])) {
                    card.setCardMatrix(Constants.HAND_CARD_MATRIX[i]);
            }
            else if((showingHand )&& (card.getPositionX() == Constants.HAND_CARDS_XY_SHOWING[i][0] && card.getPositionY() == Constants.HAND_CARDS_XY_SHOWING[i][1])){
                card.setCardMatrix(Constants.HAND_CARD_MATRIX_SHOWING[i]);
            }
            else
                card.updateMatrix();
        }
        playerHand = getNotVisibleHand();
        for(int i = 0; i < playerHand.size(); i++){
            Card card = playerHand.get(i);
            if(card.getPositionX() == Constants.HAND_CARDS_XY_UNSEEN[0][0] && card.getPositionY() == Constants.HAND_CARDS_XY_UNSEEN[0][1]){
                card.setCardMatrix(Constants.HAND_CARD_MATRIX_UNSEEN[0]);
            }
            else if(card.getPositionX() == Constants.HAND_CARDS_XY_UNSEEN[1][0] && card.getPositionY() == Constants.HAND_CARDS_XY_UNSEEN[1][1]){
                card.setCardMatrix(Constants.HAND_CARD_MATRIX_UNSEEN[1]);
            }
            else
                card.updateMatrix();
        }
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
           card.updateMatrix();
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
    }
    public ArrayList<Card> getVisiblePlayerHand(){
        return mainPlayer.getHandCards();
    }
    public ArrayList<Card> getAllPlayerHand(){
        return mainPlayer.getAllCards();
    }
    public ArrayList<Card> getNotVisibleHand(){
        return mainPlayer.getNotVisibleCards();
    }
    public void scrollHand(int where){
        this.mainPlayer.scrollHand(where);
    }
    public void showHand(){
        this.showingHand=true;
        this.mainPlayer.showHand();
    }
    public void hideHand(){
        this.showingHand=false;
        this.mainPlayer.hideHand();
    }
}
