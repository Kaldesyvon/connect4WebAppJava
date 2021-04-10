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

        if (getRating("connect4", rating.getPlayer()) == -1) {
            entityManager.persist(rating);
            System.out.println("novy rating");
        }
        else {
            entityManager.createQuery("update Rating r set rating = " + rating.getRating() + "where player='" + rating.getPlayer() + "'")
            .executeUpdate();
            System.out.println("updatol som rating");
        }
    }

    @Override
    public int getAverageRating(String game) {
        Query query = entityManager.createQuery("select avg(r.rating) from Rating r");
        return ((Number)query.getSingleResult()).intValue();
    }

    @Override
    public int getRating(String game, String player) {
        Query query = entityManager.createQuery("select r.rating from Rating as r where r.player ='" + player+ "'");
        return query.getResultList()
                .isEmpty() ? -1 : ((Number)query.getSingleResult()).intValue();
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createQuery("delete from Rating").executeUpdate();
    }

    @Override
    public void endConnection() {

    }
}
