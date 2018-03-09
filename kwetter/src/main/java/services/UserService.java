package services;

import dao.IUserDao;
import dao.JPA;
import domain.User;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class UserService {
    @Inject
    @JPA
    IUserDao dao;

    public UserService() {
        super();
    }

    public User addUser(User entity) {
        return dao.create(entity);
    }

    public User getUserByUsername(String username) {
        if (username.length() == 0) {
            return null;
        }

        return dao.findByUsername(username);
    }

    public List<User> getUsers() {
        return dao.findAll();
    }

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

    public List<User> getFollowing(long id) {
        return dao.findFollowing(id);
    }

    public List<User> getFollowers(long id) {
        return dao.findFollowers(id);
    }

    public User editUser(User user) {
        User original = dao.findByUsername(user.getUsername());

        String email = user.getEmail();
        String profilePicture = user.getProfilePicture();
        String bio = user.getBio();
        String location = user.getLocation();
        String website = user.getWebsite();

        if (email != null) original.setEmail(email);
        if (profilePicture != null) original.setProfilePicture(profilePicture);
        if (bio != null) original.setBio(bio);
        if (location != null) original.setLocation(location);
        if (website != null) original.setWebsite(website);

        return dao.update(original);
    }

    public void deleteUser(long id) {
        User user = dao.findById(id);
        dao.delete(user);
    }
}
