package services;

import dao.IUserDao;
import dao.JPA;
import domain.Kweet;
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

    public User getUserById(long id) {
        return dao.findById(id);
    }

    public User getUserByUsername(String username) {
        if (username.length() == 0) {
            return null;
        }

        return dao.findByUsername(username);
    }

    public List<User> getAll() {
        return dao.findAll();
    }

    public boolean followUser(long id, long followingId) {
        if (id == followingId) return false;

        User user = dao.findById(id);
        User toFollow = dao.findById(followingId);

        if (user != null && toFollow != null) {
            user.addFollowing(toFollow);
            toFollow.addFollower(user);
            dao.update(user);
            dao.update(toFollow);
            return true;
        }

        return false;
    }

    public List<User> getFollowing(long id) {
        List<User> following = dao.findFollowing(id);
        return following;
    }

    public List<User> getFollowers(long id) {
        return dao.findFollowers(id);
    }

    public User editUser(User user) {
        return dao.update(user);
    }

    public Kweet likeKweet(Kweet kweet, User user) {
        return user.addLike(kweet) ? kweet : null;
    }
}
