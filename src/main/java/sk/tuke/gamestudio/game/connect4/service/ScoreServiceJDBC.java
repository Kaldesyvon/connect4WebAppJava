//package sk.tuke.gamestudio.game.connect4.service;
//
//import sk.tuke.gamestudio.game.connect4.entity.Score;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ScoreServiceJDBC implements ScoreService {
//    /**
//     * Instance of class.
//     */
//    public static ScoreServiceJDBC instance;
//
//    /**
//     * Constants for connecting to database.
//     */
//    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
//    public static final String JDBC_USER = "postgres";
//    public static final String JDBC_PASS = "database26";
//
//    /**
//     * SQL commands.
//     */
//    public static final String SELECT = "SELECT game_name, player_name, points, played_at FROM score WHERE game_name = ? ORDER BY points DESC LIMIT 10";
//    public static final String DELETE = "DELETE FROM score";
//    public static final String INSERT = "INSERT INTO score (game_name, player_name, points, played_at) VALUES (?, ?, ?, ?)";
//    public static final String VACUUM = "VACUUM score";
//
//    private final Connection connection;
//
//    public ScoreServiceJDBC() throws ScoreException {
//        try {
//            System.out.println("Connecting to database from score service...");
//            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
//        } catch (SQLException ex) {
//            throw new ScoreException("Problem connecting to database " + ex);
//        }
//    }
//
//    /**
//     * Adds a score to a database
//     * @param score actual score
//     * @throws ScoreException when adding fails
//     */
//    @Override
//    public void addScore(Score score) throws ScoreException {
//        try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
//            statement.setString(1, score.getGameName());
//            statement.setString(2, score.getPlayerName());
//            statement.setInt(3, score.getPoints());
//            statement.setTimestamp(4, new Timestamp(score.getPlayedAt().getTime()));
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            throw new ScoreException("Problem adding score " + ex);
//        }
//    }
//
//    /**
//     * Get first 10 player names that played the most points.
//     * @param name name "connect4"
//     * @return list of score of maximum size of 10
//     * @throws ScoreException when getting top score fails
//     */
//    @Override
//    public List<Score> getTopScores(String name) throws ScoreException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT)) {
//            statement.setString(1, name);
//
//            try (ResultSet rs = statement.executeQuery()) {
//                List<Score> scores = new ArrayList<>();
//                while (rs.next()){
//                    scores.add(new Score(rs.getString(1),
//                            rs.getString(2),
//                            rs.getInt(3),
//                            rs.getTimestamp(4)));
//                }
//                return scores;
//            }
//        } catch (SQLException ex) {
//            throw new ScoreException("Problem selecting top scores " + ex);
//        }
//
//    }
//
//    /**
//     * Clears database of comments
//     * @throws ScoreException when clearing failed
//     */
//    @Override
//    public void reset() throws ScoreException {
//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate(DELETE);
//            statement.executeUpdate(VACUUM);
//        } catch (SQLException ex) {
//            throw new ScoreException("Problem resetting score " + ex);
//        }
//    }
//
//    /**
//     * End connection with database.
//     */
//    @Override
//    public void endConnection() {
//        try { connection.close(); }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
