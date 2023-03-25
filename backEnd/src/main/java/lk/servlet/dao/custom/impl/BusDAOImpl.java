package lk.servlet.dao.custom.impl;

import lk.servlet.dao.custom.BusDAO;
import lk.servlet.entity.Bus;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class BusDAOImpl implements BusDAO {


    @Override
    public Bus save(Session session, Bus entity) throws SQLException {
        session.save(entity);
        return entity;
    }

    @Override
    public Bus update(Session session, Bus entity) throws SQLException {
        session.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Session session, Integer integer) throws SQLException {
        session.delete(session.get(Bus.class, integer));
        return session.get(Bus.class, integer) == null;

    }

    @Override
    public Bus search(Session session, Integer integer) throws SQLException {
        return session.get(Bus.class, integer);
    }

    @Override
    public List<Bus> getAll(Session session) throws SQLException {
        final Query from_bus = session.createQuery("FROM bus");
        from_bus.setCacheable(true);
        final List<Bus> list = from_bus.getResultList();
        return list;
    }
}
