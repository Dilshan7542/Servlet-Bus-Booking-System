package lk.servlet.controller;

import lk.servlet.bo.BOFactory;
import lk.servlet.bo.BoType;
import lk.servlet.bo.custom.CustomerBO;
import lk.servlet.bo.custom.impl.CustomerBOImpl;
import lk.servlet.dto.CustomerDTO;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet {
    CustomerBO customerBO= (CustomerBOImpl) BOFactory.getInstance().getBO(BoType.CUSTOMER);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                        resp.setContentType("application/json");
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        switch (req.getParameter("option")){
                           case "GET_ALL":
                               try {
                                   final List<CustomerDTO> allCustomer = customerBO.getAllCustomer();
                                   for (CustomerDTO c : allCustomer) {
                                       final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                                       objectBuilder.add("custID",c.getCusID());
                                       objectBuilder.add("nic",c.getNic());
                                       objectBuilder.add("name",c.getName());
                                       objectBuilder.add("surname",c.getSurname());
                                       objectBuilder.add("email",c.getEmail());
                                       objectBuilder.add("pwd",c.getPwd());
                                       objectBuilder.add("gender", String.valueOf(c.getGender()));
                                       objectBuilder.add("dob", String.valueOf(c.getDob()));
                                       objectBuilder.add("tel",c.getTel());
                                       arrayBuilder.add(objectBuilder.build());
                                   }
                               } catch (SQLException e) {
                                   e.printStackTrace();
                               }
                               break;
                           case "SEARCH":
                               try {
                                   final CustomerDTO customer = customerBO.searchCustomer(Integer.valueOf(req.getParameter("custID")));
                                   if(customer!=null){
                                       final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                                       objectBuilder.add("custID",customer.getCusID());
                                       objectBuilder.add("nic",customer.getNic());
                                       objectBuilder.add("name",customer.getName());
                                       objectBuilder.add("surname",customer.getSurname());
                                       objectBuilder.add("email",customer.getEmail());
                                       objectBuilder.add("pwd",customer.getPwd());
                                       objectBuilder.add("gender", String.valueOf(customer.getGender()));
                                       objectBuilder.add("dob", String.valueOf(customer.getDob()));
                                       objectBuilder.add("tel",customer.getTel());
                                       arrayBuilder.add(objectBuilder.build());

                                   }

                               } catch (SQLException e) {
                                   e.printStackTrace();
                               }
                               break;

                       }

        resp.getWriter().print(arrayBuilder.build());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("application/json");
        try {

            final int custID = Integer.parseInt(req.getParameter("custID"));
            final String nic = req.getParameter("nic");
            final String name = req.getParameter("name");
            final String surname = req.getParameter("surname");
            final String email = req.getParameter("email");
            final String pwd = req.getParameter("pwd");
            final String gender = req.getParameter("gender");
            final String tel = req.getParameter("tel");
            final String dob = req.getParameter("dob");
                CustomerDTO.Gender genderValue= CustomerDTO.Gender.FEMALE;
            if(gender.equals("male") || gender.equals("MALE")|| gender.equals("Male")){
                genderValue= CustomerDTO.Gender.MALE;
            }

            if(customerBO.saveCustomer(new CustomerDTO(custID,nic,name,surname,email,pwd,genderValue, Date.valueOf(dob),tel))!=null){
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("[{\"status\":true}]");
            }else{
                resp.getWriter().write("[{\"status\":false}]");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonReader reader = Json.createReader(req.getReader());
        final JsonObject jsonObject = reader.readObject();
        final int custID = Integer.parseInt(jsonObject.getString("custID"));
        final String nic = jsonObject.getString("nic");
        final String name = jsonObject.getString("name");
        final String surname = jsonObject.getString("surname");
        final String email = jsonObject.getString("email");
        final String pwd = jsonObject.getString("pwd");
        final String gender = jsonObject.getString("gender");
        final String tel = jsonObject.getString("tel");
        final String dob = jsonObject.getString("dob");
        CustomerDTO.Gender genderValue= CustomerDTO.Gender.FEMALE;
        if(gender.equals("male") || gender.equals("MALE")|| gender.equals("Male")){
            genderValue= CustomerDTO.Gender.MALE;
        }
        try {
        if(customerBO.updateCustomer(new CustomerDTO(custID,nic,name,surname,email,pwd,genderValue, Date.valueOf(dob),tel))!=null){
            resp.getWriter().write("[{\"status\":true}]");
        }else{
            resp.getWriter().write("[{\"status\":false}]");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            customerBO.deleteCustomer(Integer.valueOf(req.getParameter("custID")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
