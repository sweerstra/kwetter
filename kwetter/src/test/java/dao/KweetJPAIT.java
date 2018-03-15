package dao;

import dao.impl.KweetDaoJPA;
import dao.impl.UserDaoJPA;
import domain.Kweet;
import domain.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.*;

public class KweetJPAIT {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static IKweetDao kweetDao;
    private static IUserDao userDao;
    private EntityTransaction transaction;

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("KwetterTestPU");
        entityManager = entityManagerFactory.createEntityManager();
        kweetDao = new KweetDaoJPA(entityManager);
        userDao = new UserDaoJPA(entityManager);
    }

    @Before
    public void setUp() {
        transaction = entityManager.getTransaction();
    }

    private Kweet addKweet() {
        User user = new User("username", "password", User.Role.USER);
        userDao.create(user);

        Kweet kweet = new Kweet("@username hello there #hoeisie", user);
        return kweetDao.create(kweet);
    }

    @Test
    public void createKweetShouldReturnCreatedKweet() {
        transaction.begin();

        assertNotNull(addKweet());

        transaction.commit();
    }


    @Test
    public void findKweetByIdShouldReturnKweetIfFound() {
        transaction.begin();

        addKweet();

        Kweet found = kweetDao.findById(2);
        assertNotNull(found);

        Kweet notFound = kweetDao.findById(100);
        assertNull(notFound);

        transaction.commit();
    }

    @Test
    public void updateKweetShouldReturnUpdatedKweet() {
        transaction.begin();

        addKweet();

        Kweet kweet = kweetDao.findById(2);
        String text = "Updated text";
        kweet.setText(text);
        Kweet updated = kweetDao.update(kweet);
        assertNotNull(updated);
        assertEquals(text, updated.getText());

        transaction.commit();
    }

    @Test
    public void findAllKweetsShouldReturnKweetsIfTheyExist() {
        transaction.begin();

        addKweet();
        addKweet();

        List<Kweet> foundKweets = kweetDao.findAll();
        assertEquals(2, foundKweets.size());

        transaction.commit();
    }

    @Test
    public void findKweetsByTextShouldReturnListOfKweetsIfMatchFound() {
        transaction.begin();

        addKweet();

        List<Kweet> kweetsFound = kweetDao.findByText("hello");
        assertEquals(1, kweetsFound.size());

        List<Kweet> kweetsNotFound = kweetDao.findByText("unknown");
        assertEquals(0, kweetsNotFound.size());

        transaction.commit();
    }

    @Test
    public void findKweetsByUserShouldReturnListOfKweets() {
        transaction.begin();

        addKweet();

        List<Kweet> kweetsFound = kweetDao.findByUser(1);
        assertEquals(1, kweetsFound.size());

        List<Kweet> kweetsNotFound = kweetDao.findByUser(100);
        assertEquals(0, kweetsNotFound.size());

        transaction.commit();
    }

    @Test
    public void findTrendsShouldReturnListOfStrings() {
        transaction.begin();

        addKweet();

        List<String> trends = kweetDao.findTrends();
        assertEquals(1, trends.size());
        assertTrue(trends.contains("#hoeisie"));

        transaction.commit();
    }
}
