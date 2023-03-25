package lk.servlet.dao.custom;

import lk.servlet.dao.SuperDAO;
import lk.servlet.entity.Custom;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface CustomDAO extends SuperDAO {
   List<Custom> getAllCustomBooking(Session session)throws SQLException;
}
