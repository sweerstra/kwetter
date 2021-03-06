package services;

import com.mysql.cj.core.util.StringUtils;
import dao.IUserDao;
import dao.JPA;
import domain.User;
import domain.UserGroup;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Stateless
public class UserService implements Serializable {
    @Inject
    @JPA
    IUserDao userDao;

    public UserService() {
        super();
    }

    /**
     * @param user, to create
     * @return User, created
     */
    public User addUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        if (StringUtils.isNullOrEmpty(username)
                || StringUtils.isNullOrEmpty(password)
                || getUserByUsername(username) != null) return null;

        // hash password
        /* String sha256HexPassword = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString(); */

        return userDao.create(new User(username, password));
    }

    /**
     * @param username, of the user to find
     * @return User, object that was found or null
     */
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * Find the field of User object that are filled, and updates these fields
     *
     * @param user, to edit
     * @return User, edited
     */
    public User editUser(User user) {
        User originalUser = userDao.findByUsername(user.getUsername());
        if (originalUser == null) return null;

        String email = user.getEmail();
        String profilePicture = user.getProfilePicture();
        String bio = user.getBio();
        String location = user.getLocation();
        String website = user.getWebsite();

        if (email != null) originalUser.setEmail(email);
        if (profilePicture != null) originalUser.setProfilePicture(profilePicture);
        if (bio != null) originalUser.setBio(bio);
        if (location != null) originalUser.setLocation(location);
        if (website != null) originalUser.setWebsite(website);

        return userDao.update(originalUser);
    }

    /**
     * Finds the User object based on username and compares the password
     *
     * @param username, to authenticate
     * @param password, to authenticate
     * @return boolean, user authenticated
     */
    public User authenticateUser(String username, String password) {
        User originalUser = userDao.findByUsername(username);

        if (originalUser != null) {
            if (originalUser.getPassword().equals(password)) {
                return originalUser;
            }
        }
        return null;
    }

    /**
     * @return List<User>, list of all users
     */
    public List<User> getUsers() {
        return userDao.findAll();
    }

    /**
     * Adds a following user to the following list and a follower to the followers list of the other user
     *
     * @param id,          of user that follows
     * @param followingId, of user that is being followed
     * @return boolean, if follow connection was successful
     */
    public boolean followUser(long id, long followingId) {
        if (id == followingId) return false;

        User user = userDao.findById(id);
        User toFollow = userDao.findById(followingId);

        if (user == null || toFollow == null
                || !user.addFollowing(toFollow)
                || !toFollow.addFollower(user)) return false;

        userDao.update(user);
        userDao.update(toFollow);
        return true;
    }

    /**
     * @param id,            of user with follower to unfollow
     * @param unfollowingId, of follower to remove following user
     * @return boolean, if unfollowing was successful
     */
    public boolean unfollowUser(long id, long unfollowingId) {
        if (id == unfollowingId) return false;

        User user = userDao.findById(id);
        User toUnfollow = userDao.findById(unfollowingId);

        if (user == null || toUnfollow == null
                || !user.removeFollowing(toUnfollow)
                || !toUnfollow.removeFollower(user)) return false;

        userDao.update(user);
        userDao.update(toUnfollow);
        return true;
    }

    /**
     * @param id, of user to get following
     * @return List<User>, of following users
     */
    public List<User> getFollowing(long id) {
        return userDao.findFollowing(id);
    }

    /**
     * @param id, of user to get following
     * @return List<User>, of followers
     */
    public List<User> getFollowers(long id) {
        return userDao.findFollowers(id);
    }

    /**
     * @param group, new user group
     * @return User, updated
     */
    public User editUserGroup(long id, UserGroup group) {
        User user = userDao.findById(id);
        if (user == null) return null;

        user.addUserGroup(group);

        return userDao.update(user);
    }

    public User editUserGroups(long id, List<UserGroup> groups) {
        User user = userDao.findById(id);
        if (user == null) return null;

        user.setGroups(groups);

        return userDao.update(user);
    }

    /**
     * @param id, of user to delete
     */
    public boolean deleteUser(long id) {
        User user = userDao.findById(id);
        if (user == null) return false;

        userDao.delete(user);
        return true;
    }
}
