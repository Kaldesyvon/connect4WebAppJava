package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.util.List;

public interface RatingService {
    void setRating(Rating rating);

    int getAverageRating(String game);

    int getRating(String game, String player);

    List<Rating> getRatings(String game);

    void reset() throws RatingException;
}
