package sk.tuke.gamestudio.game.connect4.service;

import sk.tuke.gamestudio.game.connect4.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getComments(String game) {
        return entityManager.createNativeQuery("select c from comment c").setMaxResults(10).getResultList();
    }

    @Override
    public void reset() throws CommentException {
        entityManager.createNativeQuery("truncate table comment");
    }

    @Override
    public void endConnection() {

    }
}
