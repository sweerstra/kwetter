package domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void likeShouldOnlyBeAddedIfNew() {
        User user1 = new User("username1", "password", User.Role.USER);
        User user2 = new User("username2", "password", User.Role.USER);
        Kweet kweet1 = new Kweet("@otherUsername", user2);

        boolean likeResult1 = user1.addLike(kweet1);
        assertTrue(likeResult1);
        assertEquals(1, user1.getLiked().size());

        boolean likeResult2 = user1.addLike(kweet1);
        assertFalse(likeResult2);
        assertEquals(1, user1.getLiked().size());
    }

    @Test
    public void followingOrFollowerShouldOnlyBeAddedIfNew() {
        User user1 = new User("username1", "password", User.Role.USER);
        User user2 = new User("username2", "password", User.Role.USER);

        boolean followingResult1 = user1.addFollowing(user2);
        boolean followerResult1 = user2.addFollower(user1);
        assertTrue(followingResult1);
        assertTrue(followerResult1);
        assertEquals(1, user1.getFollowing().size());
        assertEquals(1, user2.getFollowers().size());

        boolean followingResult2 = user1.addFollowing(user2);
        boolean followerResult2 = user2.addFollower(user1);
        assertFalse(followingResult2);
        assertFalse(followerResult2);
        assertEquals(1, user1.getFollowing().size());
        assertEquals(1, user2.getFollowers().size());
    }

    @Test
    public void followingOrFollowerShouldOnlyBeRemovedIfTheyExist() {
        User user1 = new User("username1", "password", User.Role.USER);
        User user2 = new User("username2", "password", User.Role.USER);

        user1.addFollowing(user2);
        user2.addFollower(user1);
        assertEquals(1, user1.getFollowing().size());

        boolean unfollowResult1 = user1.removeFollowing(user2);
        boolean unfollowResult2 = user1.removeFollower(user1);
        assertTrue(unfollowResult1);
        assertFalse(unfollowResult2);
        assertEquals(0, user1.getFollowing().size());
        assertEquals(1, user2.getFollowers().size());
    }
}
