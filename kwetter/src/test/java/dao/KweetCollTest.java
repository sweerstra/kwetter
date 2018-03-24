package dao;

import dao.impl.KweetDaoColl;
import domain.Kweet;
import domain.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class KweetCollTest {
    private IKweetDao dao = new KweetDaoColl();

    @Test
    public void createKweet() {
        Kweet kweet = new Kweet("@fantastic yo", new User("Testuser1", "Password1"));
        Kweet insertedKweet = dao.create(kweet);

        assertEquals(kweet.getText(), insertedKweet.getText());
    }

    @Test
    public void findKweetById() {
        dao.create(new Kweet("@fantastic yo", new User("Testuser1", "Password1")));

        Kweet foundKweet = dao.findById(1);
        assertEquals("@fantastic yo", foundKweet.getText());

        Kweet notFoundKweet = dao.findById(10);
        assertNull(notFoundKweet);
    }

    @Test
    public void findByKweetByText() {
        Kweet inserted = new Kweet("@fantastic yo", new User("Testuser1", "Password1"));
        dao.create(inserted);

        List<Kweet> foundKweets = dao.findByText("yo");
        assertEquals("@fantastic yo", foundKweets.get(0).getText());

        List<Kweet> notFoundKweets = dao.findByText("kweetmessage");
        assertEquals(0, notFoundKweets.size());
    }

    @Test
    public void findKweetsByUser() {
        User user1 = new User("Testuser1", "Password1");
        user1.setId(1);
        Kweet kweet = new Kweet("@fantastic yo", user1);

        dao.create(kweet);

        assertEquals(1, dao.findByUser(1).size());
    }

    @Test
    public void findKweetsForUser() {
        Kweet kweet = new Kweet("@fantastic yo", new User("Testuser1", "Password1"));
        User user1 = new User("Testuser1", "Password1");
        List<User> following = new ArrayList<>();
        User followingUser = new User("Testuser2", "Password2");
        following.add(followingUser);
        user1.setId(1);
        user1.setFollowing(following);
        kweet.setUser(user1);

        dao.create(kweet);

        assertEquals(1, dao.findForUser(user1).size());
    }

    @Test
    public void findByTrend() {
        User user = new User("Testuser1", "Password1");
        Kweet kweet = new Kweet("kweet #trend1 #trend2", user);
        dao.create(kweet);

        assertEquals(0, dao.findByTrend("#hashtag").size());
        assertEquals(0, dao.findByTrend("#trend").size());
        assertEquals(0, dao.findByTrend("trend1").size());
        assertEquals(1, dao.findByTrend("#trend2").size());
    }

    @Test
    public void findTrends() {
        User user = new User("Testuser1", "Password1");
        Kweet kweet1 = new Kweet("kweet #trend1 #trend2", user);
        Kweet kweet2 = new Kweet("#trend2 kweetje", user);
        dao.create(kweet1);
        dao.create(kweet2);

        assertTrue(dao.findTrends().contains("#trend1"));
        assertTrue(dao.findTrends().contains("#trend2"));
    }

    @Test
    public void findByMention() {

    }

    @Test
    public void findAllKweets() {
        assertEquals(0, dao.findAll().size());

        User user1 = new User("Testuser1", "Password1");

        dao.create(new Kweet("@fantastic yo #dope", user1));
        dao.create(new Kweet("echt he #heftig", user1));

        assertEquals(2, dao.findAll().size());
    }

    @Test
    public void updateKweet() {
        dao.create(new Kweet("@fantastic yo", new User("Testuser1", "Password1")));

        long ID = 1;
        Kweet updatedKweet = new Kweet("niet mee eens", new User("Testuser1", "Password1"));
        updatedKweet.setId(ID);
        dao.update(updatedKweet);

        Kweet foundKweet = dao.findById(ID);
        assertEquals("niet mee eens", foundKweet.getText());
    }

    @Test
    public void deleteKweet() {
        Kweet kweet = new Kweet("@fantastic yo", new User("Testuser1", "Password1"));
        dao.create(kweet);

        dao.delete(kweet);
        assertEquals(0, dao.findAll().size());
    }
}
