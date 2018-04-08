package dao.impl;

import dao.DaoFacade;
import dao.IKweetDao;
import dao.JPA;
import domain.Kweet;
import domain.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@JPA
@Stateless
@SuppressWarnings("unchecked")
public class KweetDaoJPA extends DaoFacade<Kweet> implements IKweetDao {
    @PersistenceContext(unitName = "KwetterPU")
    private EntityManager em;

    public KweetDaoJPA() {
        super(Kweet.class);
    }

    public KweetDaoJPA(EntityManager em) {
        super(Kweet.class, em);
        this.em = em;
    }

    public List<Kweet> findByText(String text) {
        return em.createQuery("SELECT k FROM Kweet k WHERE LOWER(k.text) LIKE LOWER(:text)")
                .setParameter("text", "%" + text + "%")
                .getResultList();
    }

    public List<Kweet> findByUser(long id) {
        return em.createQuery("SELECT k from Kweet k WHERE k.user.id = :id ORDER BY k.date DESC")
                .setParameter("id", id)
                .getResultList();
    }

    public List<Kweet> findForUser(User entity) {
        return em.createQuery("SELECT k from Kweet k WHERE k.user.id = :id OR :id in elements(k.user.followers) ORDER BY k.date DESC")
                .setParameter("id", entity.getId())
                .getResultList();
    }

    public List<Kweet> findByTrend(String trend) {
        return em.createQuery("SELECT k from Kweet k WHERE :trend in elements(k.hashtags)")
                .setParameter("trend", trend)
                .getResultList();
    }

    public List<String> findTrends() {
        String query = "SELECT h.hashtags FROM kweet_hashtags as h GROUP BY h.hashtags ORDER BY COUNT(*) DESC LIMIT 10";
        return em.createNativeQuery(query)
                .getResultList();
    }

    public List<Kweet> findByMention(String mention) {
        return em.createQuery("SELECT k from Kweet k WHERE :mention in elements(k.mentions)")
                .setParameter("mention", mention)
                .getResultList();
    }

    public List<Integer> findLikeIds(long id) {
        return em.createQuery("SELECT k.id from Kweet k, User u WHERE u.id = :id AND k.id in elements(u.liked)")
                .setParameter("id", id)
                .getResultList();
    }
}
