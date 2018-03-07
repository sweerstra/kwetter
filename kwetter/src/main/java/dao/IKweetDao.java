package dao;

import domain.Kweet;
import domain.User;

import java.util.List;

public interface IKweetDao {
    Kweet findById(long id);

    List<Kweet> findByText(String text);

    List<Kweet> findByUser(long id);

    List<Kweet> findForUser(User entity);

    List<Kweet> findByTrend(String trend);

    List<String> findTrends();

    List<Kweet> findByMention(String mention);

    List<Kweet> findAll();

    Kweet create(Kweet entity);

    Kweet update(Kweet entity);

    void delete(Kweet entity);
}
