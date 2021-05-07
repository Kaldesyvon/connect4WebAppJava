package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) {
        entityManager.persist(score);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Score> getTopScores(String game) {
        return entityManager.createQuery("select s from Score s order by s.points desc")
                .setMaxResults(10).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Score> getTopScoresOfPlayer(String game, String player) {
        return entityManager.createQuery("select s from Score s where s.player='" + player + "' order by s.points desc")
                .setMaxResults(10).getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("truncate table score").executeUpdate();
    }

}
