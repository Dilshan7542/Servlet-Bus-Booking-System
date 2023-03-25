package lk.servlet.controller;

import lk.servlet.bo.BOFactory;
import lk.servlet.bo.BoType;
import lk.servlet.bo.custom.RouteBO;
import lk.servlet.bo.custom.impl.RouteBOImpl;
import lk.servlet.dto.BusDTO;
import lk.servlet.dto.RouteDTO;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@WebServlet(urlPatterns = "/route")
public class RouteController extends HttpServlet {
    RouteBO routeBO= (RouteBOImpl) BOFactory.getInstance().getBO(BoType.ROUTE);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("application/json");
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        switch(req.getParameter("option")){
                case "GET_ALL":
                    try {
                        final List<RouteDTO> allRoute = routeBO.getAllRoute();
                        for (RouteDTO r : allRoute) {
                            final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                            objectBuilder.add("routeID",r.getRouteID());
                            objectBuilder.add("city",r.getVia_city());
                            objectBuilder.add("cost",r.getCost());
                            objectBuilder.add("departureDate", String.valueOf(r.getDate()));
                            objectBuilder.add("departureTime", String.valueOf(r.getTime()));
                            objectBuilder.add("busID",r.getBusDTO().getBusID());
                            arrayBuilder.add(objectBuilder.build());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "SEARCH":
                    try {
                        final RouteDTO route = routeBO.searchRoute(Integer.valueOf(req.getParameter("routeID")));
                        if(route!=null){
                            final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                            objectBuilder.add("routeID",route.getRouteID());
                            objectBuilder.add("city",route.getVia_city());
                            objectBuilder.add("cost",route.getCost());
                            objectBuilder.add("departureDate", String.valueOf(route.getDate()));
                            objectBuilder.add("departureTime", String.valueOf(route.getTime()));
                            objectBuilder.add("busID",route.getBusDTO().getBusID());
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
        final int routeID = Integer.parseInt(req.getParameter("routeID"));
        final String city = req.getParameter("city");
        final double cost = Double.parseDouble(req.getParameter("cost"));
        final Date departureDate = Date.valueOf(req.getParameter("departureDate"));
        final Time departureTime = Time.valueOf(LocalTime.parse(req.getParameter("departureTime")));
        final String busID = req.getParameter("busID");
        final BusDTO busDTO = new BusDTO();
        busDTO.setBusID(Integer.parseInt(busID));
        System.out.println(busDTO.getBusID());
        try {
            final RouteDTO route = routeBO.saveRoute(new RouteDTO(routeID,city,cost, departureDate,departureTime,busDTO));
            if(route!=null){
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
        resp.setContentType("application/json");
        final JsonReader reader = Json.createReader(req.getReader());
        final JsonObject jsonObject = reader.readObject();
       int routeID= Integer.parseInt(jsonObject.getString("routeID"));
        final String city = jsonObject.getString("city");
        final double cost = Double.parseDouble(jsonObject.getString("cost"));
        Date date= Date.valueOf(jsonObject.getString("departureDate"));
        final Time time = Time.valueOf(LocalTime.parse(jsonObject.getString("departureTime")));
        final BusDTO busDTO = new BusDTO();
        busDTO.setBusID(Integer.parseInt(jsonObject.getString("busID")));
        try {
            final RouteDTO route = routeBO.updateRoute(new RouteDTO(routeID,city,cost, date, time,busDTO));
            if(route!=null){
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
            final boolean isDelete = routeBO.deleteRoute(Integer.valueOf(req.getParameter("routeID")));
                        if(isDelete){
                            resp.getWriter().write("[{\"status\":true}]");
                        }else{
                            resp.getWriter().write("[{\"status\":false}]");
                        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
