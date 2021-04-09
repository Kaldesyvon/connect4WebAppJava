package sk.tuke.gamestudio.game.connect4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.connect4.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.connect4.core.Game;
import sk.tuke.gamestudio.game.connect4.core.Playfield;
import sk.tuke.gamestudio.game.connect4.service.*;


@SpringBootApplication
@Configuration
public class SpringClient {
    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }

    @Bean
    public CommandLineRunner runner(Game game){
        return args -> game.play();
    }

    @Bean
    public ConsoleUI consoleUI(Playfield playfield, ScoreService ss, CommentService cs, RatingService rs) {
        return new ConsoleUI(playfield, ss, cs, rs);
    }

    @Bean Game game(ConsoleUI ui, Playfield playfield) { return new Game(ui, playfield); }

    @Bean
    public Playfield playfield() { return new Playfield(7,6); }

    @Bean
    public ScoreService scoreService(){ return new ScoreServiceJPA(); }

    @Bean CommentService commentService() { return new CommentServiceJPA(); }

    @Bean
    public RatingService ratingService() { return new RatingServiceJPA(); }
}
