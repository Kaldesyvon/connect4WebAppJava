//package sk.tuke.gamestudio.game.connect4.service;
//
//import org.junit.Assert;
//import org.junit.Test;
//import sk.tuke.gamestudio.game.connect4.entity.Rating;
//
//import java.util.Date;
//
//public class RatingServiceTest {
//
//    public RatingServiceTest() throws RatingException { }
//
//    @Test
//    public void addRatingTest1() throws RatingException {
//        service.reset();
//
//        service.setRating(new Rating("connect4", "martin", 10, new Date()));
//        service.setRating(new Rating("connect4", "bobik", 10, new Date()));
//        service.setRating(new Rating("connect4", "martin", 5, new Date()));
//
//        var ratings = service.getRating("connect4", "martin");
//
//        Assert.assertEquals(5, ratings);
//
//        ratings = service.getRating("connect4", "bobik");
//
//        Assert.assertEquals(10, ratings);
//    }
//
//    @Test
//    public void averageTest() throws RatingException {
//        service.reset();
//
//        service.setRating(new Rating("connect4", "bobik", 10, new Date()));
//        service.setRating(new Rating("connect4", "martin", 5, new Date()));
//
//        // priemer je 7.5 ale getAverageRating ma vraciat int, takze zaokruhluje nadol
//        Assert.assertEquals(7, service.getAverageRating("connect4"));
//    }
//}
