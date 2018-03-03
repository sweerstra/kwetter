package dao;

import domain.Kweet;

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

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
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
        return entityManager.createQuery(String.format("from %s", this.entityClass.getName())).getResultList();
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(long id) {
        T obj = findById(id);

        if (obj != null) {
            delete(obj);
        }
    }
}