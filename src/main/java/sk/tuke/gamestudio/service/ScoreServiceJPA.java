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
    public List<Score> getTopScores(String name) {
        return entityManager.createQuery("select s from Score s where s.gameName = :game order by s.points desc")
                .setParameter("game", name)
                .setMaxResults(10).getResultList();
    }

    @Override
    public void reset() {
        entityManager.createQuery("delete from Score").executeUpdate();
    }

    @Override
    public void endConnection() {

    }
}
