package lk.servlet.dao.custom.impl;

import lk.servlet.dao.custom.UserDAO;
import lk.servlet.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User save(Session session, User entity) throws SQLException {
        session.save(entity);
        return session.get(User.class,entity.getUserID());
    }

    @Override
    public User update(Session session, User entity) throws SQLException {
        session.update(entity);
        return session.get(User.class,entity.getUserID());
    }

    @Override
    public boolean delete(Session session, Integer integer) throws SQLException {
        session.delete(session.get(User.class,integer));
        return session.get(User.class,integer)==null;
    }

    @Override
    public User search(Session session, Integer integer) throws SQLException {
        return session.get(User.class,integer);
    }

    @Override
    public List<User> getAll(Session session) throws SQLException {
        final Query from_user = session.createQuery("FROM User");
        from_user.setCacheable(true);
        final List<User> list = from_user.list();
        return list;
    }
}
