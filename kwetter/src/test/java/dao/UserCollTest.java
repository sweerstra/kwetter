package dao;

import dao.impl.UserDaoColl;
import domain.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserCollTest {
    private IUserDao dao = new UserDaoColl();

    @Test
    public void createUser() {
        User user = new User("Testuser", "Password123", User.Role.USER);
        User insertedUser = dao.create(user);

        assertEquals(user.getUsername(), insertedUser.getUsername());
        assertEquals(user.getPassword(), insertedUser.getPassword());
        assertEquals(user.getRole(), insertedUser.getRole());
    }

    @Test
    public void findUserById() {
        dao.create(new User("Testuser", "Password123", User.Role.USER));

        User foundUser = dao.findById(1);
        assertEquals("Testuser", foundUser.getUsername());
        assertEquals("Password123", foundUser.getPassword());
        assertEquals(User.Role.USER, foundUser.getRole());

        User notFoundUser = dao.findById(10);
        assertNull(notFoundUser);
    }

    @Test
    public void findByUsername() {
        User inserted = new User("Testuser", "Password123", User.Role.USER);
        dao.create(inserted);

        User foundUser = dao.findByUsername("Testuser");
        assertEquals("Testuser", foundUser.getUsername());
        assertEquals("Password123", foundUser.getPassword());
        assertEquals(User.Role.USER, foundUser.getRole());

        User notFoundUser = dao.findByUsername("Otheruser");
        assertNull(notFoundUser);
    }

    @Test
    public void findUserFollowing() {
        User user1 = new User("Testuser1", "Password1", User.Role.USER);
        List<User> following = new ArrayList<User>();
        following.add(new User("Testuser2", "Password2", User.Role.USER));
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
        User user1 = new User("Testuser1", "Password1", User.Role.USER);
        List<User> followers = new ArrayList<User>();
        followers.add(new User("Testuser2", "Password2", User.Role.USER));
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

        dao.create(new User("Testuser1", "Password1", User.Role.USER));
        dao.create(new User("Testuser2", "Password2", User.Role.USER));

        assertEquals(2, dao.findAll().size());
    }

    @Test
    public void updateUser() {
        dao.create(new User("Testuser1", "Password1", User.Role.USER));
        dao.create(new User("Testuser2", "Password2", User.Role.USER));

        long ID = 2;
        User updatedUser = new User("Testuser3", "Password3", User.Role.USER);
        updatedUser.setId(ID);
        dao.update(updatedUser);

        User foundUser = dao.findById(ID);
        assertEquals("Testuser3", foundUser.getUsername());
        assertEquals("Password3", foundUser.getPassword());
        assertEquals(User.Role.USER, foundUser.getRole());
    }

    @Test
    public void deleteUser() {
        User user = new User("Testuser1", "Password1", User.Role.USER);
        dao.create(user);

        dao.delete(user);
        assertEquals(0, dao.findAll().size());
    }

    @Test
    public void deleteUserById() {
        dao.create(new User("Testuser1", "Password1", User.Role.USER));
        dao.create(new User("Testuser2", "Password2", User.Role.USER));
        assertEquals(2, dao.findAll().size());

        dao.deleteById(2);
        assertEquals(1, dao.findAll().size());
    }
}
