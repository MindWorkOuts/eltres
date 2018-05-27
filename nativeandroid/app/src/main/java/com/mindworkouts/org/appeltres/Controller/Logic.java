package com.mindworkouts.org.appeltres.Controller;

public class Logic {

    private int[][] forbidden_moves;
    private int[][] indexes = {
            {0,0},//Dorso
            {0,0},//As
            {0,0},//2
            {0,1},//3
            {0,1},//4
            {0,6,7,8,9,11,12,13},//5
            {0,1,4,5},//6
            {0,7,8,9,11,12,13},//7
            {0,1,4,5,6,7},//8
            {0,1,4,5,6,7,8},//9
            {0,0},//10
            {0,0},//11 - J
            {0,1,4,5,6,7,8,9,11},//12- Q
            {0,1,4,5,6,7,8,9,11,12}//13 - K
    };//13
    public Logic(){
        forbidden_moves = new int[14][14];
        int[] idxs;
        int id;
        for(int i = 0; i < 14; i ++){
            for(int j = 0; i < 14; i ++){
                forbidden_moves[i][j]=0;
            }
        }
        for(int i = 0; i < indexes.length; i++){
            idxs = indexes[i];
            for (int j = 0 ; j < idxs.length; j++){
                id = idxs[j];
                forbidden_moves[i][id]=1;
            }
        }
    }
    public int[][] getForbiddenMoves(){
        return forbidden_moves;
    }


}
