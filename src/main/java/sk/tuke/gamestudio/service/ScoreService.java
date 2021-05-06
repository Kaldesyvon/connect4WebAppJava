package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface ScoreService {
    void addScore(Score score);

    List<Score> getTopScores(String game);

    List<Score> getTopScoresOfPlayer(String game, String player);

    void reset();
}
