package sk.tuke.gamestudio.game.connect4.entity;

import java.util.Date;

public class Rating {
    private final String game;

    private final String player;

    private final int rating;

    private final Date ratedOn;

    /**
     * Creates rating that is sent to database so I can see if the game is good or not.
     * @param game game's name "connect4" I guess
     * @param player players name
     * @param rating stars out of 10
     * @param ratedOn date when rating was added
     */
    public Rating(String game, String player, int rating, Date ratedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public String getGame() {
        return game;
    }

    public int getRating() {
        return rating;
    }

    public String getPlayer() {
        return player;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    /**
     * Overridden toString to print in special format.
     * @return string in format
     */
    @Override
    public String toString() {
        return "Comment{" +
                "game='" + game + '\'' +
                "player='" + player + '\'' +
                ", rating=" + rating +
                ", ratedOn=" + ratedOn +
                '}';
    }
}
