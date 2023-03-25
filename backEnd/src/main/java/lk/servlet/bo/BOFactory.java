package lk.servlet.bo;

import lk.servlet.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){}
    public static BOFactory getInstance(){
        return boFactory==null ? boFactory=new BOFactory():boFactory;
    }
    public SuperBO getBO(BoType boType){
        switch (boType) {
            case BUS:return new BusBOImpl();
            case ROUTE:return new RouteBOImpl();
            case CUSTOMER:return new CustomerBOImpl();
            case BOOKING:return new BookingBOImpl();
            case USER:return new UserBOImpl();
            case CUSTOM:return new CustomBOImpl();
            default:return null;
        }
    }
}
