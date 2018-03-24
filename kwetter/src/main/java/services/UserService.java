package services;

import com.mysql.cj.core.util.StringUtils;
import dao.IUserDao;
import dao.JPA;
import dao.impl.UserGroupJPA;
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

    @Inject
    @JPA
    private UserGroupJPA userGroupDao;

    public UserService() {
        super();
    }

    /**
     * @param username, of user to create
     * @param password, of user to create
     * @return User, created
     */
    public User addUser(String username, String password) {
        if (StringUtils.isNullOrEmpty(username)
                || StringUtils.isNullOrEmpty(password)
                || getUserByUsername(username) != null) return null;

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
    public boolean authenticateUser(String username, String password) {
        User originalUser = userDao.findByUsername(username);

        if (originalUser != null) {
            if (originalUser.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
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
     * @param id,        of user to edit role
     * @param groupName, name of new user group
     * @return User, updated
     */
    public UserGroup editUserGroup(long id, String groupName) {
        User user = userDao.findById(id);
        if (user == null || StringUtils.isNullOrEmpty(groupName)) return null;

        UserGroup group = userGroupDao.findByName(groupName);
        if (group == null) return null;

        group.addUser(user);
        return userGroupDao.update(group);
    }

    /**
     * @param id, of user to delete
     */
    public void deleteUser(long id) {
        User user = userDao.findById(id);
        if (user == null) return;

        userDao.delete(user);
    }
}
