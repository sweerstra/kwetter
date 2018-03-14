package services;

import com.mysql.cj.core.util.StringUtils;
import dao.IKweetDao;
import dao.IUserDao;
import dao.JPA;
import domain.Kweet;
import domain.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
// TODO: KweetService Omzetten naar @Stateless?
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

    /**
     * Check if kweet is between 0 and 140 characters
     * Adds kweet to designated list of user's kweets
     *
     * @param kweet, to create with text and user id
     * @return Kweet, that gets posted)
     */
    public Kweet postKweet(Kweet kweet) {
        String text = kweet.getText();
        if (!StringUtils.isNullOrEmpty(text) && text.length() <= 140) {
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

    /**
     * Check if new text is empty, initiate a new Kweet instance
     * and set the hashtags and mentions.
     *
     * @param kweet, with kweet id to update and new text
     * @return Kweet, newly created kweet
     */
    public Kweet editKweet(Kweet kweet) {
        String text = kweet.getText();
        if (StringUtils.isNullOrEmpty(text) || text.length() > 140) return null;

        Kweet originalKweet = kweetDao.findById(kweet.getId());
        if (originalKweet == null) return null;

        originalKweet.setText(kweet.getText());
        originalKweet.setHashtags();
        originalKweet.setMentions();

        return kweetDao.update(originalKweet);
    }

    /**
     * @param id, id of user to get kweets from
     * @return List<Kweet>, list of kweets found
     */
    public List<Kweet> getKweetsOfUser(long id) {
        User user = userDao.findById(id);
        if (user == null) return null;

        return kweetDao.findByUser(id);
    }

    /**
     * This method gets the kweets of the user and the
     * kweets of the users following
     *
     * @param id, of the user to get kweets and following from
     * @return List<Kweet>, list of kweets found
     */
    public List<Kweet> getTimeline(long id) {
        User user = userDao.findById(id);
        if (user == null) return null;

        return kweetDao.findForUser(user);
    }

    /**
     * @param trend, trend to find
     * @return List<Kweet>, in where the trend is used
     */
    public List<Kweet> getKweetsByTrend(String trend) {
        return kweetDao.findByTrend(String.format("#%s", trend));
    }

    /**
     * @param mention, mention to find
     * @return List<Kweet>, in where the user is mentioned
     */
    public List<Kweet> getKweetsByMention(String mention) {
        mention = mention.startsWith("@")
                ? mention
                : String.format("@%s", mention);

        return kweetDao.findByMention(mention);
    }

    /**
     * @return List<String>, of current trends, sorted ascending by occurence
     */
    public List<String> getTrends() {
        return kweetDao.findTrends();
    }

    /**
     * @param text, keyword to match
     * @return List<Kweet>, where keyword was found in text
     */
    public List<Kweet> searchKweets(String text) {
        if (StringUtils.isNullOrEmpty(text)) {
            return new ArrayList<>();
        }

        return kweetDao.findByText(text);
    }

    /**
     * Add a like to the user's liked list
     *
     * @param kweet,  to like
     * @param userId, to add to the user's like list
     * @return boolean, if like was persisted
     */
    public boolean likeKweet(Kweet kweet, long userId) {
        User user = userDao.findById(userId);
        if (user == null) return false;

        boolean likeResult = user.addLike(kweet);

        if (likeResult) {
            userDao.update(user);
        }

        return likeResult;
    }

    /**
     * @param id, of kweet to remove
     */
    public void removeKweet(long id) {
        Kweet kweet = kweetDao.findById(id);
        kweetDao.delete(kweet);
    }
}
