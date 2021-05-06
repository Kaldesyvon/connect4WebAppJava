package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScoreServiceRestClient implements ScoreService{

    private final String url = "http://localhost:8080/api/score";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addScore(Score score) {
        restTemplate.postForEntity(url, score, Score.class);
    }

    @Override
    public List<Score> getTopScores(String game) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url + '/' + game, Score[].class).getBody()));
    }

    @Override
    public List<Score> getTopScoresOfPlayer(String game, String player) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForEntity(url+'/'+game+'/'+player, Score[].class).getBody()));
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
