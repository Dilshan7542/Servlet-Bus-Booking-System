package lk.servlet.bo.custom.impl;

import lk.servlet.bo.custom.BusBO;
import lk.servlet.bo.util.FactoryConfiguration;
import lk.servlet.dao.DAOType;
import lk.servlet.dao.DAOFactory;
import lk.servlet.dao.custom.BusDAO;
import lk.servlet.dao.custom.impl.BusDAOImpl;
import lk.servlet.dto.BusDTO;
import lk.servlet.entity.Bus;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class BusBOImpl implements BusBO {
    Session session;
    Transaction transaction;
    BusDAO busDAO=(BusDAOImpl) DAOFactory.getInstance().getDAO(DAOType.BUS);
    @Override
    public BusDTO saveBus(BusDTO entity) throws SQLException {
         openSession();
        final Bus save = busDAO.save(session, new Bus(
                entity.getBusID(),
                entity.getBusNumber(),
                entity.getSeat()
        ));
          closeSession();
        if(save!=null) {
            return entity;
        }
        return null;
    }

    @Override
    public BusDTO updateBus(BusDTO entity) throws SQLException {
         openSession();
        final Bus update = busDAO.update(session, new Bus(
                entity.getBusID(),
                entity.getBusNumber(),
                entity.getSeat()
        ));
          closeSession();
        if(update!=null) {
            return entity;
        }else{
            return null;
        }
    }

    @Override
    public boolean deleteBus(Integer id) throws SQLException {
       openSession();
        final boolean delete = busDAO.delete(session, id);
        closeSession();
        return delete;

    }

    @Override
    public BusDTO searchBus(Integer id) throws SQLException {
       openSession();
        final Bus search = busDAO.search(session, id);
        closeSession();
        return new BusDTO(
                search.getBusID(),
                search.getBusNumber(),
                search.getSeat()
        );
    }

    @Override
    public List<BusDTO> getAllBus() throws SQLException {
      openSession();
        final List<BusDTO> list = busDAO.getAll(session).stream().map(b -> new BusDTO(
                b.getBusID(),
                b.getBusNumber(),
                b.getSeat()
        )).collect(Collectors.toList());
        closeSession();
        return list;

    }
    @Override
    public void openSession(){
        session=FactoryConfiguration.getInstance().getSession();
        transaction=session.beginTransaction();
    }
    @Override
    public void closeSession(){
        transaction.commit();
        session.close();
    }
}
