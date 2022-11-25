package com.example.class23a_and_rocksgame;

public class Artur {
/*
        final int NUM_OF_HEARTS = 3;
        final int NUM_OF_ROWS = 4;
        final int NUM_OF_COLUMNS = 3;
        final int NUM_OF_CARS = 3;
        final int NUM_OF_BTNS = 2;
        final int DELAY = 1000;
        final String STRING_LOST_1_LIFE = "You lost 1 life";
        final String STRING_GAME_OVER = "Game Over";

        private ArrayList<AppCompatImageView> game_IMG_Hearts;
        private ArrayList<ArrayList<AppCompatImageView>> game_IMG_Bombs;
        private ArrayList<AppCompatImageView> game_IMG_Cars;
        private ArrayList<ExtendedFloatingActionButton> game_BTNS;
        private ArrayList<ArrayList<Boolean>> activeBombs;


        private Timer timer;
        private int currentColumnCar;
        private int indexRemoveHeart;

        GameManager gameManager;

        private void clickedRight() {
            int nextColumnCar = currentColumnCar + 1;
            if (nextColumnCar < 3) {
                game_IMG_Cars.get(currentColumnCar).setVisibility(View.INVISIBLE);
                game_IMG_Cars.get(nextColumnCar).setVisibility(View.VISIBLE);
                currentColumnCar = nextColumnCar;
            }
        }

        private void clickedLeft() {
            int nextColumnCar = currentColumnCar - 1;
            if (nextColumnCar >= 0) {
                game_IMG_Cars.get(currentColumnCar).setVisibility(View.INVISIBLE);
                game_IMG_Cars.get(nextColumnCar).setVisibility(View.VISIBLE);
                currentColumnCar = nextColumnCar;
            }
        }

        private void initBTNS() {
            game_BTNS.get(0).setOnClickListener(v -> {
                clickedLeft();
            });
            game_BTNS.get(1).setOnClickListener(v -> {
                clickedRight();

            });
        }


        private void initCars() {
            currentColumnCar = new Random().nextInt(NUM_OF_CARS);


            for (int indexCar = 0; indexCar < NUM_OF_CARS; indexCar++) {
                if (indexCar == currentColumnCar) {
                    game_IMG_Cars.get(indexCar).setVisibility(View.VISIBLE);
                } else {
                    game_IMG_Cars.get(indexCar).setVisibility(View.INVISIBLE);
                }
            }
        }

        private void tableBombSetVisibility() {
            for (int row = 0; row < NUM_OF_ROWS; row++) {
                for (int column = 0; column < NUM_OF_COLUMNS; column++) {
                    boolean isBombActive = activeBombs.get(row).get(column);
                    if (isBombActive) {
                        game_IMG_Bombs.get(row).get(column).setVisibility(View.VISIBLE);
                    } else {
                        game_IMG_Bombs.get(row).get(column).setVisibility(View.INVISIBLE);
                    }
                }
            }
        }


        private void initBombs() {
            activeBombs = new ArrayList<>();
            for (int indexRow = 0; indexRow < NUM_OF_ROWS; indexRow++) {
                activeBombs.add(new ArrayList<>());
                int randomColumn = new Random().nextInt(NUM_OF_COLUMNS);
                for (int indexColumn = 0; indexColumn < NUM_OF_COLUMNS; indexColumn++) {
                    if (randomColumn == indexColumn) {
                        activeBombs.get(indexRow).add(indexColumn, true);
                    } else {
                        activeBombs.get(indexRow).add(indexColumn, false);
                    }
                }
            }
            tableBombSetVisibility();
        }

        private int getRandom(int boundary) {
            return new Random().nextInt(boundary);
        }


        private Boolean isBOOM(int currentColumnBomb) {
            return currentColumnBomb == currentColumnCar;
        }

        private void showToast(String string) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, string, duration);
            toast.show();
        }

        private void decreaseLife() {
            game_IMG_Hearts.get(indexRemoveHeart).setVisibility(View.INVISIBLE);
            indexRemoveHeart++;
        }

        private boolean isGameOver() {
            return indexRemoveHeart == NUM_OF_HEARTS;
        }


        private void updateBombs() {
            ArrayList<ArrayList<Boolean>> tempActiveBombs = new ArrayList<>();
            for (int i = 0; i < NUM_OF_ROWS; i++) {
                tempActiveBombs.add(new ArrayList<>());
                for (int j = 0; j < NUM_OF_COLUMNS; j++) {
                    tempActiveBombs.get(i).add(j, false);
                }
            }


            for (int indexRow = 0; indexRow < NUM_OF_ROWS; indexRow++) {
                for (int indexColumn = 0; indexColumn < NUM_OF_COLUMNS; indexColumn++) {
                    boolean isActiveBomb = activeBombs.get(indexRow).get(indexColumn);
                    if (isActiveBomb) {
                        if (indexRow + 1 == NUM_OF_ROWS) {
                            tempActiveBombs.get(0).set(getRandom(NUM_OF_COLUMNS), true);

                            if (isBOOM(indexColumn)) {
                                decreaseLife();
                                showToast(STRING_LOST_1_LIFE);
                                if (isGameOver()) {
                                    showToast(STRING_GAME_OVER);
                                    stopTimer();
                                }
                            }
                        } else {
                            tempActiveBombs.get(indexRow + 1).set(indexColumn, true);
                        }
                    }
                }
            }

            activeBombs = tempActiveBombs;

            tableBombSetVisibility();
        }


        private void initTimer() {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateBombs();
                        }
                    });
                }
            }, DELAY, DELAY);
        }

        private void stopTimer() {
            timer.cancel();
        }

        private void generalInit() {
            initTimer();
            initBombs();
            initCars();
            initBTNS();
        }

        private void findButtons() {
            game_BTNS = new ArrayList<>(NUM_OF_BTNS);
            for (int buttonIndex = 0; buttonIndex < NUM_OF_BTNS; buttonIndex++) {
                String btn_name = "game_BTN_" + String.valueOf(buttonIndex);
                int btn_ID = getResources().getIdentifier(btn_name, "id", getPackageName());
                ExtendedFloatingActionButton current_BTN = findViewById(btn_ID);
                game_BTNS.add(current_BTN);
            }
        }

        private void findCars() {
            game_IMG_Cars = new ArrayList<>(NUM_OF_CARS);
            for (int carIndex = 0; carIndex < NUM_OF_CARS; carIndex++) {
                String car_name = "game_IMG_car_" + String.valueOf(carIndex);
                int car_ID = getResources().getIdentifier(car_name, "id", getPackageName());
                AppCompatImageView current_Car = findViewById(car_ID);
                game_IMG_Cars.add(current_Car);
            }
        }

        private void findBombs() {
            game_IMG_Bombs = new ArrayList<>(NUM_OF_ROWS);
            for (int rowIndex = 0; rowIndex < NUM_OF_ROWS; rowIndex++) {
                game_IMG_Bombs.add(new ArrayList<>(NUM_OF_COLUMNS));
                for (int columnIndex = 0; columnIndex < NUM_OF_COLUMNS; columnIndex++) {
                    String bomb_name = "game_IMG_bomb_" + String.valueOf(rowIndex) + "_" + String.valueOf(columnIndex);
                    int bomb_ID = getResources().getIdentifier(bomb_name, "id", getPackageName());
                    AppCompatImageView current_Bomb = findViewById(bomb_ID);
                    game_IMG_Bombs.get(rowIndex).add(current_Bomb);
                }
            }
        }

        private void findHearts() {
            game_IMG_Hearts = new ArrayList<>(NUM_OF_HEARTS);
            for (int heartIndex = 0; heartIndex < NUM_OF_HEARTS; heartIndex++) {
                String heart_name = "game_IMG_heart_" + String.valueOf(heartIndex);
                int heart_ID = getResources().getIdentifier(heart_name, "id", getPackageName());
                AppCompatImageView current_Heart = findViewById(heart_ID);
                game_IMG_Hearts.add(current_Heart);
            }
        }

        private void findViews() {
            findHearts();
            findBombs();
            findCars();
            findButtons();
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);
            gameManager = new GameManager();
            findViews();
            generalInit();
        }
    }*/
}
