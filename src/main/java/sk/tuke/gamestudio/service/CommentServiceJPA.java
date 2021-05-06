package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

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
    @SuppressWarnings("unchecked")
    public List<Comment> getComments(String game) {
        return entityManager.createQuery("select c from Comment c order by c.commentedOn desc")
                .setMaxResults(10).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Comment> getCommentsByPlayer(String game, String player) {
        return entityManager.createQuery("select c from Comment c where c.player='"+player+"' order by c.commentedOn desc")
                .setMaxResults(10).getResultList();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("truncate table comment").executeUpdate();
    }
}
