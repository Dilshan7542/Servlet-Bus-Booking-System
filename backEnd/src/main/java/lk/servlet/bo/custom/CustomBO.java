package lk.servlet.bo.custom;

import lk.servlet.bo.SuperBO;
import lk.servlet.dto.CustomDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomBO extends SuperBO {
   List<CustomDTO> getAllCustomBooking() throws SQLException;
}
