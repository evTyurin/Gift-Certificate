package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QueryCriteria;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.util.CriteriaInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class implements GiftCertificateRepository interface
 * This class perform CRUD operations on database
 */
@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public GiftCertificateRepositoryImpl() {
    }

    @Override
    public Optional<GiftCertificate> find(int id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public Optional<GiftCertificate> find(String certificateName) {
        final String FIND_CERTIFICATE_BY_NAME = "SELECT gift_certificate FROM GiftCertificate gift_certificate WHERE gift_certificate.name=:name";
        TypedQuery<GiftCertificate> query = entityManager
                .createQuery(FIND_CERTIFICATE_BY_NAME, GiftCertificate.class);
        query.setParameter("name", certificateName);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<GiftCertificate> findCertificatesByCriterion(List<QueryCriteria> searchQueryCriteria,
                                                             List<QueryCriteria> orderQueryCriteria,
                                                             int pageNumber,
                                                             int pageElementAmount) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        addOrdersAndPredicates(root, criteriaQuery, criteriaBuilder, searchQueryCriteria, orderQueryCriteria);
        return entityManager
                .createQuery(criteriaQuery)
                .setFirstResult((pageNumber - 1) * pageElementAmount)
                .setMaxResults(pageElementAmount)
                .getResultList();
    }

    @Override
    public int countByCriterion(List<QueryCriteria> searchQueryCriteria,
                                List<QueryCriteria> orderQueryCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        addOrdersAndPredicates(
                root,
                criteriaQuery,
                criteriaBuilder,
                searchQueryCriteria,
                orderQueryCriteria);
        Expression<Long> countExpression = criteriaBuilder.count(root);
        criteriaQuery.select(countExpression);
        TypedQuery<Long> typedStudentQuery = entityManager.createQuery(criteriaQuery);
        Long count = typedStudentQuery.getSingleResult();
        return (int) (double) count;
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public void create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
    }

    @Override
    public void update(GiftCertificate giftCertificate) {
        entityManager.merge(giftCertificate);
    }

    private List<Predicate> getPredicates(CriteriaBuilder criteriaBuilder,
                                          List<QueryCriteria> searchQueryCriteria,
                                          Root<GiftCertificate> root) {
        List<Predicate> predicates = new ArrayList<>();
        for (QueryCriteria queryCriteria:searchQueryCriteria) {
            if (queryCriteria.getField().equals("tagName")) {
                predicates.add(criteriaBuilder.equal(root
                        .join("tags").get("name"), queryCriteria.getValue()));
            } else {
                predicates.add(criteriaBuilder.like(root
                                .get(CriteriaInfo.getEntityFieldName(queryCriteria.getField())),
                        "%" + queryCriteria.getValue() + "%"));
            }
        }
        return predicates;
    }

    private List<Order> getOrders(CriteriaBuilder criteriaBuilder,
                                  List<QueryCriteria> orderQueryCriteria,
                                  Root<GiftCertificate> root) {
        List<Order> orders = new ArrayList<>();
        for (QueryCriteria queryCriteria:orderQueryCriteria) {
            if (queryCriteria.getValue().equals("asc")) {
                orders.add(criteriaBuilder.asc(root.get(CriteriaInfo
                        .getEntityFieldName(queryCriteria.getField()))));
            } else {
                orders.add(criteriaBuilder.desc(root.get(CriteriaInfo
                        .getEntityFieldName(queryCriteria.getField()))));
            }
        }
        return orders;
    }

    private void addOrdersAndPredicates(Root<GiftCertificate> root,
                                        CriteriaQuery criteriaQuery,
                                        CriteriaBuilder criteriaBuilder,
                                        List<QueryCriteria> searchQueryCriteria,
                                        List<QueryCriteria> orderQueryCriteria) {
        List<Predicate> predicates = getPredicates(criteriaBuilder, searchQueryCriteria, root);
        List<Order> orders = getOrders(criteriaBuilder, orderQueryCriteria, root);
        criteriaQuery.where(predicates.toArray(new Predicate[]{}));
        criteriaQuery.orderBy(orders);
        criteriaQuery.distinct(true);
    }
}