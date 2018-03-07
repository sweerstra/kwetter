package services;

import dao.IKweetDao;
import dao.IUserDao;
import dao.JPA;
import domain.Kweet;
import domain.User;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class KweetService {
    @Inject
    @JPA
    private IKweetDao kweetDao;

    @Inject
    @JPA
    private IUserDao userDao;

    public KweetService() {
        super();
    }

    public Kweet getKweet(long id) {
        return kweetDao.findById(id);
    }

    public List<Kweet> getKweetsOfUser(long id) {
        return kweetDao.findByUser(id);
    }

    public List<Kweet> getTimeline(long id) {
        User user = userDao.findById(id);
        return kweetDao.findForUser(user);
    }

    public List<Kweet> getKweetsByTrend(String trend) {
        trend = trend.startsWith("#") ? trend : String.format("#%s", trend);
        return kweetDao.findByTrend(trend);
    }

    public List<Kweet> getKweetsByMention(String mention) {
        mention = mention.startsWith("@") ? mention : String.format("@%s", mention);
        return kweetDao.findByMention(mention);
    }

    public List<String> getTrends() {
        return kweetDao.findTrends();
    }

    public List<Kweet> searchKweets(String text) {
        return kweetDao.findByText(text);
    }

    public Kweet postKweet(Kweet _kweet) {
        String text = _kweet.getText();
        if (text.length() > 0 && text.length() <= 140) {
            User user = userDao.findById(_kweet.getUser().getId());

            if (user != null) {
                Kweet kweet = kweetDao.create(_kweet);
                user.addKweet(kweet);
                userDao.update(user);
                return kweet;
            }
        }
        return null;
    }

    public boolean likeKweet(Kweet kweet, long userId) {
        User user = userDao.findById(userId);
        boolean result = user.addLike(kweet);

        if (result) {
            userDao.update(user);
        }

        return result;
    }

    public void removeKweet(long id) {
        Kweet kweet = kweetDao.findById(id);
        kweetDao.delete(kweet);
    }
}
