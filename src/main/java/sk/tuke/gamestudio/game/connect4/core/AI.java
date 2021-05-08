package sk.tuke.gamestudio.game.connect4.core;

import java.util.Arrays;
import java.util.Random;

public class AI {
    private final int EMPTY = 0;
    private final int PLAYER_PIECE = 1;
    private final int AI_PIECE = 2;

    private final Random random = new Random();
    private Playfield realPlayfield;
    private Playfield experimentalPlayfield;
    private int[][] experimentalPlayfieldInt;
    private int[][] scorePlayfield;
    private final Color AIColor;
    private final int rows;
    private final int cols;

    public AI(Playfield playfield) {
        this.realPlayfield = playfield;
        AIColor = Color.YELLOW;
        scorePlayfield = new int[realPlayfield.getHeight()][realPlayfield.getWidth()];
        rows = playfield.getHeight();
        cols = playfield.getWidth();
    }

    public boolean addStone() {
        int move;
//        makeIntPlayfield(realPlayfield);
        move = pickBestMove();
//        move = random.nextInt(7);
        return realPlayfield.addStone(move, AIColor);
    }

    private int pickBestMove() {
        int bestCol;
        int bestScore = 0;
        do {
            bestCol = random.nextInt(7);
        } while (getEmptyTile(realPlayfield, bestCol) == -1);

        for (int col = 0; col < cols; col++) {
            experimentalPlayfield = copyRealPfToExPf();
            if (!experimentalPlayfield.addStone(col, AIColor)) continue;

            var score = scorePosition(experimentalPlayfield, AIColor);
            if (col == 3) score += 3;
            if (score > bestScore) {
                bestScore = score;
                bestCol = col;
            }
        }
        return bestCol;
    }

    private int scorePosition(Playfield playfield, Color color) {
        int score = 0;
        var stones = playfield.getTiles();
        // score horizontal
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            var row = stones[rowIndex];
            for (int colIndex = 0; colIndex < cols - 3; colIndex++) {
                var window = Arrays.copyOfRange(row, colIndex, colIndex + 4);
                if (countStones(window, color) == 4) {
                    score += 100;
                } else if (countStones(window, color) == 3 && countNulls(window) == 1) {
                    score += 10;
                } else if (countStones(window, color) == 2 && countNulls(window) == 2) {
                    score += 2;
                }
            }
        }
        // score vertical
        for (int colIndex = 0; colIndex < cols; colIndex++) {
            // fill column
            var column = rotateClockwise(stones)[colIndex];
            for (int rowIndex = 0; rowIndex < rows-3; rowIndex++) {
                var window = Arrays.copyOfRange(column, rowIndex, rowIndex + 4);
                if (countStones(window, color) == 4) {
                    score += 1000;
                } else if (countStones(window, color) == 3 && countNulls(window) == 1) {
                    score += 20;
                } else if (countStones(window, color) == 2 && countNulls(window) == 2) {
                    score += 5;
                }
            }
        }
        // score diagonal to left down
        for (int rowIndex = 0; rowIndex < rows - 3; rowIndex++) {
            for (int colIndex = 0; colIndex < cols - 3; colIndex++) {
                var window = new Stone[4];
                for (int i = 0; i < 4; i++) {
                    window[i] = stones[rowIndex+i][colIndex+i];
                }
                if (countStones(window, color) == 4) {
                    score += 1000;
                } else if (countStones(window, color) == 3 && countNulls(window) == 1) {
                    score += 20;
                } else if (countStones(window, color) == 2 && countNulls(window) == 2) {
                    score += 5;
                }
            }
        }
        for (int rowIndex = 0; rowIndex < rows - 3; rowIndex++) {
            for (int colIndex = 3; colIndex < cols; colIndex++) {
                var window = new Stone[4];
                for (int i = 0; i < 4; i++) {
                    window[i] = stones[rowIndex + i][colIndex - i];
                }
                if (countStones(window, color) == 4) {
                    score += 1000;
                } else if (countStones(window, color) == 3 && countNulls(window) == 1) {
                    score += 20;
                } else if (countStones(window, color) == 2 && countNulls(window) == 2) {
                    score += 5;
                }
            }
        }
        return score;
    }

    private Playfield copyRealPfToExPf() {
        var playfield = new Playfield(7, 6);
        var exStones = playfield.getTiles();
        var realStones = realPlayfield.getTiles();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                exStones[i][j] = realStones[i][j];
            }
        }
        return playfield;
    }

    private void makeIntPlayfield(Playfield playfield) {
        var stones = playfield.getTiles();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (stones[i][j] == null) {
                    experimentalPlayfieldInt[i][j] = 0;
                } else if (stones[i][j].getTileState() == Color.RED) {
                    experimentalPlayfieldInt[i][j] = 1;
                } else if (stones[i][j].getTileState() == Color.YELLOW) {
                    experimentalPlayfieldInt[i][j] = 2;
                }
            }
        }
    }

    public void setRealPlayfield(Playfield realPlayfield) {
        this.realPlayfield = realPlayfield;
    }

    private int countStones(Stone[] array, Color color) {
        int counter = 0;
        for (Stone stone : array) {
            if (stone == null) continue;
            if (stone.getTileState() == color) counter++;
        }
        return counter;
    }

    private int countNulls(Stone[] array) {
        int counter = 0;
        for (Stone stone : array) {
            if (stone == null) counter++;
        }
        return counter;
    }

    private int getEmptyTile(Playfield playfield, int column) {
        var stones = playfield.getTiles();
        for (int i = rows - 1; i >= 0; i--) {
            if (stones[i][column] == null) {
                return i;
            }
        }
        return -1;
    }

    public Stone[][] rotateClockwise(Stone[][] stones) {
        var newStones = new Stone[cols][rows];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                newStones[col][row] = stones[row][col];
            }
        }
        return newStones;
    }
}
