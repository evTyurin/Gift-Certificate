package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.repository.OrderRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * This class implements OrderRepository interface
 * This class perform create/read/delete operations on database
 */
@Repository
@NoArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Order> find(int id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public int findAmount() {
        TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(orders) FROM Order orders", Long.class);
        return (int) (double) countQuery.getSingleResult();
    }

    @Override
    public List<Order> findAll(int pageNumber, int pageElementAmount) {
        final String READ_ALL_ORDERS = "SELECT orders FROM Order orders";
        TypedQuery<Order> query = entityManager.createQuery(READ_ALL_ORDERS, Order.class);
        query.setFirstResult((pageNumber - 1) * pageElementAmount);
        query.setMaxResults(pageElementAmount);
        return query.getResultList();
    }

    @Override
    public void create(Order order) {
        entityManager.persist(order);
    }
}
