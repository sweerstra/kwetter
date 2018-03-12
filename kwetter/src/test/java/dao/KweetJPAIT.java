package dao;

import dao.impl.KweetDaoJPA;
import domain.Kweet;
import domain.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class KweetJPAIT {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static EntityTransaction transaction;
    private static IKweetDao dao;

    @BeforeClass
    public static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("KwetterTestPU");
        entityManager = entityManagerFactory.createEntityManager();
        dao = new KweetDaoJPA(entityManager);
    }

    @Before
    public void setUp() {
        transaction = entityManager.getTransaction();
    }

    @Test
    public void kweetPersistsToDatabase() {
        transaction.begin();

        User user = new User("username", "password", User.Role.USER);

        Kweet kweet = new Kweet("@username hello there", user);
        dao.create(kweet);

        transaction.commit();
    }
}
