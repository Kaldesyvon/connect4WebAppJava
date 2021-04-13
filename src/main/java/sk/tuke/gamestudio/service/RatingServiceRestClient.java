package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Objects;

public class RatingServiceRestClient implements RatingService {
    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    @SuppressWarnings("unboxing")
    public int getAverageRating(String game) {
        return Objects.requireNonNull(restTemplate.getForEntity(url + '/' + game + "/average", Integer.class).getBody());
    }

    @Override
    public int getRating(String game, String player) {
        return Objects.requireNonNull(restTemplate.getForEntity(url + '/' + game + '/' + player, Integer.class).getBody());
    }

    @Override
    public void reset() throws RatingException {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
