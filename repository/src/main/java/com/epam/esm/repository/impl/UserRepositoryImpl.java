package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * This class implements OrderRepository interface
 * This class perform read operation on database
 */
@Repository
@NoArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> find(int id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll(int pageNumber, int pageElementAmount) {
        final String READ_ALL_USERS = "SELECT user FROM User user";
        TypedQuery<User> query = entityManager.createQuery(READ_ALL_USERS, User.class);
        query.setFirstResult((pageNumber - 1) * pageElementAmount);
        query.setMaxResults(pageElementAmount);
        return query.getResultList();
    }

    @Override
    public int findAmount() {
        TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(user) FROM User user", Long.class);
        return (int) (double) countQuery.getSingleResult();
    }
}
