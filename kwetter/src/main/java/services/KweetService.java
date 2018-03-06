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

    public List<Kweet> getKweetsOfUser(long id) {
        return kweetDao.findByUser(id);
    }

    public List<Kweet> getTimeline(long id) {
        User user = userDao.findById(id);
        return kweetDao.findForUser(user);
    }

    public List<Kweet> searchKweets(String keyword) {
        if (keyword.length() == 0) {
            return new ArrayList<Kweet>();
        }

        return kweetDao.findByText(keyword);
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

    public void removeKweet(long id) {
        kweetDao.deleteById(id);
    }
}
