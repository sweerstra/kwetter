package dao;

import domain.Kweet;
import domain.User;

import java.util.List;

public interface IKweetDao {
    Kweet findById(long id);

    List<Kweet> findByText(String text);

    List<Kweet> findByUser(long id);

    List<Kweet> findForUser(User entity);

    List<Kweet> findAll();

    Kweet create(Kweet entity);

    Kweet update(Kweet entity);

    void delete(Kweet entity);

    void deleteById(long id);
}
