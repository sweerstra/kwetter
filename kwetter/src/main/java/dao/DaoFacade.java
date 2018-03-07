package dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class DaoFacade<T> {
    private Class<T> entityClass;

    @PersistenceContext(unitName = "KwetterPU")
    private EntityManager entityManager;

    public DaoFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public T findById(long id) {
        return entityManager.find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery(String.format("FROM %s", this.entityClass.getName()))
                .getResultList();
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }
}