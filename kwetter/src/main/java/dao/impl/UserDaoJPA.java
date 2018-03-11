package dao.impl;

import dao.DaoFacade;
import dao.IUserDao;
import dao.JPA;
import domain.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@JPA
@Stateless
@SuppressWarnings("unchecked")
public class UserDaoJPA extends DaoFacade<User> implements IUserDao {
    @PersistenceContext(unitName = "KwetterPU")
    private EntityManager em;

    public UserDaoJPA() {
        super(User.class);
    }

    public User findByUsername(String username) {
        try {
            return (User) em.createQuery("SELECT u FROM User u WHERE u.username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> findFollowing(long id) {
        return em.createQuery("SELECT u.following FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .getResultList();
    }

    public List<User> findFollowers(long id) {
        return em.createQuery("SELECT u.followers FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .getResultList();
    }
}
