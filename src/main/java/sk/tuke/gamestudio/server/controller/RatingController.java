package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.UserEntity;
import sk.tuke.gamestudio.service.RatingService;

import java.util.Date;


@Controller
@RequestMapping("/rating")
public class RatingController {
    private boolean playerFound;
    private boolean shownOnePlayer;
    private boolean ratingOutOfRange = false;
    @Autowired
    private RatingService ratingService;

    @RequestMapping()
    public String showRatings(@RequestParam(required = false) String player, Model model) {
        model.asMap().clear();
        playerFound = true;
        model.addAttribute("average", ratingService.getAverageRating("connect4"));
        if (player == null || player.equals("")) {
            model.addAttribute("ratings", ratingService.getRatings("connect4"));
        }
        else {
            int rating = ratingService.getRating("connect4", player);
            if (rating == -1)
                playerFound = false;
            else {
                shownOnePlayer = true;
                model.addAttribute("playerName", player);
                model.addAttribute("playerRating", rating);
            }
        }
        return "rating";
    }

    @RequestMapping("/add")
    public String addRating(@RequestParam int rating) {
        if (rating > 10 || rating < 0){
            ratingOutOfRange = true;
        } else {
            ratingService.setRating(new Rating("connect4", UserTransporter.getUser().getLogin(), rating, new Date()));
        }
        return "redirect:/rating";
    }

    public boolean isFound() {
        return playerFound;
    }

    public boolean isShownOnePlayer() {
        return shownOnePlayer;
    }
}
