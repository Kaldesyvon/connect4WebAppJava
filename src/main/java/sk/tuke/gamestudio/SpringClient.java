package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.game.connect4.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.connect4.core.Game;
import sk.tuke.gamestudio.game.connect4.core.Playfield;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "sk.tuke.gamestudio.server.*"))
public class SpringClient {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
//        SpringApplication.run(SpringClient.class);
    }

    @Bean
    public CommandLineRunner runner(Game game) {
        return args -> game.play();
    }

    @Bean
    Game game(Playfield playfield) { return new Game(playfield); }

    @Bean
    public Playfield playfield() { return new Playfield(7, 6); }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceRestClient();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommentService commentService() { return new CommentServiceRestClient(); }

    @Bean
    public RatingService ratingService() { return new RatingServiceRestClient(); }
}
