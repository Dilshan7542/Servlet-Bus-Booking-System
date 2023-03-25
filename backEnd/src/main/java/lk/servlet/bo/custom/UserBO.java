package lk.servlet.bo.custom;

import lk.servlet.bo.SuperBO;
import lk.servlet.dto.UserDTO;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    UserDTO saveUser(UserDTO entity)throws SQLException;
    UserDTO updateUser(UserDTO entity)throws SQLException;
    boolean deleteUser(Integer id)throws SQLException;
    UserDTO searchUser(Integer id)throws SQLException;
    List<UserDTO> getAllUser()throws SQLException;
}
