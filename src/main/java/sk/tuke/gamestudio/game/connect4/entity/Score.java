package sk.tuke.gamestudio.game.connect4.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Score {
    @Id
    @GeneratedValue
    private int idScore;

    private String gameName;

//    private final String opponentName;

    private String playerName;

    private int points;

    private Date playedAt;

    public Score() { }

    /**
     * Creates score that is send to database so I can see results of players.
     * @param gameName name of game that is "connect4" obviously
     * @param playerName player's name
     * @param points points that player earned
     * @param playedAt date at which score was added
     */
    public Score(String gameName, String playerName,  int points, Date playedAt){
        this.gameName = gameName;
        this.playerName = playerName;
        this.points = points;
        this.playedAt = playedAt;
    }

//    public String getGameName() {
//        return gameName;
//    }
//
//    public void setGameName(String game) {
//        this.gameName = game;
//    }
//
//    public String getPlayerName() {
//        return playerName;
//    }
//
//    public void setPlayerName(String player) {
//        this.playerName = player;
//    }
//
//    public int getPoints() {
//        return points;
//    }
//
//    public void setPoints(int points) {
//        this.points = points;
//    }
//
//    public Date getPlayedAt() {
//        return playedAt;
//    }
//
//    public void setPlayedAt(Date playedAt) {
//        this.playedAt = playedAt;
//    }


    /**
     * Overridden toString to print in special format.
     * @return string in format
     */
    @Override
    public String toString() {
        return "Score{" +
                "game='" + gameName + '\'' +
                "player='" + playerName + '\'' +
//                ", opponent='" + opponentName + '\'' +
                ", points=" + points +
                ", playedAt=" + playedAt +
                "}\n";
    }


}
