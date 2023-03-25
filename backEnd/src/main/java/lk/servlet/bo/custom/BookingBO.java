package lk.servlet.bo.custom;

import lk.servlet.bo.SuperBO;
import lk.servlet.dto.BookingDTO;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface BookingBO extends SuperBO {
    BookingDTO saveBooking(BookingDTO entity)throws SQLException;
    BookingDTO updateBooking(BookingDTO entity)throws SQLException;
    boolean deleteBooking(Integer id)throws SQLException;
    BookingDTO searchBooking(Integer id)throws SQLException;
    List<BookingDTO> getAllBooking()throws SQLException;
}
