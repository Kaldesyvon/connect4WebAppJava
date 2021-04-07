package sk.tuke.gamestudio.game.connect4.service;

import sk.tuke.gamestudio.game.connect4.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    /**
     * Instance of class.
     */
    public static CommentServiceJDBC instance;

    /**
     * Constants for connecting to database.
     */
    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASS = "database26";

    /**
     * SQL commands.
     */
    public static final String DELETE = "DELETE FROM comment";
    public static final String INSERT = "INSERT INTO comment (player, game, comment, commented_on) VALUES (?, ?, ?, ?)";
    public static final String SELECT = "SELECT player, game, comment, commented_on FROM comment WHERE game = ?";
    public static final String VACUUM = "VACUUM comment";

    private final Connection connection;

    private CommentServiceJDBC() throws CommentException {
        try {
            System.out.println("Connecting to database from comment service...");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
        } catch (SQLException ex) {
            throw new CommentException("Problem connecting to database " + ex);
        }
    }

    /**
     * Singleton of Comment Service.
     * @return CommentService instance
     * @throws CommentException when creating of service is failed
     */
    public static CommentServiceJDBC getInstance() throws CommentException {
        if (CommentServiceJDBC.instance == null){
            CommentServiceJDBC.instance = new CommentServiceJDBC();
        }
        return CommentServiceJDBC.instance;
    }

    /**
     * Adds comment to database.
     * @param comment instance of Comment
     * @throws CommentException when comment was failed to add
     */
    @Override
    public void addComment(Comment comment) throws CommentException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
            statement.setString(1, comment.getPlayer());
            statement.setString(2, comment.getGame());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new CommentException("Problem adding comment " + ex);
        }
    }

    /**
     * Filter comments that are written above 'game' and returns all of them
     * @param game name of game which is "connect4" if I am right
     * @return list of comments
     * @throws CommentException when getting comment fails
     */
    @Override
    public List<Comment> getComments(String game) throws CommentException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT)) {
            statement.setString(1, game);

            try (ResultSet rs = statement.executeQuery()) {
                List<Comment> comments = new ArrayList<>();
                while (rs.next()){
                    comments.add(new Comment(rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getTimestamp(4)));
                }
                return comments;
            }
        } catch (SQLException ex) {
            throw new CommentException("Problem selecting comment " + ex);
        }
    }

    /**
     * Clears database of comments
     * @throws CommentException when clearing failed
     */
    @Override
    public void reset() throws CommentException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE);
            statement.executeUpdate(VACUUM);
        } catch (SQLException ex) {
            throw new CommentException("Problem resetting comment " + ex);
        }
    }

    /**
     * End connection with database.
     */
    @Override
    public void endConnection() {
        try { connection.close(); }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
