package services;

import com.mysql.cj.core.util.StringUtils;
import dao.IUserDao;
import dao.JPA;
import domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Stateless
public class UserService implements Serializable {
    @Inject
    @JPA
    IUserDao dao;

    public UserService() {
        super();
    }

    /**
     * @param username, of user to create
     * @param password, of user to create
     * @return User, created
     */
    public User addUser(String username, String password) {
        if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(password)) {
            return null;
        }

        return dao.create(new User(username, password, User.Role.USER));
    }

    /**
     * @param username, of the user to find
     * @return User, object that was found or null
     */
    public User getUserByUsername(String username) {
        return dao.findByUsername(username);
    }

    /**
     * Find the field of User object that are filled, and updates these fields
     *
     * @param user, to edit
     * @return User, edited
     */
    public User editUser(User user) {
        User originalUser = dao.findByUsername(user.getUsername());
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

        return dao.update(originalUser);
    }

    /**
     * Finds the User object based on username and compares the password
     *
     * @param username, to authenticate
     * @param password, to authenticate
     * @return boolean, user authenticated
     */
    public boolean authenticateUser(String username, String password) {
        User originalUser = dao.findByUsername(username);

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
        return dao.findAll();
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

        User user = dao.findById(id);
        User toFollow = dao.findById(followingId);

        if (user == null || toFollow == null
                || !user.addFollowing(toFollow)
                || !toFollow.addFollower(user)) return false;

        dao.update(user);
        dao.update(toFollow);
        return true;
    }

    /**
     * @param id,            of user with follower to unfollow
     * @param unfollowingId, of follower to remove following user
     * @return boolean, if unfollowing was successful
     */
    public boolean unfollowUser(long id, long unfollowingId) {
        if (id == unfollowingId) return false;

        User user = dao.findById(id);
        User toUnfollow = dao.findById(unfollowingId);

        if (user == null || toUnfollow == null
                || !user.removeFollowing(toUnfollow)
                || !toUnfollow.removeFollower(user)) return false;

        dao.update(user);
        dao.update(toUnfollow);
        return true;
    }

    /**
     * @param id, of user to get following
     * @return List<User>, of following users
     */
    public List<User> getFollowing(long id) {
        return dao.findFollowing(id);
    }

    /**
     * @param id, of user to get following
     * @return List<User>, of followers
     */
    public List<User> getFollowers(long id) {
        return dao.findFollowers(id);
    }

    /**
     * @param id,   of user to edit role
     * @param role, new role
     * @return User, updated
     */
    public User editRole(long id, User.Role role) {
        User user = dao.findById(id);
        if (user == null || role == null) return null;

        user.setRole(role);
        return dao.update(user);
    }

    /**
     * @param id, of user to delete
     */
    public void deleteUser(long id) {
        User user = dao.findById(id);
        if (user == null) return;

        dao.delete(user);
    }
}
