package dao;

import domain.Kweet;
import domain.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class KweetCollTest {
    private IKweetDao dao = new KweetDaoColl();

    @Test
    public void createKweet() {
        Kweet kweet = new Kweet("@fantastic yo");
        Kweet insertedKweet = dao.create(kweet);

        assertEquals(kweet.getText(), insertedKweet.getText());
    }

    @Test
    public void findKweetById() {
        dao.create(new Kweet("@fantastic yo"));

        Kweet foundKweet = dao.findById(1);
        assertEquals("@fantastic yo", foundKweet.getText());

        Kweet notFoundKweet = dao.findById(10);
        assertNull(notFoundKweet);
    }

    @Test
    public void findByKweetByText() {
        Kweet inserted = new Kweet("@fantastic yo");
        dao.create(inserted);

        List<Kweet> foundKweets = dao.findByText("yo");
        assertEquals("@fantastic yo", foundKweets.get(0).getText());

        List<Kweet> notFoundKweets = dao.findByText("kweetmessage");
        assertEquals(0, notFoundKweets.size());
    }

    @Test
    public void findKweetsByUser() {
        Kweet kweet = new Kweet("@fantastic yo");
        User user1 = new User("Testuser1", "Password1", User.Role.USER);
        user1.setId(1);
        kweet.setUser(user1);

        dao.create(kweet);

        assertEquals(1, dao.findByUser(1).size());
    }

    @Test
    public void findKweetsForUser() {
        Kweet kweet = new Kweet("@fantastic yo");
        User user1 = new User("Testuser1", "Password1", User.Role.USER);
        List<User> following = new ArrayList<User>();
        User followingUser = new User("Testuser2", "Password2", User.Role.USER);
        following.add(followingUser);
        user1.setId(1);
        user1.setFollowing(following);
        kweet.setUser(user1);

        dao.create(kweet);

        assertEquals(1, dao.findForUser(user1).size());
    }

    @Test
    public void findAllKweets() {
        assertEquals(0, dao.findAll().size());

        dao.create(new Kweet("@fantastic yo"));
        dao.create(new Kweet("echt he #heftig"));

        assertEquals(2, dao.findAll().size());
    }

    @Test
    public void updateKweet() {
        dao.create(new Kweet("@fantastic yo"));

        long ID = 1;
        Kweet updatedKweet = new Kweet("niet mee eens");
        updatedKweet.setId(ID);
        dao.update(updatedKweet);

        Kweet foundKweet = dao.findById(ID);
        assertEquals("niet mee eens", foundKweet.getText());
    }

    @Test
    public void deleteKweet() {
        Kweet kweet = new Kweet("@fantastic yo");
        dao.create(kweet);

        dao.delete(kweet);
        assertEquals(0, dao.findAll().size());
    }

    @Test
    public void deleteKweetById() {
        dao.create(new Kweet("@fantastic yo"));
        dao.create(new Kweet("niet mee eens"));
        assertEquals(2, dao.findAll().size());

        dao.deleteById(2);
        assertEquals(1, dao.findAll().size());
    }
}
