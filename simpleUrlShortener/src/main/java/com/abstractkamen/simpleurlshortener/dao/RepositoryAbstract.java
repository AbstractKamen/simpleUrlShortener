package com.abstractkamen.simpleurlshortener.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.*;
import java.util.List;

public abstract class RepositoryAbstract<Entity, Identity> implements Repository<Entity, Identity> {

    private final SessionFactory sessionFactory;

    protected abstract Class<Entity> getEntityClass();

    protected abstract Class<Identity> getIdentityClass();

    protected abstract Identity getIdentity(Entity entity);

    public RepositoryAbstract(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Entity findByIdentity(Identity id) {
        try (final Session session = sessionFactory.openSession()) {
            final Class<Entity> tClass = getEntityClass();
            final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            final CriteriaQuery<Entity> query = criteriaBuilder.createQuery(tClass);
            final Root<Entity> root = query.from(tClass);
            final Predicate predicate = criteriaBuilder.equal(root.get("id"), id);
            query.select(root).where(predicate);
            final List<Entity> resultList = session.createQuery(query).getResultList();
            return !resultList.isEmpty() && resultList.get(0) != null ? resultList.get(0) : null;
        }
    }

    @Override
    public boolean isPresent(Identity id) {
        try (final Session session = sessionFactory.openSession()) {
            final Class<Identity> identityClass = getIdentityClass();
            final Class<Entity> tClass = getEntityClass();
            final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            final CriteriaQuery<Identity> query = criteriaBuilder.createQuery(identityClass);
            final Root<Entity> root = query.from(tClass);
            final Predicate predicate = criteriaBuilder.equal(root.get("id"), id);
            query.multiselect(root.get("id")).where(predicate);
            final List<Identity> resultList = session.createQuery(query).getResultList();
            return !resultList.isEmpty() && resultList.get(0) != null && resultList.get(0).equals(id);
        }
    }

    @Override
    public long count() {
        try (final Session session = sessionFactory.openSession()) {
            final Class<Entity> tClass = getEntityClass();
            final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            final CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
            final Root<Entity> root = query.from(tClass);
            final Expression<Long> expression = criteriaBuilder.count(root.get("id"));
            query.multiselect(expression);
            final List<Long> resultList = session.createQuery(query).getResultList();
            return !resultList.isEmpty() ? resultList.get(0) : 0;
        }
    }

    @Override
    public Entity save(Entity saved) {
        if (!isPresent(getIdentity(saved))) {
            try (final Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.save(saved);
                session.getTransaction().commit();
                return saved;
            }
        }
        throw new IllegalArgumentException();
    }
}

