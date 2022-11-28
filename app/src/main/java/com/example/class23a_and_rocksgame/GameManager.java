package com.example.class23a_and_rocksgame;

import java.util.Random;

public class GameManager {

    final int ROWS = 4;
    final int COLS = 3;
    final int Lifes = 3;
    private int wrong = 0;
    private int life;
    public int carPlace;
    public int[][] activeRocks = new int[ROWS][COLS];

    public GameManager() {
        this.life = Lifes;
    }

    public void setCarPlace(int place) {
        this.carPlace = place;
    }
    public int getCarPlace(){
        return this.carPlace;
    }


    public int getWrong() {
        return wrong;
    }

    public int setPlace(boolean answer, int place) {
        if(place == 1){
            if(answer == true)
                setCarPlace(0);
            else setCarPlace(2);
        }
        else if((place == 0 && answer == false) || (place == 2 && answer == true)){
            setCarPlace(1);
        }
        else setCarPlace(place);
        return getCarPlace();
    }

    public int checkAccident(int rockPlace) {
        if(carPlace == rockPlace) {
            wrong++;
            return 1;
        }
        return 0;
    }


    public void getNewRock(){
        activeRocks[0][new Random().nextInt(COLS)] = 1;
    }


    public boolean isLose() {
        return wrong == life;
    }

}
