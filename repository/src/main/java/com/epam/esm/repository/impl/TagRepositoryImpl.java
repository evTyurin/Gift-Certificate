package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * This class implements TagRepository interface
 * This class perform create/read/delete operations on database
 */
@Repository
@NoArgsConstructor
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int findAmount() {
        TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(tag) FROM Tag tag", Long.class);
        return (int) (double) countQuery.getSingleResult();
    }

    @Override
    public List<Tag> findAll(int pageNumber, int pageElementAmount) {
        final String READ_ALL_TAGS = "SELECT tag FROM Tag tag";
        TypedQuery<Tag> query = entityManager.createQuery(READ_ALL_TAGS, Tag.class);
        query.setFirstResult((pageNumber - 1) * pageElementAmount);
        query.setMaxResults(pageElementAmount);

        return query.getResultList();
    }

    @Override
    public List<Tag> findMostWidelyUsedTag() {
        final String mostWidelyUsedTagQuery = "SELECT t1.tag_id as id, t1.tag_name as name FROM ((SELECT distinct most_payable_customers.id as customer_id, tag.id as tag_id, count(tag.id) as tag_amount, tag.name as tag_name FROM (SELECT distinct orders.user_id as id FROM orders GROUP BY orders.user_id HAVING sum(orders.price) = (SELECT sum(price) OVER (PARTITION BY user_id) FROM orders LIMIT 1)) AS most_payable_customers JOIN orders ON most_payable_customers.id = orders.user_id JOIN order_has_gift_certificate ON orders.id = order_has_gift_certificate.order_id JOIN gift_certificate ON gift_certificate.id = order_has_gift_certificate.gift_certificate_id JOIN gift_certificate_has_tag ON gift_certificate_has_tag.gift_certificate_id = gift_certificate.id JOIN tag ON tag.id = gift_certificate_has_tag.tag_id group by most_payable_customers.id, tag.id)) as t1 JOIN (SELECT customer_id, max(tag_amount) as max_tag_amount from ((SELECT distinct most_payable_customers.id as customer_id, tag.id as tag_id, count(tag.id) as tag_amount, tag.name as tag_name FROM (SELECT distinct orders.user_id as id FROM orders GROUP BY orders.user_id HAVING sum(orders.price) = (SELECT sum(price) OVER (PARTITION BY user_id) FROM orders LIMIT 1)) AS most_payable_customers JOIN orders ON most_payable_customers.id = orders.user_id JOIN order_has_gift_certificate ON orders.id = order_has_gift_certificate.order_id JOIN gift_certificate ON gift_certificate.id = order_has_gift_certificate.gift_certificate_id JOIN gift_certificate_has_tag ON gift_certificate_has_tag.gift_certificate_id = gift_certificate.id JOIN tag ON tag.id = gift_certificate_has_tag.tag_id group by most_payable_customers.id, tag.id)) as ttt GROUP BY customer_id) as t2 ON t1.customer_id = t2.customer_id and t1.tag_amount = t2.max_tag_amount";
        Query query = entityManager.createNativeQuery(mostWidelyUsedTagQuery, Tag.class);
        return query.getResultList();
    }

    @Override
    public Optional<Tag> find(int id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> find(String name) {
        final String FIND_TAG_BY_NAME = "SELECT tag FROM Tag tag WHERE tag.name=:name";
        TypedQuery<Tag> query = entityManager.createQuery(FIND_TAG_BY_NAME, Tag.class);
        query.setParameter("name", name);
        return query.getResultStream().findFirst();
    }

    @Override
    public void create(String name) {
        entityManager.persist(Tag.builder().name(name).build());
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(Tag.class, id));
    }
}