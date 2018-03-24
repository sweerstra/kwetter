package dao.impl;

import dao.DaoFacade;
import dao.JPA;
import domain.UserGroup;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@JPA
@Stateless
public class UserGroupJPA extends DaoFacade<UserGroup> {
    @PersistenceContext(unitName = "KwetterPU")
    private EntityManager em;

    public UserGroupJPA() {
        super(UserGroup.class);
    }

    public UserGroupJPA(EntityManager entityManager) {
        super(UserGroup.class, entityManager);
    }

    public UserGroup findByName(String name) {
        return (UserGroup) em.createQuery("SELECT ug FROM UserGroup ug WHERE ug.name = :name")
                .setParameter("name", name)
                .getSingleResult();
    }
}
