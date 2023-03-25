package lk.servlet.bo.custom.impl;

import lk.servlet.bo.custom.CustomerBO;
import lk.servlet.bo.util.FactoryConfiguration;
import lk.servlet.dao.DAOType;
import lk.servlet.dao.DAOFactory;
import lk.servlet.dao.custom.CustomerDAO;
import lk.servlet.dao.custom.impl.CustomerDAOImpl;
import lk.servlet.dto.CustomerDTO;
import lk.servlet.entity.Customer;
import lk.servlet.entity.embeded.Name;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAOImpl) DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);
    Session session;
    Transaction transaction;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO entity) throws SQLException {
        openSession();
        Customer.Gender gender = Customer.Gender.MALE;
        if (entity.getGender().equals(CustomerDTO.Gender.FEMALE)) {
            gender = Customer.Gender.FEMALE;
        }
        final Customer save = customerDAO.save(session, new Customer(entity.getCusID(), entity.getNic(), new Name(entity.getName(), entity.getSurname()), entity.getEmail(), entity.getPwd(), gender, entity.getDob(), entity.getTel()));
        closeSession();
        return save != null ? entity : null;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO entity) throws SQLException {
        openSession();
        Customer.Gender gender = Customer.Gender.MALE;
        if (entity.getGender().equals(CustomerDTO.Gender.FEMALE)) {
            gender = Customer.Gender.FEMALE;
        }
        final Customer update = customerDAO.update(session, new Customer(entity.getCusID(), entity.getNic(), new Name(entity.getName(), entity.getSurname()), entity.getEmail(), entity.getPwd(), gender, entity.getDob(), entity.getTel()));
        closeSession();
        return update != null ? entity : null;
    }

    @Override
    public boolean deleteCustomer(Integer id) throws SQLException {
        openSession();
        final boolean delete = customerDAO.delete(session, id);
        closeSession();
        return delete;
    }

    @Override
    public CustomerDTO searchCustomer(Integer id) throws SQLException {
        openSession();
        final Customer search = customerDAO.search(session, id);
        closeSession();
        CustomerDTO.Gender gender = CustomerDTO.Gender.MALE;
        if (search.getGender().equals(Customer.Gender.FEMALE)) {
            gender = CustomerDTO.Gender.FEMALE;
        }
        return new CustomerDTO(search.getCusID(), search.getNic(), search.getName().getName(), search.getName().getSurname(), search.getEmail(), search.getPwd(), gender, search.getDob(), search.getTel());

    }

    @Override
    public List<CustomerDTO> getAllCustomer() throws SQLException {
        openSession();
        final List<CustomerDTO> list = customerDAO.getAll(session).stream().map(c -> new CustomerDTO(c.getCusID(), c.getNic(), c.getName().getName(), c.getName().getSurname(), c.getEmail(), c.getPwd(), c.getGender().equals(Customer.Gender.MALE) ? CustomerDTO.Gender.MALE : CustomerDTO.Gender.FEMALE, c.getDob(), c.getTel())).collect(Collectors.toList());
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
