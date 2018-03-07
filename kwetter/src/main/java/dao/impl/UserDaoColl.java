package dao.impl;

import dao.IUserDao;
import domain.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserDaoColl implements IUserDao {
    private final CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
    private int ID = 1;

    public User findById(long id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public List<User> findFollowing(long id) {
        User user = findById(id);
        if (user == null) return null;

        return user.getFollowing();
    }

    public List<User> findFollowers(long id) {
        User user = findById(id);
        if (user == null) return null;

        return user.getFollowers();
    }

    public List<User> findAll() {
        return users;
    }

    public User create(User entity) {
        entity.setId(ID++);
        users.add(entity);
        return entity;
    }

    public User update(User entity) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId() == entity.getId()) {
                users.set(i, entity);
                return entity;
            }
        }
        return null;
    }

    public void delete(User entity) {
        users.remove(entity);
    }
}
