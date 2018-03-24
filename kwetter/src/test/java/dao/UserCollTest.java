package dao;

import dao.impl.UserDaoColl;
import domain.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserCollTest {
    private IUserDao dao = new UserDaoColl();

    @Test
    public void createUser() {
        User user = new User("Testuser", "Password123");
        User insertedUser = dao.create(user);

        assertEquals(user.getUsername(), insertedUser.getUsername());
        assertEquals(user.getPassword(), insertedUser.getPassword());
    }

    @Test
    public void findUserById() {
        dao.create(new User("Testuser", "Password123"));

        User foundUser = dao.findById(1);
        assertEquals("Testuser", foundUser.getUsername());
        assertEquals("Password123", foundUser.getPassword());

        User notFoundUser = dao.findById(10);
        assertNull(notFoundUser);
    }

    @Test
    public void findByUsername() {
        User inserted = new User("Testuser", "Password123");
        dao.create(inserted);

        User foundUser = dao.findByUsername("Testuser");
        assertEquals("Testuser", foundUser.getUsername());
        assertEquals("Password123", foundUser.getPassword());

        User notFoundUser = dao.findByUsername("Otheruser");
        assertNull(notFoundUser);
    }

    @Test
    public void findUserFollowing() {
        User user1 = new User("Testuser1", "Password1");
        List<User> following = new ArrayList<>();
        following.add(new User("Testuser2", "Password2"));
        user1.setFollowing(following);

        dao.create(user1);

        List<User> findFollowing = dao.findFollowing(1);
        assertEquals(1, findFollowing.size());

        User firstFollowing = findFollowing.get(0);
        assertEquals("Testuser2", firstFollowing.getUsername());
        assertEquals("Password2", firstFollowing.getPassword());
    }

    @Test
    public void findUserFollowers() {
        User user1 = new User("Testuser1", "Password1");
        List<User> followers = new ArrayList<>();
        followers.add(new User("Testuser2", "Password2"));
        user1.setFollowers(followers);

        dao.create(user1);

        List<User> findFollowers = dao.findFollowers(1);
        assertEquals(1, findFollowers.size());

        User firstFollower = findFollowers.get(0);
        assertEquals("Testuser2", firstFollower.getUsername());
        assertEquals("Password2", firstFollower.getPassword());
    }

    @Test
    public void findAllUsers() {
        assertEquals(0, dao.findAll().size());

        dao.create(new User("Testuser1", "Password1"));
        dao.create(new User("Testuser2", "Password2"));

        assertEquals(2, dao.findAll().size());
    }

    @Test
    public void updateUser() {
        dao.create(new User("Testuser1", "Password1"));
        dao.create(new User("Testuser2", "Password2"));

        long ID = 2;
        User updatedUser = new User("Testuser3", "Password3");
        updatedUser.setId(ID);
        dao.update(updatedUser);

        User foundUser = dao.findById(ID);
        assertEquals("Testuser3", foundUser.getUsername());
        assertEquals("Password3", foundUser.getPassword());
    }

    @Test
    public void deleteUser() {
        User user = new User("Testuser1", "Password1");
        dao.create(user);

        dao.delete(user);
        assertEquals(0, dao.findAll().size());
    }
}
