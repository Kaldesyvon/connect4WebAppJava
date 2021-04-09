//package sk.tuke.gamestudio.game.connect4.service;
//
//import sk.tuke.gamestudio.game.connect4.entity.Rating;
//
//import java.sql.*;
//
//public class RatingServiceJDBC implements RatingService{
//    /**
//     * Instance of class.
//     */
//    public static RatingServiceJDBC instance;
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
//    public static final String DELETE = "DELETE FROM rating";
//    public static final String SELECT = "SELECT rating FROM rating WHERE rating.player = ? AND rating.game = ?";
//    public static final String INSERT = "INSERT INTO rating VALUES(?, ?, ?, ?)";
//    public static final String AVG = "SELECT AVG(rating) FROM rating";
//    public static final String UPDATE = "UPDATE rating SET rating = ?, rated_on = ? WHERE player = ? AND game = ?";
//    public static final String VACUUM = "VACUUM rating";
//
//    private final Connection connection;
//
//    public RatingServiceJDBC() throws RatingException {
//        try {
//            System.out.println("Connecting to database from rating service...");
//            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
//        } catch (SQLException ex) {
//            throw new RatingException("Problem connecting to database " + ex);
//        }
//    }
//
//    /**
//     * Adds the rating to database if player did not rated, otherwise rating will be overwritten.
//     * @param rating actual rating
//     * @throws RatingException when adding rating fails
//     */
//    @Override
//    public void setRating(Rating rating) throws RatingException {
//        if (getRating(rating.getGame(), rating.getPlayer()) == -1) {
//            try (PreparedStatement statement = connection.prepareStatement(INSERT)) {
//                statement.setString(1, rating.getGame());
//                statement.setString(2, rating.getPlayer());
//                statement.setInt(3, rating.getRating());
//                statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
//                statement.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RatingException("Problem setting rating " + ex);
//            }
//        } else {
//            try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
//                statement.setInt(1, rating.getRating());
//                statement.setTimestamp(2, new Timestamp(rating.getRatedOn().getTime()));
//                statement.setString(3, rating.getPlayer());
//                statement.setString(4, rating.getGame());
//                statement.executeUpdate();
//            } catch (SQLException ex) {
//                throw new RatingException("Problem setting rating " + ex);
//            }
//        }
//    }
//
//    /**
//     * Returns average rating of game.
//     * @param game hmm maybe "connect4"?
//     * @return average rating
//     * @throws RatingException when getting average fails
//     */
//    @Override
//    public int getAverageRating(String game) throws RatingException {
//        try (Statement statement = connection.createStatement()) {
//            ResultSet rs = statement.executeQuery(AVG);
//            rs.next();
//            return rs.getInt(1);
//        } catch (SQLException ex) {
//            throw new RatingException("Problem getting average rating " + ex);
//        }
//    }
//
//    /**
//     * Returns how many stars player gave to game.
//     * @param game I don't know
//     * @param player name of player
//     * @return how many stars player gave
//     * @throws RatingException when getting stars fails
//     */
//    @Override
//    public int getRating(String game, String player) throws RatingException {
//        try (PreparedStatement statement = connection.prepareStatement(SELECT)) {
//            statement.setString(1, player);
//            statement.setString(2, game);
//            try (ResultSet rs = statement.executeQuery()){
//                rs.next();
//                return rs.getInt(1);
//            } catch (SQLException e){
//                return -1;
//            }
//        } catch (SQLException ex) {
//            throw new RatingException("Problem getting rating " + ex);
//        }
//    }
//
//    /**
//     * Clears database of comments
//     * @throws RatingException when clearing failed
//     */
//    @Override
//    public void reset() throws RatingException {
//        try (Statement statement = connection.createStatement()) {
//            statement.executeUpdate(DELETE);
//            statement.executeUpdate(VACUUM);
//        } catch (SQLException ex) {
//            throw new RatingException("Problem resetting rating " + ex);
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
//
//        }
//    }
//}
