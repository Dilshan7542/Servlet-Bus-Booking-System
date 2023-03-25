package lk.servlet.bo.custom.impl;

import lk.servlet.bo.custom.UserBO;
import lk.servlet.bo.util.FactoryConfiguration;
import lk.servlet.dao.DAOType;
import lk.servlet.dao.DAOFactory;
import lk.servlet.dao.custom.UserDAO;
import lk.servlet.dao.custom.impl.UserDAOImpl;
import lk.servlet.dto.UserDTO;
import lk.servlet.entity.User;
import lk.servlet.entity.embeded.Name;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAOImpl) DAOFactory.getInstance().getDAO(DAOType.USER);
    Session session;
    Transaction transaction;

    @Override
    public void openSession() {
        session = FactoryConfiguration.getInstance().getSession();
        transaction = session.beginTransaction();
    }

    @Override
    public void closeSession() {
        transaction.commit();
        session.close();
    }

    @Override
    public UserDTO saveUser(UserDTO entity) throws SQLException {
        openSession();
        final User save = userDAO.save(session, new User(
                entity.getUserID(),
                new Name(entity.getName(), entity.getSurname()),
                entity.getEmail(),
                entity.getPwd()
        ));
        closeSession();
        return save != null ? entity : null;
    }

    @Override
    public UserDTO updateUser(UserDTO entity) throws SQLException {
        openSession();
        final User update = userDAO.update(session, new User(
                entity.getUserID(),
                new Name(entity.getName(), entity.getSurname()),
                entity.getEmail(),
                entity.getPwd()
        ));
        closeSession();
        return update != null ? entity : null;
    }

    @Override
    public boolean deleteUser(Integer id) throws SQLException {
        openSession();
        final boolean delete = userDAO.delete(session, id);
        closeSession();
        return delete;
    }

    @Override
    public UserDTO searchUser(Integer id) throws SQLException {
        openSession();
        final User search = userDAO.search(session, id);
        closeSession();
        return new UserDTO(
                search.getUserID(),
                search.getName().getName(),
                search.getName().getSurname(),
                search.getEmail(),
                search.getPwd()
        );
    }

    @Override
    public List<UserDTO> getAllUser() throws SQLException {
        openSession();
        final List<UserDTO> list = userDAO.getAll(session).stream().map(u -> new UserDTO(
                u.getUserID(),
                u.getName().getName(),
                u.getName().getSurname(),
                u.getEmail(),
                u.getPwd()
        )).collect(Collectors.toList());
        closeSession();
        return list;
    }
}
