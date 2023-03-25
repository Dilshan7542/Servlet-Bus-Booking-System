package lk.servlet.bo.custom;

import lk.servlet.bo.SuperBO;
import lk.servlet.dto.RouteDTO;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface RouteBO extends SuperBO {
    RouteDTO saveRoute(RouteDTO entity)throws SQLException;
    RouteDTO updateRoute(RouteDTO entity)throws SQLException;
    boolean deleteRoute(Integer id)throws SQLException;
    RouteDTO searchRoute(Integer id)throws SQLException;
    List<RouteDTO> getAllRoute()throws SQLException;


}
