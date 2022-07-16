package com.epam.esm.dao.impl;

import com.epam.esm.dao.constants.FieldName;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dao.GiftCertificateDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDAOImpl implements GiftCertificateDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public GiftCertificateDAOImpl() {
    }

    @Override
    public Optional<GiftCertificate> find(int id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public Optional<GiftCertificate> find(String certificateName) {
        final String FIND_CERTIFICATE_BY_NAME = "SELECT gift_certificate FROM GiftCertificate gift_certificate WHERE gift_certificate.name=:name";
        TypedQuery<GiftCertificate> query = entityManager.createQuery(FIND_CERTIFICATE_BY_NAME, GiftCertificate.class);
        query.setParameter(FieldName.NAME, certificateName);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<GiftCertificate> findCertificatesByCriterion(String query) {
        return (List<GiftCertificate>) entityManager
                .createNativeQuery(query, GiftCertificate.class).getResultList();
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
}