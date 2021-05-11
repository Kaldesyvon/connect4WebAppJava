package sk.tuke.gamestudio.game.connect4.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AI {
    private Playfield realPlayfield;
    private final Color AIColor;
    private final int rows;
    private final int cols;

    public AI(Playfield playfield) {
        this.realPlayfield = playfield;
        AIColor = Color.YELLOW;
        rows = playfield.getHeight();
        cols = playfield.getWidth();
    }

    public boolean addStone() {
        int move;

//        move = random.nextInt(7);
//        move = pickBestMove();
        move = minimax(realPlayfield, 4, true).getColumn();

        return realPlayfield.addStone(move, AIColor);
    }

//    private int pickBestMove() {
//        int bestCol;
//        int bestScore = 0;
//        do {
//            bestCol = random.nextInt(7);
//        } while (!isValidMove(realPlayfield, bestCol));
//
//        for (int col = 0; col < cols; col++) {
//            Playfield experimentalPlayfield = copyPlayfield(realPlayfield);
//            if (!experimentalPlayfield.addStone(col, AIColor)) continue;
//
//            var score = scorePosition(experimentalPlayfield, AIColor);
//            if (score > bestScore) {
//                bestScore = score;
//                bestCol = col;
//            }
//        }
//        return bestCol;
//    }

    private int scorePosition(Playfield playfield) {
        int score = 0;
        var stones = playfield.getTiles();

        {
            var centerColumn = Arrays.asList(rotateClockwise(stones)[cols / 2]);
            score += countStones(centerColumn, Color.YELLOW) * 4;
        }

        // score horizontal
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            var row = Arrays.asList(stones[rowIndex]);
            for (int colIndex = 0; colIndex < cols - 3; colIndex++) {
                var window = row.subList(colIndex, colIndex + 4);
                score += evaluateScore(window);
            }
        }
        // score vertical
        for (int colIndex = 0; colIndex < cols; colIndex++) {
            // fill column
            var column = Arrays.asList(rotateClockwise(stones)[colIndex]);
            for (int rowIndex = 0; rowIndex < rows - 3; rowIndex++) {
                var window = column.subList(rowIndex, rowIndex + 4);
                score += evaluateScore(window);
            }
        }
        // score diagonal to right up
        for (int rowIndex = rows - 1; rowIndex >= 3; rowIndex--) {
            for (int colIndex = 0; colIndex < cols - 3; colIndex++) {
                List<Stone> window = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    window.add(stones[rowIndex - i][colIndex + i]);
                }
                score += evaluateScore(window);
            }
        }
        // score left up
        for (int rowIndex = rows - 1; rowIndex >= 3; rowIndex--) {
            for (int colIndex = cols - 1; colIndex >= 3; colIndex--) {
                List<Stone> window = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    window.add(stones[rowIndex - i][colIndex - i]);
                }
                score += evaluateScore(window);
            }
        }
        // score right down
        for (int rowIndex = 0; rowIndex < rows - 3; rowIndex++) {
            for (int colIndex = 0; colIndex < cols - 3; colIndex++) {
                List<Stone> window = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    window.add(stones[rowIndex + i][colIndex + i]);
                }
                score += evaluateScore(window);
            }
        }
        // score left down
        for (int rowIndex = 0; rowIndex < rows - 3; rowIndex++) {
            for (int colIndex = cols - 1; colIndex >= 3; colIndex--) {
                List<Stone> window = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    window.add(stones[rowIndex + i][colIndex - i]);
                }
                score += evaluateScore(window);
            }
        }
        return score;
    }

    private Pair minimax(Playfield playfield, int depth, boolean max) {
        var pair = new Pair(0, 0);
        boolean isTerminalMove = isTerminate(playfield);
        if (depth == 0 || isTerminalMove) {
            if (isTerminalMove) {
                if (playfield.checkForWin(Color.YELLOW)) {
                    pair.setScore(10000000);
                    return pair;
                } else if (playfield.checkForWin(Color.RED)) {
                    pair.setScore(-10000000);
                    return pair;
                } else { // tie
                    pair.setScore(0);
                    return pair;
                }
            } else {
                pair.setScore(scorePosition(playfield));
                return pair;
            }
        }
        if (max) {
            int value = Integer.MIN_VALUE;
            int bestCol = 0;

            for (int i = 0; i < cols; i++) {
                Playfield experimentalPlayfield = copyPlayfield(playfield);
                if (experimentalPlayfield.addStone(i, Color.YELLOW)) {
                    pair = minimax(experimentalPlayfield, depth - 1, false);
                    if (pair.getScore() > value) {
                        value = pair.getScore();
                        bestCol = i;
                    }
                    pair.setColumn(bestCol);
                    pair.setScore(value);
                }
            }
            return pair;
        } else {
            int value = Integer.MAX_VALUE;
            int bestCol = 0;

            for (int i = 0; i < cols; i++) {
                Playfield experimentalPlayfield = copyPlayfield(playfield);
                if (experimentalPlayfield.addStone(i, Color.RED)) {
                    pair = minimax(experimentalPlayfield, depth - 1, true);
                    if (pair.getScore() < value) {
                        value = pair.getScore();
                        bestCol = i;
                    }
                    pair.setColumn(bestCol);
                    pair.setScore(value);
                }
            }
            return pair;
        }
    }

    private boolean isTerminate(Playfield playfield) {
        boolean terminal = playfield.checkForWin(Color.RED) || playfield.checkForWin(Color.YELLOW);
        if (!terminal) {
            for (int i = 0; i < cols; i++) {
                if (isValidMove(playfield, i)) return false;
            }
        }
        return true;
    }

    private Playfield copyPlayfield(Playfield playfield) {
        var newPlayfield = new Playfield(7, 6);
        var newStones = newPlayfield.getTiles();
        var realStones = playfield.getTiles();
        for (int i = 0; i < 6; i++) {
            System.arraycopy(realStones[i], 0, newStones[i], 0, 7);
        }
        return newPlayfield;
    }

    public void setRealPlayfield(Playfield realPlayfield) {
        this.realPlayfield = realPlayfield;
    }

    private int countStones(List<Stone> array, Color color) {
        int counter = 0;
        for (Stone stone : array) {
            if (stone == null) continue;
            if (stone.getTileState() == color) counter++;
        }
        return counter;
    }

    private int countNulls(List<Stone> array) {
        int counter = 0;
        for (Stone stone : array) {
            if (stone == null) counter++;
        }
        return counter;
    }

    // change scoring to tune AI
    private int evaluateScore(List<Stone> window) {
        int score = 0;
        Color opponentColor = Color.YELLOW == AIColor ? Color.RED : Color.YELLOW;

        int stoneCount = countStones(window, AIColor);
        int nullCount = countNulls(window);
        if (stoneCount == 4)
            score += 1000;
        if (stoneCount == 3 && nullCount == 1)
            score += 5;
        else if (stoneCount == 2 && nullCount == 2)
            score += 2;

        stoneCount = countStones(window, opponentColor);
        nullCount = countNulls(window);
        if (stoneCount == 3 && nullCount == 1)
            score -= 4;
        else if (stoneCount == 2 && nullCount == 2)
            score -= 3;

        return score;
    }

    private boolean isValidMove(Playfield playfield, int column) {
        var stones = playfield.getTiles();
        return stones[0][column] == null;
    }

    private Stone[][] rotateClockwise(Stone[][] stones) {
        var newStones = new Stone[cols][rows];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                newStones[col][row] = stones[row][col];
            }
        }
        return newStones;
    }
}
