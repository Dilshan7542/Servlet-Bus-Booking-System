package lk.servlet.bo.custom.impl;

import lk.servlet.bo.custom.RouteBO;
import lk.servlet.bo.util.FactoryConfiguration;
import lk.servlet.dao.DAOType;
import lk.servlet.dao.DAOFactory;
import lk.servlet.dao.custom.BusDAO;
import lk.servlet.dao.custom.RouteDAO;
import lk.servlet.dao.custom.impl.BusDAOImpl;
import lk.servlet.dao.custom.impl.RouteDAOImpl;
import lk.servlet.dto.BusDTO;
import lk.servlet.dto.RouteDTO;
import lk.servlet.entity.Bus;
import lk.servlet.entity.Route;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class RouteBOImpl implements RouteBO {
    RouteDAO routeDAO = (RouteDAOImpl) DAOFactory.getInstance().getDAO(DAOType.ROUTE);
    BusDAO busDAO = (BusDAOImpl) DAOFactory.getInstance().getDAO(DAOType.BUS);
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
    public RouteDTO saveRoute(RouteDTO entity) throws SQLException {
        openSession();
        final Bus bus = busDAO.search(session,entity.getBusDTO().getBusID());
        final Route save = routeDAO.save(session, new Route(
                entity.getRouteID(),
                entity.getVia_city(),
                entity.getCost(),
                entity.getDate(),
                entity.getTime(),
                bus
        ));
        closeSession();
        return save != null ? entity : null;
    }

    @Override
    public RouteDTO updateRoute(RouteDTO entity) throws SQLException {
        openSession();
        final Bus bus = new Bus();
        bus.setBusID(entity.getBusDTO().getBusID());
        final Route update = routeDAO.update(session, new Route(
                entity.getRouteID(),
                entity.getVia_city(),
                entity.getCost(),
                entity.getDate(),
                entity.getTime(),
                bus
        ));
        closeSession();
        return update != null ? entity : null;
    }

    @Override
    public boolean deleteRoute(Integer id) throws SQLException {
        openSession();
        final boolean delete = routeDAO.delete(session, id);
        closeSession();
        return delete;
    }

    @Override
    public RouteDTO searchRoute(Integer id) throws SQLException {
        openSession();
        final Route search = routeDAO.search(session, id);
        closeSession();
        return new RouteDTO(
                search.getRouteID(),
                search.getVia_city(),
                search.getCost(),
                search.getDate(),
                search.getTime(),
                new BusDTO(search.getBus().getBusID())
        );
    }

    @Override
    public List<RouteDTO> getAllRoute() throws SQLException {
        openSession();
        final List<RouteDTO> list = routeDAO.getAll(session).stream().map(r -> new RouteDTO(
                r.getRouteID(),
                r.getVia_city(),
                r.getCost(),
                r.getDate(),
                r.getTime(),
                new BusDTO(r.getBus().getBusID())
        )).collect(Collectors.toList());
        closeSession();
        return list;
    }
}
