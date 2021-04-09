package sk.tuke.gamestudio.game.connect4.service;

import sk.tuke.gamestudio.game.connect4.entity.Rating;

public interface RatingService {
    void setRating(Rating rating);

    int getAverageRating(String game);

    int getRating(String game, String player);

    void reset() throws RatingException;

    void endConnection();
}
