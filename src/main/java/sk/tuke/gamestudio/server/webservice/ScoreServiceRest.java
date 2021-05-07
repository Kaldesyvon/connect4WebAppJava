package sk.tuke.gamestudio.server.webservice;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;


@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {
    private final ScoreService scoreService;

    public ScoreServiceRest(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public void addScore(@RequestBody Score score) {
        scoreService.addScore(score);
    }

    @GetMapping("/{game}")
    public List<Score> getTopScores(@PathVariable String game) {
        return scoreService.getTopScores(game);
    }

    @GetMapping("/{game}/{name}")
    public List<Score> getTopScores(@PathVariable String game, @PathVariable String name) {
        return scoreService.getTopScoresOfPlayer(game, name);
    }

    @PostMapping(value = "/reset", consumes = "application/json")
    public void reset(@RequestBody String body) {
        String password = new JSONObject(body).getString("password");
        if (password.equals("secret")) scoreService.reset();
    }
}
