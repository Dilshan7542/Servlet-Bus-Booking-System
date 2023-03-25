package lk.servlet.listener;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ServletListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BasicDataSource bds=new BasicDataSource();
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/webServlet");
        bds.setUsername("root");
        bds.setPassword("1234");
        bds.setMaxTotal(5);
        bds.setInitialSize(5);
        final ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("bds",bds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Pool Destroyed");
        BasicDataSource bds= (BasicDataSource) sce.getServletContext().getAttribute("bds");
        try {
            bds.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
