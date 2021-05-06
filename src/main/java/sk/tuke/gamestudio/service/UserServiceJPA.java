package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Transactional
public class UserServiceJPA implements UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(UserEntity user) {
        entityManager.persist(user);
    }

    @Override
    public UserEntity getUser(String name) {
        Query query = entityManager.createQuery("select u from UserEntity u where u.login='" + name + "'");
        return query.getResultList()
                .isEmpty() ? null : (UserEntity) query.getSingleResult();
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("truncate table user_entity");
    }
}
