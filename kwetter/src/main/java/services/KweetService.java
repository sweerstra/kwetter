package services;

import dao.IKweetDao;
import domain.Kweet;
import domain.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class KweetService {
    @Inject
    private IKweetDao dao;

    public KweetService() {
        super();
    }

    public Kweet getKweet(long id) {
        return dao.findById(id);
    }

    public List<Kweet> getKweets(long id) {
        return dao.findByUser(id);
    }

    public List<Kweet> getTimeline(User user) {
        return dao.findForUser(user);
    }

    public List<Kweet> searchKweets(String keyword) {
        if (keyword.length() == 0) {
            return new ArrayList<Kweet>();
        }

        return dao.findByText(keyword);
    }

    public Kweet postKweet(Kweet kweet) {
        String text = kweet.getText();
        if (text.length() == 0 || text.length() > 140) {
            return null;
        }

        return dao.create(kweet);
    }

    public void removeKweet(long id) {
        dao.deleteById(id);
    }
}
