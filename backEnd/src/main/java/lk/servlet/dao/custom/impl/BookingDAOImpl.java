package lk.servlet.dao.custom.impl;

import lk.servlet.dao.custom.BookingDAO;
import lk.servlet.entity.Booking;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {
    @Override
    public Booking save(Session session, Booking entity) throws SQLException {
       int id=-1;
        id= (Integer)session.save(entity);
       return session.get(Booking.class,id);
    }

    @Override
    public Booking update(Session session, Booking entity) throws SQLException {
       session.update(entity);
        return entity;
    }

    @Override
    public boolean delete(Session session, Integer integer) throws SQLException {
        session.delete(session.get(Booking.class, integer));
        return session.get(Booking.class, integer)==null;
    }

    @Override
    public Booking search(Session session, Integer integer) throws SQLException {
        return session.get(Booking.class, integer);
    }

    @Override
    public List<Booking> getAll(Session session) throws SQLException {
        final Query from_booking = session.createQuery("FROM Booking");
        from_booking.setCacheable(true);
        final List<Booking> list = from_booking.getResultList();
        return list;
    }
}
