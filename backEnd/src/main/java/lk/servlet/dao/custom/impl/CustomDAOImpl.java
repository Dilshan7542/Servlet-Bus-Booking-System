package lk.servlet.dao.custom.impl;

import lk.servlet.dao.custom.CustomDAO;
import lk.servlet.entity.Custom;
import lk.servlet.entity.embeded.Name;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class CustomDAOImpl implements CustomDAO {

    @Override
    public List<Custom> getAllCustomBooking(Session session) throws SQLException {
        final Query<Object[]> query = session.createQuery("SELECT b.bookingID,b.customer.cusID,c.name,c.tel,r.bus.busID,r.via_city,b.seat,r.cost,r.date,r.time,b.bookingDate,b.bookingTime FROM Booking b INNER JOIN Customer c on b.customer.cusID = c.cusID INNER JOIN route r on b.route.routeID = r.routeID", Object[].class);
        query.setCacheable(true);
        final List<Object[]> resultList = query.getResultList();
        List<Custom> list=new ArrayList<>();
        for (Object[] objects : resultList) {
            Name name=(Name)objects[2];
            list.add(new Custom(
                    (Integer)objects[0],
                    (Integer)objects[1],
                    name.getName(),
                    (String)objects[3],
                    (Integer)objects[4],
                    (String)objects[5],
                    (Integer)objects[6],
                    (Double)objects[7],
                    (Date) objects[8],
                    (Time) objects[9],
                    (Date) objects[10],
                    (Time) objects[11]
            ));
        }

        return list;
    }
}
