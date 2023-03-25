package lk.servlet.dao.custom.impl;

import lk.servlet.dao.custom.RouteDAO;
import lk.servlet.entity.Route;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class RouteDAOImpl implements RouteDAO {
    @Override
    public Route save(Session session, Route entity) throws SQLException {
        session.save(entity);
        return entity;
    }

    @Override
    public Route update(Session session, Route entity) throws SQLException {
        session.update(entity);
        return session.get(Route.class, entity.getRouteID());
    }

    @Override
    public boolean delete(Session session, Integer integer) throws SQLException {
        session.delete(session.get(Route.class, integer));
        return session.get(Route.class, integer) == null;
    }

    @Override
    public Route search(Session session, Integer integer) throws SQLException {
        return session.get(Route.class, integer);
    }

    @Override
    public List<Route> getAll(Session session) throws SQLException {
        final Query from_route = session.createQuery("FROM route");
        from_route.setCacheable(true);
        final List<Route> list = from_route.getResultList();
        return list;
    }
}
