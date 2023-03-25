package lk.servlet.bo.util;

import lk.servlet.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;
    private FactoryConfiguration(){
        Configuration configuration=new Configuration().configure().
                addAnnotatedClass(Booking.class).
                addAnnotatedClass(Bus.class).addAnnotatedClass(Customer.class).addAnnotatedClass(Route.class).addAnnotatedClass(User.class);
        sessionFactory=configuration.buildSessionFactory();
    }
    public static FactoryConfiguration getInstance(){
        return factoryConfiguration==null ? factoryConfiguration=new FactoryConfiguration():factoryConfiguration;
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }
}
