package sk.tuke.gamestudio.game.connect4.service;

import org.junit.Assert;
import org.junit.Test;
import sk.tuke.gamestudio.game.connect4.entity.Score;

import java.util.Date;
import java.util.List;

public class ScoreServiceTest {
    ScoreService service = ScoreServiceJDBC.getInstance();

    @Test
    public void testAddScore1(){
        service.reset();

        service.addScore(new Score("connect4", "martin", 100, new Date()));
        service.addScore(new Score("connect4", "jaromir", 20, new Date()));
        service.addScore(new Score("connect4", "martin", 30, new Date()));
        service.addScore(new Score("connect4", "martin", 40, new Date()));

        List<Score> scores = service.getTopScores("connect4");

        Assert.assertEquals(4, scores.size());

        Assert.assertEquals("martin", scores.get(0).getPlayerName());
        Assert.assertEquals(100, scores.get(0).getPoints());

        Assert.assertEquals(20, scores.get(3).getPoints());
        Assert.assertEquals("jaromir", scores.get(3).getPlayerName());
    }

    @Test
    public void testResetScore(){
        service.addScore(new Score("connect4", "martin", 100, new Date()));
        service.addScore(new Score("connect4", "jaromir", 20, new Date()));
        service.addScore(new Score("connect4", "martin", 30, new Date()));
        service.addScore(new Score("connect4", "martin", 40, new Date()));

        service.reset();

        List<Score> scores = service.getTopScores("connect4");

        Assert.assertEquals(0, scores.size());
    }
}
