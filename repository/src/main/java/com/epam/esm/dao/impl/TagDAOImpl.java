package com.epam.esm.dao.impl;

import com.epam.esm.dao.constants.FieldName;
import com.epam.esm.entity.Tag;
import com.epam.esm.dao.TagDAO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class TagDAOImpl implements TagDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> findAll() {
        final String READ_ALL_TAGS = "SELECT tag FROM Tag tag";
        return entityManager.createQuery(READ_ALL_TAGS, Tag.class).getResultList();
    }

    @Override
    public Optional<Tag> find(int id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> find(String name) {
        final String FIND_TAG_BY_NAME = "SELECT tag FROM Tag tag WHERE tag.name=:name";
        TypedQuery<Tag> query = entityManager.createQuery(FIND_TAG_BY_NAME, Tag.class);
        query.setParameter(FieldName.NAME, name);
        return query.getResultStream().findFirst();
    }

    @Override
    public void create(String name) {
        entityManager.persist(Tag.builder().name(name).build());
    }

    @Override
    public void deleteById(int id) {
        entityManager.remove(entityManager.find(Tag.class, id));
    }
}