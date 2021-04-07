package sk.tuke.gamestudio.game.connect4.core;


import sk.tuke.gamestudio.game.connect4.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.connect4.service.CommentException;
import sk.tuke.gamestudio.game.connect4.service.RatingException;

public final class Game {

    private final Playfield playfield;

    private final Player redPlayer;

    private final Player yellowPlayer;

    private final ConsoleUI ui;

    // change these two variables to change playfield
    private final int playfieldWidth = 7;
    private final int playfieldHeight = 6;

    /**
     * Creates everything needed to start a game.
     * @throws CommentException if connection to comment service in database is failed
     * @throws RatingException if connection to rating service in database is failed
     */
    public Game() throws RatingException, CommentException {
        playfield = new Playfield(playfieldWidth, playfieldHeight);
        ui = new ConsoleUI(playfield);
        redPlayer = ui.createPlayer(Color.RED);
        yellowPlayer = ui.createPlayer(Color.YELLOW);
    }

    /**
     * Main game loop and UI calls. When game ends first player is switched.
     * @param firstPlayer color of player who takes first turn
     */
    public void play(Color firstPlayer) {
        while (true){
            int columnInput;

            Player playerOnTurn = redPlayer.getColor() == firstPlayer ? redPlayer : yellowPlayer;

            int turns = 0; // game must be ended in width * height moves at most
            int maxTurns = playfieldWidth * playfieldHeight;

            do {
                ui.showPlayfield();
                columnInput = ui.processInput(playerOnTurn, switchPlayers(playerOnTurn));

                if (!playerOnTurn.addStone(columnInput)) {
                    System.out.println("Stone cannot be added");
                    continue;
                }

                turns++;
                playerOnTurn = switchPlayers(playerOnTurn);
            } while (!playfield.checkForWin() && turns != maxTurns);

            ui.showPlayfield();

            if (turns == maxTurns) {
                ui.tie(redPlayer, yellowPlayer);
            } else {
                ui.win(switchPlayers(playerOnTurn), (playerOnTurn));
            }
            ui.printScores(redPlayer, yellowPlayer);

            if (ui.playAgain()) {
                emptyPlayfield();
                firstPlayer = switchPlayers(firstPlayer);
                continue;
            }
            ui.addScoreToDB(redPlayer, yellowPlayer);
            ui.endConnections();
            break;
        }
    }

    private void emptyPlayfield() {
        for (int i = 0; i < playfieldHeight; i++) {
            for (int j = 0; j < playfieldWidth; j++) {
                playfield.getTiles()[i][j] = null;
            }
        }
    }

    private Color switchPlayers(Color color) {
        return color == Color.RED ? Color.YELLOW : Color.RED;
    }

    private Player switchPlayers(Player player){
        return player.getColor() == Color.RED ? yellowPlayer : redPlayer;
    }
}
