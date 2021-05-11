package sk.tuke.gamestudio.game.connect4.core;

class Pair {
    private int column;
    private int score;

    Pair(int column, int score) {
        this.column = column;
        this.score = score;
    }

    public int getColumn() {
        return column;
    }

    public int getScore() {
        return score;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
