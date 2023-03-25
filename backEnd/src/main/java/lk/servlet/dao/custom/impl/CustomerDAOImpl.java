package lk.servlet.dao.custom.impl;

import lk.servlet.dao.custom.CustomerDAO;
import lk.servlet.entity.Customer;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public Customer save(Session session, Customer entity) throws SQLException {
        session.save(entity);
        return session.get(Customer.class,entity.getCusID());
    }

    @Override
    public Customer update(Session session, Customer entity) throws SQLException {
        session.update(entity);
        return session.get(Customer.class,entity.getCusID());
    }

    @Override
    public boolean delete(Session session, Integer integer) throws SQLException {
       session.delete( session.get(Customer.class,integer));
       return session.get(Customer.class,integer)==null;
    }

    @Override
    public Customer search(Session session, Integer integer) throws SQLException {
        return session.get(Customer.class,integer);
    }

    @Override
    public List<Customer> getAll(Session session) throws SQLException {
        final Query from_customer = session.createQuery("FROM Customer");
        from_customer.setCacheable(true);
        final List<Customer> list = from_customer.getResultList();
        return list;
    }
}
