package dao;

import domain.Kweet;
import domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class KweetDaoColl implements IKweetDao {
    private final CopyOnWriteArrayList<Kweet> kweets = new CopyOnWriteArrayList<Kweet>();
    private int ID = 1;

    public Kweet findById(long id) {
        for (Kweet kweet : kweets) {
            if (kweet.getId() == id) {
                return kweet;
            }
        }
        return null;
    }

    public List<Kweet> findByText(String text) {
        List<Kweet> found = new ArrayList<Kweet>();
        for (Kweet kweet : kweets) {
            if (kweet.getText().contains(text)) {
                found.add(kweet);
            }
        }
        return found;
    }

    public List<Kweet> findForUser(User entity) {
        List<Kweet> foundKweets = findByUser(entity.getId());
        for (Kweet kweet : kweets) {
            if (entity.getFollowing().contains(kweet.getUser())) {
                foundKweets.add(kweet);
            }
        }
        return foundKweets;
    }

    public List<Kweet> findByUser(long id) {
        List<Kweet> found = new ArrayList<Kweet>();
        for (Kweet kweet : kweets) {
            if (kweet.getUser().getId() == id) {
                found.add(kweet);
            }
        }
        return found;
    }

    public List<Kweet> findAll() {
        return kweets;
    }

    public Kweet create(Kweet entity) {
        entity.setId(ID++);
        kweets.add(entity);
        return entity;
    }

    public Kweet update(Kweet entity) {
        for (int i = 0; i < kweets.size(); i++) {
            Kweet kweet = kweets.get(i);
            if (kweet.getId() == entity.getId()) {
                kweets.set(i, entity);
                return entity;
            }
        }
        return null;
    }

    public void delete(Kweet entity) {
        kweets.remove(entity);
    }

    public void deleteById(long id) {
        for (Kweet kweet : kweets) {
            if (kweet.getId() == id) {
                kweets.remove(kweet);
            }
        }
    }
}
