package com.mindworkouts.org.appeltres.Controller;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.mindworkouts.org.appeltres.Constants;
import com.mindworkouts.org.appeltres.Model.Card;
import com.mindworkouts.org.appeltres.Model.Player;
import com.mindworkouts.org.appeltres.View.Render;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
//TODO: Program responsible for dealing turn and assign it to the player

public class Controller {
    private Player mainPlayer;
    private int touchCardPointer = -1;
    private Context context;
    private Render render;
    private Logic logic;
    private boolean showingHand;
    private Network net;
    private ArrayList<Card> heap;
    private ArrayList<Card> leftTableCards;
    private ArrayList<Card> rightTableCards;
    private int [] tableValues;//leftoright
    private ArrayList<Card> drawingCards;
    private ArrayList<Player> secondaryPlayers;
    private boolean playedCard;
    private int estadoAnterior;
    public Controller(Context context, Render render){
        this.context = context;
        this.logic = new Logic();
        this.render=render;
        showingHand = false;
        this.tableValues = new int[]{3,2,9,6,11,12};
        createPlayer();
        initDrawingCards();
        //wait network()
        //refreshStatus
        //updateGame
        setTurn(true);
        playedCard = false;
    }
    private void initDrawingCards(){
        drawingCards = new ArrayList<Card>();
        int [] degrees = {355,0,5};
        Card card;
        for (int i = 0; i < 3; i++){
            card = new Card(Constants.CARD_TABLE_WIDTH,Constants.CARD_TABLE_HEIGTH,Constants.DRAW_CARDS_X,Constants.DRAW_CARDS_Y, 0);
            card.setDegrees(degrees[i]);
            card.updateMatrix();
            this.drawingCards.add(card);
        }
        leftTableCards = new ArrayList<Card>();
        int [] translations = {0};
        for (int i = 0 ; i < 3 ; i ++){
            card = new Card(Constants.CARD_FINAL_WIDTH,Constants.CARD_FINAL_HEIGTH,Constants.tableCards[1][i].x,Constants.tableCards[1][i].y, this.tableValues[i]);
            card.setDegrees(270);;
            card.updateMatrix();
            this.leftTableCards.add(card);
        }
        secondaryPlayers.add(new Player(leftTableCards,1));
        rightTableCards = new ArrayList<Card>();
        for (int i = 0 ; i < 3 ; i ++){
            card = new Card(Constants.CARD_FINAL_WIDTH,Constants.CARD_FINAL_HEIGTH,Constants.tableCards[2][i].x,Constants.tableCards[2][i].y, this.tableValues[i+3]);
            card.setDegrees(270);
            card.updateMatrix();
            this.rightTableCards.add(card);
        }
        secondaryPlayers.add(new Player(rightTableCards,2));
    }

    public ArrayList<Card> getTableCards() {
        ArrayList<Card> table = new ArrayList<Card>();
        table.addAll(this.leftTableCards);
        table.addAll(this.rightTableCards);
        table.addAll(this.mainPlayer.getTableCards());
        return table;
    }

    public ArrayList<Card> getDrawingCards(){return this.drawingCards;}
    public void setTurn(boolean isTurn){
        mainPlayer.setTurn(isTurn);
    }
    public void updateGame(){
        //update heap check power of last card added
        if(playedCard) {
            if(!heap.isEmpty()) {
                int lastValue = heap.get(heap.size() - 1).getValue();
                switch (lastValue) {
                    case 10:
                        heap.clear();
                    break;
                    default:
                        playedCard=false;
                    break;
                }
            }
        }
        if(!markPlayables()){
            playerEatHeap();
        }
        //sortHand();
        mainPlayer.setTurn(true);
    }

    /**
     * TODO: PLAYCARDHARD
     * @param index
     * @return
     */
    public Card playCard(int index){
        Card card = mainPlayer.getHandCards().get(index);
        mainPlayer.removeCard(index);
        card.setHeight(Constants.CARD_TABLE_HEIGTH);
        card.setWidth(Constants.CARD_TABLE_WIDTH);
        card.setStaticX(Constants.HEAP_CARDS_X);
        card.setStaticY(Constants.HEAP_CARDS_Y);
        card.setDegrees(Constants.HEAP_DEGREES);
        if( Constants.HEAP_DEGREES==Constants.HEAP_MAX_DEGREES)
            Constants.HEAP_DEGREES_DIRECTION = -1;
        else if (Constants.HEAP_DEGREES==Constants.HEAP_MIN_DEGREES)
            Constants.HEAP_DEGREES_DIRECTION = +1;
        Constants.HEAP_DEGREES = Constants.HEAP_DEGREES + 10*Constants.HEAP_DEGREES_DIRECTION;
        ArrayList<Integer> sortedValues= new ArrayList<Integer>();
        for(Card cardx: mainPlayer.getAllCards()) {
            sortedValues.add(cardx.getValue());
        }
        mainPlayer.removeHand();
        Collections.sort(sortedValues);
        mainPlayer.createHandFromValues(sortedValues);
        return card;
    }

    public void playerEatHeap(){
      //TODO: NOT WORKING ADALL
        //  mainPlayer.addAll(heap);
       // heap.clear();
        ArrayList<Card> allHand =getAllPlayerHand();
        ArrayList<Integer> sortedValues = new ArrayList<Integer>();
        allHand.addAll(this.heap);
        for(Card card : allHand) {
            sortedValues.add(card.getValue());
        }
        heap.clear();
        mainPlayer.removeHand();
        Collections.sort(sortedValues);
        mainPlayer.createHandFromValues(sortedValues);
        mainPlayer.setHandsInHeap();
        //TODO: mainPlayer.refreshStaticsPos();
    }
    public ArrayList<Card> getPlayables(){
        ArrayList<Card> playables = new ArrayList<Card>();
        for(Card card : mainPlayer.getHandCards()){
            if (cardPlayable(card.getValue())) {
                playables.add(card);
            }
        }
        return  playables;
    }
    public boolean markPlayables(){
        boolean canPlay = false;    ArrayList<Card> playables = new ArrayList<Card>();
        for(Card card : mainPlayer.getHandCards()) {
            if (cardPlayable(card.getValue())) {
                card.showPlayable(true);
                canPlay = true;
            }
            else
                card.showPlayable(false);
        }
        return canPlay;
    }
    public boolean cardPlayable(int value){
        if(heap.isEmpty())return true;
        int finalValue = heap.get(heap.size()-1).getValue();
        switch(heap.get(heap.size()-1).getValue()){
            case 3:
                if(heap.size()>1)
                    finalValue = heap.get(heap.size()-2).getValue();
                break;
        }
        return logic.getForbiddenMoves()[finalValue][value]==0;
    }
    public void createPlayer(){
        ArrayList<Integer> values = new ArrayList<Integer>();
        ArrayList<Integer> tableVals= new ArrayList<Integer>();
        int [] vals = {1,4,3,6,8,10,11,12,13};
        for(int i = 0 ; i < vals.length; i++){values.add(vals[i]);}
        vals= new int[]{3,4,10};
        for(int i = 0 ; i < vals.length; i++){tableVals.add(vals[i]);}
        this.mainPlayer = new Player(values,tableVals);
        this.secondaryPlayers = new ArrayList<Player>();
        heap = new ArrayList<Card>();
    }
    public void setRender(Render render){
        this.render= render;
    }
    public void initMatrixs(){
        this.initDrawingCards();
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
    public ArrayList<Card> getHeap() {
        return heap;
    }
    public int getTouchCardPointer(){return this.touchCardPointer;}
    public void sortHand(){
        mainPlayer.sortHand();
    }
    public void drawCard(){
        if(mainPlayer.getAllCards().size()<3){
            mainPlayer.addCard(1+new Random().nextInt(13));
            Card card = mainPlayer.getAllCards().get(mainPlayer.getAllCards().size()-1);
            card.setNextXPosition(Constants.DRAW_CARDS_X);
            card.setNextYPosition(Constants.DRAW_CARDS_Y);
        }
    }
    public void cardTouchReleased(){
        if(touchCardPointer!=-1) {
            Card card = mainPlayer.getAllCards().get(touchCardPointer);
            if (this.cardPlayable(card.getValue())) {;
                if (mainPlayer.isCardInHeapRange(touchCardPointer) && mainPlayer.isTurn()) {
                    addCardToHeap(this.playCard(touchCardPointer));
                    drawCard();
                }
            }
        }
        this.touchCardPointer=-1;
        updateGame();
        resetCardsPosition();
    }
    public void addCardToHeap(Card card){
        this.heap.add(card);
        this.playedCard = true;

    }
    public void resetCardsPosition() {
        Constants.ACTUAL_CARD_SPEED = Math.min(Constants.ACTUAL_CARD_SPEED + 4, Constants.FINAL_CARD_SPEED);
        this.updateGame();
        ArrayList<Card> allGameCards = this.mainPlayer.getHandCards();
        allGameCards.addAll(heap);
        for (int i = 0; i < allGameCards.size(); i++) {
            Card card = allGameCards.get(i);
            int triggerEpsilon = 10;
            if(getTouchCardPointer()!=i){
                if(card.getPositionX()!=card.getStaticX() || card.getPositionY()!=card.getStaticY()) {
                    Rect trigger = new Rect(card.getStaticX() - card.getWidth() / triggerEpsilon, card.getStaticY() - card.getHeight() / triggerEpsilon, card.getStaticX() + card.getWidth() / triggerEpsilon, card.getStaticY() + card.getHeight() / triggerEpsilon);
                    if (trigger.intersect(card.getPositionX() - card.getWidth() / triggerEpsilon, card.getPositionY() - card.getHeight() / triggerEpsilon, card.getPositionX() + card.getWidth() / triggerEpsilon, card.getPositionY() + card.getHeight() / triggerEpsilon)) {
                        card.setNextXPosition(card.getStaticX());
                        card.setNextYPosition(card.getStaticY());
                 //       card.setValue(card.getStaticValue());
                        card.updateMatrix();
                    } else {
                        double[] d = Constants.normalize(card.getPositionX() - card.getStaticX(), card.getPositionY() - card.getStaticY());
                        //distance / velocidad inicial **2 = 2*acelarcion
                        card.setNextXPosition((int) (card.getPositionX() - d[0] * Constants.ACTUAL_CARD_SPEED));
                        card.setNextYPosition((int) (card.getPositionY() - d[1] * Constants.ACTUAL_CARD_SPEED));
                        card.updateMatrix();
                    }
                }
            }
        }
    }
    public void updateTouch (int x, int y){
        if(getTouchCardPointer()!=-1){
           Constants.ACTUAL_CARD_SPEED = Constants.INITIAL_CARD_SPEED;
            Card card = this.mainPlayer.getHandCards().get(getTouchCardPointer());
           this.mainPlayer.updateCardPosition(getTouchCardPointer(), x-card.getRect().width()/2, (int) y-card.getRect().height()/2,true);
           card.updateMatrix();
        }
        else {
            ArrayList<Card> playerHand = mainPlayer.getHandCards();
            int cardIdx = -1;
            Card card;
            for (int i = 0; i < playerHand.size(); i++) {
                card = playerHand.get(i);
                if (card.getRect().contains( x, y)) {
                    cardIdx = i;
                }
            }
            if(cardIdx!=-1){
                card = mainPlayer.getHandCards().get(cardIdx);
                this.mainPlayer.updateCardPosition(cardIdx, (int) x-card.getRect().width()/2, (int) y-card.getRect().height()/2,true);
                this.touchCardPointer = cardIdx;


            }
        }
        resetCardsPosition();
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
    public boolean isShowingHand(){return showingHand;}
}
