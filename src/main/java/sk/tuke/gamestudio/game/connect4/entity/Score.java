package sk.tuke.gamestudio.game.connect4.entity;

import java.util.Date;

public class Score {
    private final String gameName;

//    private final String opponentName;

    private final String playerName;

    private final int points;

    private final Date playedAt;

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

    public String getGameName() { return gameName; }

    public String getPlayerName() {
        return playerName;
    }

//    public String getOpponentName() { return opponentName; }

    public int getPoints() {
        return points;
    }

    public Date getPlayedAt() {
        return playedAt;
    }

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
