package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.connect4.core.*;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/connect4")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class Connect4Controller {

    private Playfield playfield;
    private final Player redPlayer;
    private final AI yellowPlayer;
    private final int maxTurns;
    private int turn = 0;
    private GameState gameState = GameState.PLAYING;
    private final boolean showScore = false;
    private final ScoreService scoreService;


    public Connect4Controller(Playfield playfield, ScoreService scoreService) {
        this.playfield = playfield;
        this.redPlayer = new Player("jozef", Color.RED);
        this.yellowPlayer = new AI(playfield);
        maxTurns = playfield.getHeight() * playfield.getHeight();
        this.scoreService = scoreService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String connect4(@RequestParam(required = false) String column, @RequestParam(required = false) Color color, Model model) {
        if (maxTurns == turn)
            gameState = GameState.TIE;

        if (column != null && color != null) {
            while (gameState == GameState.PLAYING && !playfield.addStone(Integer.parseInt(column), color))
                turn++;

            if (playfield.checkForWin(Color.RED)) {
                gameState = GameState.RED_WIN;
                if (UserTransporter.isLogged())
                    redPlayer.addPoints(10);
            }

            while (gameState == GameState.PLAYING && !yellowPlayer.addStone())
                turn++;

            if (gameState == GameState.PLAYING && playfield.checkForWin(Color.YELLOW)) {
                gameState = GameState.YELLOW_WIN;
                if (UserTransporter.isLogged())
                    redPlayer.addPoints(-5);
            }
        }
        addToModel(model);
        return "connect4";
    }

    @RequestMapping("/new")
    public String newGame(Model model) {
        turn = 0;
        reset();
        addToModel(model);
        return "redirect:/connect4";
    }

    @RequestMapping("/add")
    public String submitScore(Model model) {
        scoreService.addScore(new Score("connect4", UserTransporter.getUser().getLogin(), redPlayer.getScore(), new Date()));
        addToModel(model);
        return "redirect:/connect4";
    }

    private void reset() {
        playfield = new Playfield(7, 6);
        yellowPlayer.setRealPlayfield(playfield);
        gameState = GameState.PLAYING;
    }

    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();

        sb.append("<table class='playfield'>\n");

        for (int row = 0; row < 6; row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < 7; column++) {
                Stone[][] tile = playfield.getTiles();
                sb.append("<td>\n");
                sb.append(String.format("<a href='/connect4?column=%d&color=%s'>", column, Color.RED));
                sb.append("<img src='/images/connect4/").append(getImageName(tile[row][column])).append(".png'>");
                sb.append("</a>");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }

        sb.append("</table>\n");

        return sb.toString();
    }

    public String getImageName(Stone tile) {
        if (tile == null) {
            return "empty";
        }
        if (tile.getTileState() == Color.RED) {
            return "red";
        } else return "yellow";
    }

    public void addToModel(Model model) {
        model.addAttribute("htmlField", getHtmlField());
        model.addAttribute("score", redPlayer.getScore());
        model.addAttribute("isPlaying", isPlaying());
    }

    @ModelAttribute("gameState")
    public boolean isPlaying() {
        return gameState == GameState.PLAYING;
    }
}
