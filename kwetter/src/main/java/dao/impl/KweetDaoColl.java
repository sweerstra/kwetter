package dao.impl;

import dao.IKweetDao;
import domain.Kweet;
import domain.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class KweetDaoColl implements IKweetDao {
    private final CopyOnWriteArrayList<Kweet> kweets = new CopyOnWriteArrayList<>();
    private int ID = 1;

    public Kweet findById(long id) {
        return kweets.stream()
                .filter(kweet -> kweet.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Kweet> findByText(String text) {
        return kweets.stream()
                .filter(kweet -> kweet.getText().contains(text))
                .collect(Collectors.toList());
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

    public List<Kweet> findByTrend(String trend) {
        return kweets.stream()
                .filter(kweet -> kweet.getHashtags().contains(trend))
                .collect(Collectors.toList());
    }

    public List<String> findTrends() {
        Set<String> set = new HashSet<>();
        for (Kweet kweet : kweets) {
            set.addAll(kweet.getHashtags());
        }
        return new ArrayList<>(set);
    }

    public List<Kweet> findByMention(String mention) {
        /* List<Kweet> foundKweets = new ArrayList<Kweet>();
        for (Kweet kweet : kweets) {
            if (kweet.getMentions().contains(mention)) {
                foundKweets.add(kweet);
            }
        }
        return foundKweets; */
        return null;
    }

    public List<Kweet> findByUser(long id) {
        return kweets.stream()
                .filter(kweet -> kweet.getUser().getId() == id)
                .collect(Collectors.toList());
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
}
