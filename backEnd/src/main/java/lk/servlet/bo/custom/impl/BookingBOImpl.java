package lk.servlet.bo.custom.impl;

import lk.servlet.bo.custom.BookingBO;
import lk.servlet.bo.util.FactoryConfiguration;
import lk.servlet.dao.DAOType;
import lk.servlet.dao.DAOFactory;
import lk.servlet.dao.custom.BookingDAO;
import lk.servlet.dao.custom.impl.BookingDAOImpl;
import lk.servlet.dto.BookingDTO;
import lk.servlet.dto.CustomerDTO;
import lk.servlet.dto.RouteDTO;
import lk.servlet.entity.Booking;
import lk.servlet.entity.Customer;
import lk.servlet.entity.Route;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class BookingBOImpl implements BookingBO {
    Session session;
    Transaction transaction;
    BookingDAO bookingDAO = (BookingDAOImpl) DAOFactory.getInstance().getDAO(DAOType.BOOKING);

    @Override
    public BookingDTO saveBooking(BookingDTO entity) throws SQLException {
        openSession();
        Route route = new Route();
        route.setRouteID(entity.getRouteDTO().getRouteID());
        Customer customer = new Customer();
        customer.setCusID(entity.getCustomerDTO().getCusID());
        final Booking save = bookingDAO.save(session, new Booking(
                entity.getBookingID(),
                entity.getBookingDate(),
                entity.getBookingTime(),
                entity.getSeat(),
                customer,
                route
        ));
        closeSession();
        return save != null ? entity : null;
    }

    @Override
    public BookingDTO updateBooking(BookingDTO entity) throws SQLException {
        openSession();
        Route route = new Route();
        route.setRouteID(entity.getRouteDTO().getRouteID());
        Customer customer = new Customer();
        customer.setCusID(entity.getCustomerDTO().getCusID());
        final Booking update = bookingDAO.update(session, new Booking(
                entity.getBookingID(),
                entity.getBookingDate(),
                entity.getBookingTime(),
                entity.getSeat(),
                customer,
                route
        ));
        closeSession();
        return update != null ? entity : null;
    }

    @Override
    public boolean deleteBooking(Integer id) throws SQLException {
        openSession();
        final boolean delete = bookingDAO.delete(session, id);
        closeSession();
        return delete;
    }

    @Override
    public BookingDTO searchBooking(Integer id) throws SQLException {
        openSession();
        final Booking search = bookingDAO.search(session, id);
        RouteDTO route = new RouteDTO();
        route.setRouteID(search.getRoute().getRouteID());
        CustomerDTO customer = new CustomerDTO();
        customer.setCusID(search.getRoute().getRouteID());
        closeSession();
        return new BookingDTO(
                search.getBookingID(),
                search.getBookingDate(),
                search.getBookingTime(),
                search.getSeat(),
                customer,
                route
        );
    }

    @Override
    public List<BookingDTO> getAllBooking() throws SQLException {
        openSession();
        final List<BookingDTO> list = bookingDAO.getAll(session).stream().map(b -> new BookingDTO(
                b.getBookingID(),
                b.getBookingDate(),
                b.getBookingTime(),
                b.getSeat(),
                new CustomerDTO(b.getCustomer().getCusID()),
                new RouteDTO(b.getRoute().getRouteID())
        )).collect(Collectors.toList());
        closeSession();
        return list;
    }

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
}
