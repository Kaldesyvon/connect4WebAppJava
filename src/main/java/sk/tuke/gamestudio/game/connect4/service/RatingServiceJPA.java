package sk.tuke.gamestudio.game.connect4.service;

import sk.tuke.gamestudio.game.connect4.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        entityManager.persist(rating);
    }

    @Override
    public int getAverageRating(String game) {
        Query q = entityManager.createNativeQuery("select avg(r.rating) from rating r");
        int x = q.getFirstResult();
        return x;
    }

    @Override
    public int getRating(String game, String player) {
        return entityManager.createNativeQuery("select rating from rating where player="+player).getFirstResult();
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNativeQuery("truncate table rating").executeUpdate();
    }

    @Override
    public void endConnection() {

    }
}
