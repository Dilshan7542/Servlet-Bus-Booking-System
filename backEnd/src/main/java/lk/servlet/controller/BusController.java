package lk.servlet.controller;

import lk.servlet.bo.BOFactory;
import lk.servlet.bo.BoType;
import lk.servlet.bo.custom.BusBO;
import lk.servlet.bo.custom.impl.BusBOImpl;
import lk.servlet.bo.util.FactoryConfiguration;
import lk.servlet.dao.custom.impl.CustomDAOImpl;
import lk.servlet.dto.BusDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/bus")
public class BusController extends HttpServlet {
    BusBO busBO = (BusBOImpl) BOFactory.getInstance().getBO(BoType.BUS);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); // cate ArrayBuilder

        switch (req.getParameter("option")) {
            case "GET_ALL":
                try {
                    final List<BusDTO> allBus = busBO.getAllBus();
                    for (BusDTO bus : allBus) {
                        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder(); // Create ObjectBuilder
                        objectBuilder.add("busID", bus.getBusID());
                        objectBuilder.add("busNumber", bus.getBusNumber());
                        objectBuilder.add("busSeat", bus.getSeat());
                        arrayBuilder.add(objectBuilder.build()); // Add and build JSON Array

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;


            case "SEARCH":
                try {
                    final BusDTO bus = busBO.searchBus(Integer.valueOf(req.getParameter("busID")));
                    final JsonObjectBuilder objectBuilder = Json.createObjectBuilder(); // Create ObjectBuilder
                    objectBuilder.add("busID", bus.getBusID());
                    objectBuilder.add("busNumber", bus.getBusNumber());
                    objectBuilder.add("busSeat", bus.getSeat());
                    arrayBuilder.add(objectBuilder.build()); // Add and build JSON Array

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
        final int busID = Integer.parseInt(req.getParameter("busID"));
        final String busNumber = req.getParameter("busNumber");
        final int busSeat = Integer.parseInt(req.getParameter("busSeat"));
        final BusDTO busDTO = new BusDTO(busID, busNumber, busSeat);
        try {
            System.out.println("BUS POST");
            final BusDTO busDTO1 = busBO.saveBus(busDTO);

            if(busDTO1!=null){
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("[{\"status\":true}]");
            }else{
            resp.getWriter().write("[{\"status\":false}]");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
        final JsonReader reader = Json.createReader(req.getReader());
        final JsonObject jsonObject = reader.readObject();
        final int busID =Integer.parseInt(jsonObject.getString("busID"));
        final int busSeat = jsonObject.getInt("busSeat");
        final BusDTO busDTO = new BusDTO(busID, jsonObject.getString("busNumber"), busSeat);
            System.out.println("BUS PUT");
            final BusDTO busDTO1 = busBO.updateBus(busDTO);
            if(busDTO1!=null){
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                resp.getWriter().write("[{\"status\":true}]");
            }else{
                resp.getWriter().write("[{\"status\":false}]");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final boolean isDelete = busBO.deleteBus(Integer.valueOf(req.getParameter("busID")));
            if(isDelete){
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                resp.getWriter().write("[{\"status\":true}]");
            }else{
                resp.getWriter().write("[{\"status\":false}]");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
