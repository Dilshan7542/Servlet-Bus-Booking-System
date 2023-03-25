package lk.servlet.bo.custom;

import lk.servlet.bo.SuperBO;
import lk.servlet.dto.BusDTO;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface BusBO extends SuperBO {
    BusDTO saveBus(BusDTO entity)throws SQLException;
    BusDTO updateBus(BusDTO entity)throws SQLException;
    boolean deleteBus(Integer id)throws SQLException;
    BusDTO searchBus(Integer id)throws SQLException;
    List<BusDTO> getAllBus()throws SQLException;
}
