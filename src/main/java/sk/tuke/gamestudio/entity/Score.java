package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue
    private int idScore;

    private String game;

//    private final String opponentName;

    private String player;

    private int points;

    private Date playedAt;

    public Score() {
    }

    /**
     * Creates score that is send to database so I can see results of players.
     *
     * @param game   name of game that is "connect4" obviously
     * @param player player's name
     * @param points     points that player earned
     * @param playedAt   date at which score was added
     */
    public Score(String game, String player, int points, Date playedAt) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedAt = playedAt;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String name) {
        this.player = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(Date playedAt) {
        this.playedAt = playedAt;
    }


    /**
     * Overridden toString to print in special format.
     *
     * @return string in format
     */
    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                "player='" + player + '\'' +
//                ", opponent='" + opponentName + '\'' +
                ", points=" + points +
                ", playedAt=" + playedAt +
                "}\n";
    }


}
