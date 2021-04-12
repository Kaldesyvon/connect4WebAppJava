package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private int idComment;

    private String player;

    private String game;

    private String comment;

    private Date commentedOn;

    public Comment() {
    }

    /**
     * Creates a feedback that is send to database so I can see opinions of players.
     *
     * @param player      name of player
     * @param game        game which player plays, "connect4" obviously
     * @param comment     actual feedback
     * @param commentedOn date when was feedback added
     */
    public Comment(String player, String game, String comment, Date commentedOn) {
        this.player = player;
        this.game = game;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    public Date getCommentedOn() { return commentedOn; }

    public String getComment() { return comment; }

    public String getGame() {
        return game;
    }

    public String getPlayer() {
        return player;
    }

    /**
     * Overridden toString to print in special format.
     *
     * @return string in format
     */
    @Override
    public String toString() {
        return "Comment{" +
                "game='" + game + '\'' +
                "player='" + player + '\'' +
                ", comment=" + comment +
                ", commentedOn=" + commentedOn +
                '}';
    }
}
