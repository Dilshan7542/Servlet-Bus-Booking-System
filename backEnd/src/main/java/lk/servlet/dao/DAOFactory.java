package lk.servlet.dao;

import lk.servlet.dao.custom.impl.*;

public class DAOFactory {
   private static DAOFactory DAOFactory;
   private DAOFactory(){}
    public static DAOFactory getInstance(){
       return DAOFactory ==null ? DAOFactory =new DAOFactory(): DAOFactory;
    }
    public SuperDAO getDAO(DAOType daoType){
        switch (daoType) {
            case BUS:return new BusDAOImpl();
            case ROUTE:return new RouteDAOImpl();
            case CUSTOMER:return new CustomerDAOImpl();
            case BOOKING:return new BookingDAOImpl();
            case USER:return new UserDAOImpl();
            case CUSTOM: return new CustomDAOImpl();
            default:return null;
        }
    }
}
