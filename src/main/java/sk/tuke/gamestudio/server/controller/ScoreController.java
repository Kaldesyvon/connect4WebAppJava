package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@Controller
@RequestMapping("/score")
public class ScoreController {
    private boolean playerFound;
    @Autowired
    private ScoreService scoreService;

    @RequestMapping()
    public String showScores(@RequestParam(required = false) String player, Model model) {
        playerFound = true;
        if (player == null || player.equals("")) {
            model.addAttribute("scores", scoreService.getTopScores("connect4"));
        } else {
            List<Score> scores = scoreService.getTopScoresOfPlayer("connect4", player);
            model.addAttribute("scores", scores);
            if (scores.isEmpty())
                playerFound = false;
        }

        return "score";
    }

    public boolean isFound() {
        return playerFound;
    }
}
