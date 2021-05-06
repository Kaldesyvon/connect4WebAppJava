package sk.tuke.gamestudio.game.connect4.core;

import java.util.Random;

public class AI extends Player {
    private final Playfield realPlayfield;
    private int bestMove;

    public AI(Playfield playfield) {
        super("bot", Color.YELLOW);
        this.realPlayfield = playfield;
        chooseBestMove();
    }

    private void chooseBestMove() {
        Playfield experimentalPlayfield = realPlayfield;
        Random random = new Random();
        bestMove = random.nextInt(7);
    }

    public int getBestMove() {
        return bestMove;
    }
}
