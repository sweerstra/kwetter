package services;

import dao.IKweetDao;
import dao.IUserDao;
import dao.JPA;
import domain.Kweet;
import domain.User;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
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

    public Kweet postKweet(Kweet kweet) {
        String text = kweet.getText();
        if (text.length() > 0 && text.length() <= 140) {
            User user = userDao.findById(kweet.getUser().getId());

            if (user != null) {
                Kweet createdKweet = kweetDao.create(kweet);
                user.addKweet(createdKweet);
                userDao.update(user);
                return createdKweet;
            }
        }
        return null;
    }

    public Kweet editKweet(Kweet kweet) {
        if (kweet.getText().isEmpty()) return null;

        Kweet originalKweet = kweetDao.findById(kweet.getId());

        originalKweet.setText(kweet.getText());
        originalKweet.setHashtags();
        originalKweet.setMentions();

        return kweetDao.update(originalKweet);
    }

    public List<Kweet> getKweetsOfUser(long id) {
        return kweetDao.findByUser(id);
    }

    public List<Kweet> getTimeline(long id) {
        User user = userDao.findById(id);

        if (user == null) {
            return new ArrayList<>();
        }

        return kweetDao.findForUser(user);
    }

    public List<Kweet> getKweetsByTrend(String trend) {
        return kweetDao.findByTrend(String.format("#%s", trend));
    }

    public List<Kweet> getKweetsByMention(String mention) {
        mention = mention.startsWith("@")
                ? mention
                : String.format("@%s", mention);

        return kweetDao.findByMention(mention);
    }

    public List<String> getTrends() {
        return kweetDao.findTrends();
    }

    public List<Kweet> searchKweets(String text) {
        return kweetDao.findByText(text);
    }

    public boolean likeKweet(Kweet kweet, long userId) {
        User user = userDao.findById(userId);
        boolean likeResult = user.addLike(kweet);

        if (likeResult) {
            userDao.update(user);
        }

        return likeResult;
    }

    public void removeKweet(long id) {
        Kweet kweet = kweetDao.findById(id);
        kweetDao.delete(kweet);
    }
}
