package lk.servlet.controller;

import lk.servlet.bo.BOFactory;
import lk.servlet.bo.BoType;
import lk.servlet.bo.custom.UserBO;
import lk.servlet.bo.custom.impl.UserBOImpl;
import lk.servlet.dto.UserDTO;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/user")
public class UserController extends HttpServlet {
    UserBO userBO=(UserBOImpl) BOFactory.getInstance().getBO(BoType.USER);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        switch (req.getParameter("option")){
            case "GET_ALL":
                try {
                    final List<UserDTO> allUser = userBO.getAllUser();
                    for (UserDTO userDTO : allUser) {
                        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("userID",userDTO.getUserID());
                        objectBuilder.add("ftName",userDTO.getName());
                        objectBuilder.add("ltName",userDTO.getSurname());
                        objectBuilder.add("email",userDTO.getEmail());
                        arrayBuilder.add(objectBuilder.build());
                    }
                } catch (SQLException e) {
                    setError("GET",e,resp);
                    e.printStackTrace();
                }
                break;
            case "SEARCH":
                break;
        }
        resp.getWriter().print(arrayBuilder.build());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        final JsonReader reader = Json.createReader(req.getReader());
        final JsonObject jsonObject = reader.readObject();
        final int userID = Integer.parseInt(jsonObject.getString("userID"));
        final String ftName = jsonObject.getString("ftName");
        final String ltName = jsonObject.getString("ltName");
        final String email = jsonObject.getString("email");
        final String pwd = jsonObject.getString("pwd");
        final UserDTO userDTO = new UserDTO(userID,ftName,ltName,email,pwd);
        try {

            if(userBO.saveUser(userDTO)!=null){
                resp.getWriter().write("[{\"status\": true}]");
            }else{
                resp.getWriter().write("[{\"status\": false}]");

            }

        } catch (SQLException e) {
            setError("POST",e,resp);
            e.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        final JsonReader reader = Json.createReader(req.getReader());
        final JsonObject jsonObject = reader.readObject();
        final int userID = Integer.parseInt(jsonObject.getString("userID"));
        final String ftName = jsonObject.getString("ftName");
        final String ltName = jsonObject.getString("ltName");
        final String email = jsonObject.getString("email");
        final String pwd = jsonObject.getString("pwd");
        final UserDTO userDTO = new UserDTO(userID,ftName,ltName,email,pwd);
        try {
            if(userBO.updateUser(userDTO)!=null){
                resp.getWriter().write("[{\"status\": true}]");
            }else{
                resp.getWriter().write("[{\"status\": false}]");
            }

        } catch (SQLException e) {
            System.out.println("er.......");
            setError("PUT",e,resp);
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (userBO.deleteUser(Integer.valueOf(req.getParameter("userID")))){
                resp.getWriter().write("[{\"status\": true}]");
            }else{
                resp.getWriter().write("[{\"status\": false}]");
            }
        } catch (SQLException e) {
            setError("DELETE",e,resp);
            e.printStackTrace();
        }
    }public void setError(String method,Exception e,HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("status",false);
        arrayBuilder.add(arrayBuilder.build());
        final JsonObjectBuilder objectBuilder1 = Json.createObjectBuilder();
        objectBuilder.add("statusCode",500);
        objectBuilder.add("method",method);
        objectBuilder.add("error",e.getLocalizedMessage());
        arrayBuilder.add(objectBuilder1.build());
        resp.getWriter().print(arrayBuilder.build());
    }
}
