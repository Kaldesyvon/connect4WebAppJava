package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        if (getRating("connect4", rating.getPlayer()) == -1) {
            entityManager.persist(rating);
            System.out.println("novy rating");
        } else {
            entityManager.createQuery("update Rating r set rating = " + rating.getRating() + "where player='" + rating.getPlayer() + "'")
                    .executeUpdate();
            System.out.println("updatol som rating");
        }
    }

    @Override
    public int getAverageRating(String game) {
        Query query = entityManager.createQuery("select avg(r.rating) from Rating r");
        if (query.getSingleResult() == null)  return -1;

        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public int getRating(String game, String player) {
        Query query = entityManager.createQuery("select r.rating from Rating as r where r.player ='" + player + "'");
        return query.getResultList()
                .isEmpty() ? -1 : ((Number) query.getSingleResult()).intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Rating> getRatings(String game) {
        return (List<Rating>) entityManager.createQuery("select r from Rating r order by r.ratedOn desc").getResultList();
    }

    @Override
    public void reset() {
        entityManager.createQuery("delete from Rating").executeUpdate();
    }
}
