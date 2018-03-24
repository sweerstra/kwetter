package dao;

import dao.impl.UserDaoJPA;
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

public class UserJPAIT {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static IUserDao dao;
    private EntityTransaction transaction;

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("KwetterTestPU");
        entityManager = entityManagerFactory.createEntityManager();
        dao = new UserDaoJPA(entityManager);
    }

    @Before
    public void setUp() {
        transaction = entityManager.getTransaction();
    }

    @Test
    public void createUserShouldReturnCreatedUser() {
        transaction.begin();

        User user = new User("username", "password");
        User created = dao.create(user);
        assertNotNull(created);

        transaction.commit();
    }

    @Test
    public void findUserByIdShouldReturnUserIfFound() {
        transaction.begin();

        dao.create(new User("username", "password"));

        User found = dao.findById(1);
        assertNotNull(found);

        User notFound = dao.findById(100);
        assertNull(notFound);

        transaction.commit();
    }

    @Test
    public void findUserByUsernameShouldReturnUserIfFound() {
        transaction.begin();

        dao.create(new User("username", "password"));

        User found = dao.findByUsername("username");
        assertNotNull(found);

        User notFound = dao.findByUsername("unknown");
        assertNull(notFound);

        transaction.commit();
    }

    @Test
    public void updateUserShouldReturnUpdatedUser() {
        transaction.begin();

        dao.create(new User("username", "password"));

        User user = dao.findById(1);
        String bio = "Updated bio";
        user.setBio(bio);
        User updated = dao.update(user);
        assertNotNull(updated);
        assertEquals(bio, updated.getBio());

        transaction.commit();
    }

    @Test
    public void findAllUsersShouldReturnUsersIfTheyExist() {
        transaction.begin();

        dao.create(new User("username1", "password"));
        dao.create(new User("username2", "password"));

        List<User> foundUsers = dao.findAll();
        assertEquals(2, foundUsers.size());

        transaction.commit();
    }

    @Test
    public void deleteUserShouldChangeUsersLength() {
        transaction.begin();

        dao.create(new User("username", "password"));

        int initialLength = dao.findAll().size();
        User user = dao.findByUsername("username");
        dao.delete(user);

        int newLength = dao.findAll().size();
        assertNotEquals(initialLength, newLength);

        transaction.commit();
    }
}