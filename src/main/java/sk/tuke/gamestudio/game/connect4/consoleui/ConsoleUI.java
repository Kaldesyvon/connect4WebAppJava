package sk.tuke.gamestudio.game.connect4.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.game.connect4.core.*;
import sk.tuke.gamestudio.game.connect4.entity.Comment;
import sk.tuke.gamestudio.game.connect4.entity.Rating;
import sk.tuke.gamestudio.game.connect4.entity.Score;
import sk.tuke.gamestudio.game.connect4.service.*;

import java.util.Date;
import java.util.Scanner;


public class ConsoleUI {
    /**
     * Variables that are used to color the console.
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\033[0;93m";
    public static final String ANSI_RED = "\033[0;91m";

    /**
     * Services that manages tables in database
     */
    @Autowired
    private final ScoreService scoreService;
    @Autowired
    private final CommentService commentService;
    @Autowired
    private final RatingService ratingService;

    @Autowired
    private final Playfield playfield;

    private final Stone[][] stones;

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Main part of game, here are handled inputs and outputs.

//     * @throws CommentException if connection to service in database is failed
//     * @throws RatingException if connection to rating service in database is failed
     */
    public ConsoleUI(Playfield playfield, ScoreService scoreService, CommentService commentService, RatingService ratingService) {
        this.scoreService = scoreService;
        this.commentService = commentService;
        this.ratingService = ratingService;
        this.playfield = playfield;
        stones = playfield.getTiles();
    }


    /**
     * Method process input that user made and provides adequate output.
     * @param playerOnTurn player whose turn is
     * @param otherPlayer other player
     * @return column user want to add stone to
     */
    public int processInput(Player playerOnTurn, Player otherPlayer) {
        System.out.println();

        String input;

        do {
            System.out.println(playerOnTurn.getColor() == Color.RED
                    ? ANSI_RED + playerOnTurn.getName() + "'s turn" + ANSI_RESET
                    : ANSI_YELLOW + playerOnTurn.getName() + "'s turn" + ANSI_RESET);
            System.out.println("type 'x' to end game, 'r' for rating, 'f' for feedback and 't' for top 10 players");
            System.out.println("select column: ");
            input = scanner.nextLine();
            if (!input.equals("x")) {
                if (!input.equals("r")) {
                    if (input.equals("f")) { // handles commenting
                        do {
                            System.out.println("Write what you have on heart (max 150 symbols). Type 'g' for see comments, 'b' to get back");
                            String comment = scanner.nextLine();
                            if (comment.equals("b")) break;
                            if (comment.equals("g")) {
                                System.out.println(commentService.getComments("connect4").toString());
                                continue;
                            }
                            commentService.addComment(new Comment(playerOnTurn.getName(), "connect4", comment, new Date()));
                            System.out.println("thank you for feedback");
                            break;
                        } while (true);
                    } else  if (input.equals("t")){ // handles top score
                        try {
                            System.out.println(scoreService.getTopScores("connect4").toString());
                        } catch (ScoreException e){
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            int columnInput = Integer.parseInt(input);
                            if (columnInput >= 0 && columnInput <= playfield.getWidth() - 1){
                                break;
                            }
                            else {
                                System.out.println("invalid column");
                            }
                        }
                        catch (Exception ex) {
                            System.out.println("wrong input");
                        }

                    }
                } else { // handles rating
                    int rating;
                    System.out.println("Rate this game (1-10)");
                    do {
                        String line = scanner.nextLine();
                        try {
                            rating = Integer.parseInt(line);
                            if (rating >= 1 && rating <= 10){
                                break;
                            }
                            else {
                                System.out.println("rating out of range");
                            }
                        } catch (Exception ex) {
                            System.out.println("wrong input");
                        }
                    } while (true);
                    ratingService.setRating(new Rating("connect4", playerOnTurn.getName(), rating, new Date()));
                    System.out.println("Average is " + ratingService.getAverageRating("connect4"));
                    System.out.println("Thank you for rating");
                }
            } else { // handles exit
                addScoreToDB(playerOnTurn, otherPlayer);
                System.exit(0);
            }
        }while (true);
        return Integer.parseInt(input);
    }

    /**
     * Shows at what state playfield is so players can interact.
     */
    public void showPlayfield() {
        int width = playfield.getWidth();
        int height = playfield.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (stones[i][j] == null) {
                    System.out.print(" - ");
                } else {
                    System.out.print(stones[i][j].getTileState() == Color.RED
                            ? ANSI_RED + " 0 " + ANSI_RESET
                            : ANSI_YELLOW + " 0 " + ANSI_RESET);
                }
            }
            System.out.print(" " + i);
            System.out.println();
        }
        for (int i = 0; i < width; i++) {
            System.out.print(" " + (i) + " ");
        }
    }

    /**
     * Handles when game end is draw. Does not matter on which player is passed first to method.
     * @param player1 first player
     * @param player2 second player
     */
    public void tie(Player player1, Player player2) {
        System.out.println();
        System.out.println("Its a TIE!");
        player1.addPoints(5);
        player2.addPoints(5);
    }

    /**
     * Handles when there is a winner and looser.
     * @param winner player who won
     * @param looser player who lost
     */
    public void win(Player winner, Player looser) {
        System.out.println();
        System.out.println(winner.getColor() == Color.YELLOW
                ? ANSI_YELLOW + winner.getName() + " Wins" + ANSI_RESET
                : ANSI_RED + winner.getName() + " Wins" + ANSI_RESET);
        winner.addPoints(10);
        looser.addPoints(-5);
    }

    /**
     * Handles input of players if they agree to play again.
     * @return true if they want to play again, false otherwise
     */
    public boolean playAgain() {
        System.out.println();
        System.out.println("Do you want to play again? [Y/n]");

        while (true){
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("Y")) return true;
            else if (answer.equalsIgnoreCase("n")) return false;
            else {
                System.out.println("wrong input");
            }
        }
    }

    /**
     * Creates player named as user wish.
     * @param color what color player is
     * @return new instance of Player class
     */
    public Player createPlayer(Color color){
        System.out.println("Enter name of "  +(color == Color.RED ? "RED" : "YELLOW") + " player ");
        String line = scanner.nextLine();
        return new Player(line, color, playfield);
    }

    /**
     * Prints score of both players. Does not matter on order of arguments, players' name will be printed.
     * @param player1 first player
     * @param player2 second player
     */
    public void printScores(Player player1, Player player2) {
        System.out.println(player1.getName() + "'s score: " + player1.getScore());
        System.out.println(player2.getName() + "'s score: " + player2.getScore());
    }

    /**
     * Creates Score instance and adds it to created database.
     * Does not matter on order of arguments, players' name will be added to database.
     * @param player1 first player
     * @param player2 second player
     */
    public void addScoreToDB(Player player1, Player player2) {
        scoreService.addScore(new Score("connect4", player1.getName(), player1.getScore(), new Date()));
        scoreService.addScore(new Score("connect4",player2.getName(), player2.getScore(), new Date()));
    }

    /**
     * Ends connection with all services (score, rating, comment)
     */
    public void endConnections() {
        scoreService.endConnection();
        commentService.endConnection();
        ratingService.endConnection();
    }
}
