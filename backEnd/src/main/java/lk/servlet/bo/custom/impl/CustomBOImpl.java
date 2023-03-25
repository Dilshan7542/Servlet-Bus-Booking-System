package lk.servlet.bo.custom.impl;

import lk.servlet.bo.custom.CustomBO;
import lk.servlet.bo.util.FactoryConfiguration;
import lk.servlet.dao.DAOFactory;
import lk.servlet.dao.DAOType;
import lk.servlet.dao.custom.CustomDAO;
import lk.servlet.dao.custom.impl.CustomDAOImpl;
import lk.servlet.dto.CustomDTO;
import lk.servlet.entity.Custom;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomBOImpl implements CustomBO {
    CustomDAO customDAO= (CustomDAOImpl) DAOFactory.getInstance().getDAO(DAOType.CUSTOM);
    @Override
    public List<CustomDTO> getAllCustomBooking() throws SQLException {
    Session session= FactoryConfiguration.getInstance().getSession();
        final List<Custom> allCustomBooking = customDAO.getAllCustomBooking(session);
        session.beginTransaction().commit();
        session.close();
        return allCustomBooking.stream().map(c -> new CustomDTO(
                c.getBookingID(),
                c.getCusID(),
                c.getName(),
                c.getTel(),
                c.getBusID(),
                c.getCity(),
                c.getBusSeat(),
                c.getCost(),
                c.getDepartureDate(),
                c.getDepartureTime(),
                c.getBookingDate(),
                c.getBookingTime()
        )).collect(Collectors.toList());
    }

    @Override
    public void openSession() {

    }

    @Override
    public void closeSession() {

    }
}
