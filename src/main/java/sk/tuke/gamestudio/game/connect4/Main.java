package sk.tuke.gamestudio.game.connect4;

import sk.tuke.gamestudio.game.connect4.core.Game;
import sk.tuke.gamestudio.game.connect4.core.Color;
import sk.tuke.gamestudio.game.connect4.service.CommentException;
import sk.tuke.gamestudio.game.connect4.service.RatingException;

public class Main {

    public static void main(String[] args) throws CommentException, RatingException {
        Game game = new Game();
        game.play(Color.RED);
    }
}
