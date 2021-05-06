package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.UserEntity;
import sk.tuke.gamestudio.game.connect4.core.*;


@Controller
@RequestMapping("/connect4")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class Connect4Controller {
    private Playfield playfield;
    private final Player redPlayer = new Player("jozo", Color.RED);
    private final Player yellowPlayer = new AI(playfield);
    private Player playerOnTurn = redPlayer;

    public Connect4Controller(Playfield playfield) {
        this.playfield = playfield;
    }


    @RequestMapping
    public String connect4(@RequestParam(required = false) String column, @RequestParam(required = false) Color color, Model model) {
        try {
            playfield.addStone(Integer.parseInt(column), color);
            playerOnTurn = switchPlayers(playerOnTurn);
        } catch (Exception ex) {
            System.out.println("Zle parametre");
        }

        addToModel(model);

        return "connect4";
    }

    @RequestMapping("/new")
    public String newGame(Model model) {
        playfield = new Playfield(7, 6);
        addToModel(model);
        return "redirect:/connect4";
    }

    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();

        sb.append("<table>\n");

        for (int row = 0; row < 6; row++) {
            sb.append("<tr>\n");
            for (int column = 0; column < 7; column++) {
                Stone[][] tile = playfield.getTiles();
                sb.append("<td>\n");
                sb.append(String.format("<a href='/connect4?column=%d&color=%s'>", column, playerOnTurn.getColor()));
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
//        model.addAttribute("scores", scoreService.getTopScores("connect4"));
    }

    private Player switchPlayers(Player player) {
        return player.getColor() == Color.RED ? yellowPlayer : redPlayer;
    }
}
