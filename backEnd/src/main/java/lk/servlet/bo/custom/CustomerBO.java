package lk.servlet.bo.custom;

import lk.servlet.bo.SuperBO;
import lk.servlet.dto.CustomerDTO;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    CustomerDTO saveCustomer(CustomerDTO entity)throws SQLException;
    CustomerDTO updateCustomer(CustomerDTO entity)throws SQLException;
    boolean deleteCustomer(Integer id)throws SQLException;
    CustomerDTO searchCustomer(Integer id)throws SQLException;
    List<CustomerDTO> getAllCustomer()throws SQLException;
}
