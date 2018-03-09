package domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void likeShouldOnlyBeAddedIfNewAndNotByUser() {
        User user1 = new User("username1", "password", User.Role.USER);
        User user2 = new User("username2", "password", User.Role.USER);
        Kweet kweet1 = new Kweet("@otherUsername", user2);

        boolean likeResult1 = user1.addLike(kweet1);
        assertTrue(likeResult1);
        assertEquals(1, user1.getLiked().size());

        boolean likeResult2 = user1.addLike(kweet1);
        assertFalse(likeResult2);
        assertEquals(1, user1.getLiked().size());

        Kweet kweet2 = new Kweet("#hello everyone", user1);
        user1.addKweet(kweet2);

        boolean likeYourOwnKweetResult = user1.addLike(kweet2);
        assertFalse(likeYourOwnKweetResult);
        assertEquals(1, user1.getLiked().size());
    }

    @Test
    public void followingOrFollowerShouldOnlyBeAddedIfNew() {
        User user1 = new User("username1", "password", User.Role.USER);
        User user2 = new User("username2", "password", User.Role.USER);

        boolean followingResult1 = user1.addFollowing(user2);
        boolean followerResult1 = user2.addFollower(user1);
        // check count - 0
        assertTrue(followingResult1);
        assertTrue(followerResult1);

        boolean followingResult2 = user1.addFollowing(user2);
        boolean followerResult2 = user2.addFollower(user1);
        // check count - 0

        assertFalse(followingResult2);
        assertFalse(followerResult2);
    }

    @Test
    public void followingOrFollowerShouldOnlyBeRemovedIfExisting() {
        User user1 = new User("username1", "password", User.Role.USER);
        User user2 = new User("username2", "password", User.Role.USER);

        user1.addFollowing(user2);
        user2.addFollower(user1);

        assertEquals(1, user1.getFollowing().size());
        user1.removeFollower(user2);
        user1.removeFollower(user1);

    }
}
