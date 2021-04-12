package sk.tuke.gamestudio.server.webservice;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingService;

@RestController
@RequestMapping("api/rating")
public class RatingServiceRest {
    @Autowired
    private RatingService ratingService;

    @PostMapping
    public void addRating(@RequestBody Rating rating) { ratingService.setRating(rating); }

    @GetMapping("/{game}/{player}")
    public int getRatings(@PathVariable String game, @PathVariable String player) { return ratingService.getRating(game, player); }

    @GetMapping("/{game}/average")
    public int getAverageRating(@PathVariable String game) { return ratingService.getAverageRating(game); }

    @PostMapping(value = "/reset", consumes = "application/json")
    public void reset(@RequestBody String body) throws RatingException {
        String password = new JSONObject(body).getString("password");
        if (password.equals("secret")) ratingService.reset();
    }
}
