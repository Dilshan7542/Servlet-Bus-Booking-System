package lk.servlet.controller;

import lk.servlet.bo.BOFactory;
import lk.servlet.bo.BoType;
import lk.servlet.bo.custom.BookingBO;
import lk.servlet.bo.custom.CustomBO;
import lk.servlet.bo.custom.CustomerBO;
import lk.servlet.bo.custom.RouteBO;
import lk.servlet.bo.custom.impl.BookingBOImpl;
import lk.servlet.bo.custom.impl.CustomBOImpl;
import lk.servlet.bo.custom.impl.CustomerBOImpl;
import lk.servlet.bo.custom.impl.RouteBOImpl;
import lk.servlet.dto.BookingDTO;
import lk.servlet.dto.CustomDTO;
import lk.servlet.dto.CustomerDTO;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet(urlPatterns = "/booking")
public class BookingController extends HttpServlet {
    CustomBO customBO=(CustomBOImpl)BOFactory.getInstance().getBO(BoType.CUSTOM);
    BookingBO bookingBO= (BookingBOImpl) BOFactory.getInstance().getBO(BoType.BOOKING);
    RouteBO routeBO= (RouteBOImpl) BOFactory.getInstance().getBO(BoType.ROUTE);
    CustomerBO customerBO= (CustomerBOImpl) BOFactory.getInstance().getBO(BoType.CUSTOMER);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        switch (req.getParameter("option")){
                case "GET_ALL":
                    try {
                     /*   final List<BookingDTO> allBooking = bookingBO.getAllBooking();
                        for (BookingDTO b : allBooking) {
                            final CustomerDTO customerDTO = customerBO.searchCustomer(b.getCustomerDTO().getCusID());
                            final RouteDTO routeDTO = routeBO.searchRoute(b.getRouteDTO().getRouteID());
                            final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                            objectBuilder.add("bookingID",b.getBookingID());
                            objectBuilder.add("custID",b.getCustomerDTO().getCusID());
                            System.out.println("Name ::"+b.getCustomerDTO());
                            objectBuilder.add("name",customerDTO.getName());
                            objectBuilder.add("tel",customerDTO.getTel());
                            objectBuilder.add("busID",routeDTO.getBusDTO().getBusID());
                            objectBuilder.add("city",routeDTO.getVia_city());
                            objectBuilder.add("busSeat",b.getSeat());
                            objectBuilder.add("cost",routeDTO.getCost());
                            objectBuilder.add("departureDate", String.valueOf(routeDTO.getDate()));
                            objectBuilder.add("departureTime", String.valueOf(routeDTO.getTime()));
                            objectBuilder.add("bookingDate", String.valueOf(b.getBookingDate()));
                            objectBuilder.add("bookingTime", String.valueOf(b.getBookingTime()));
                            arrayBuilder.add(objectBuilder.build());
                        }*/

                        final List<CustomDTO> allCustomBooking = customBO.getAllCustomBooking();// This coming from join query...
                        for (CustomDTO ctm : allCustomBooking) {
                            final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                            objectBuilder.add("bookingID",ctm.getBookingID());
                            objectBuilder.add("custID",ctm.getCusID());
                            objectBuilder.add("name",ctm.getName());
                            objectBuilder.add("tel",ctm.getTel());
                            objectBuilder.add("busID",ctm.getBusID());
                            objectBuilder.add("city",ctm.getCity());
                            objectBuilder.add("busSeat",ctm.getBusSeat());
                            objectBuilder.add("cost",ctm.getCost());
                            objectBuilder.add("departureDate", String.valueOf(ctm.getDepartureDate()));
                            objectBuilder.add("departureTime", String.valueOf(ctm.getDepartureTime()));
                            objectBuilder.add("bookingDate", String.valueOf(ctm.getBookingDate()));
                            objectBuilder.add("bookingTime", String.valueOf(ctm.getBookingTime()));
                            arrayBuilder.add(objectBuilder.build());
                        }

                    } catch (SQLException | NullPointerException e) {
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
        final int bookingID = Integer.parseInt(jsonObject.getString("bookingID"));
        final int busSeat = Integer.parseInt(jsonObject.getString("busSeat"));
        final Date bookingDate = Date.valueOf(LocalDate.parse(jsonObject.getString("bookingDate")));
        final Time bookingTime = Time.valueOf(LocalTime.parse(jsonObject.getString("bookingTime")));
        final int routeID = Integer.parseInt(jsonObject.getString("routeID"));
        final int cusID = Integer.parseInt(jsonObject.getString("custID"));
        try {
            final RouteDTO routeDTO = routeBO.searchRoute(routeID);
            final CustomerDTO customerDTO = customerBO.searchCustomer(cusID);
            final BookingDTO bookingDTO = bookingBO.saveBooking(new BookingDTO(bookingID,bookingDate,bookingTime,busSeat,customerDTO,routeDTO));
            if(bookingDTO!=null){
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
        final int bookingID = Integer.parseInt(jsonObject.getString("bookingID"));
        final int busSeat = Integer.parseInt(jsonObject.getString("busSeat"));
        final Date bookingDate = Date.valueOf(LocalDate.parse(jsonObject.getString("bookingDate")));
        final Time bookingTime = Time.valueOf(LocalTime.parse(jsonObject.getString("bookingTime")));
        final int routeID = Integer.parseInt(jsonObject.getString("routeID"));
        final int cusID = Integer.parseInt(jsonObject.getString("custID"));
        try {
            final RouteDTO routeDTO = routeBO.searchRoute(routeID);
            final CustomerDTO customerDTO = customerBO.searchCustomer(cusID);
            final BookingDTO bookingDTO = bookingBO.updateBooking(new BookingDTO(bookingID,bookingDate,bookingTime,busSeat,customerDTO,routeDTO));
            if(bookingDTO!=null){
                resp.getWriter().write("[{\"status\": true}]");
            }else{
                resp.getWriter().write("[{\"status\": false}]");
            }
        } catch (SQLException e) {
            setError("PUT",e,resp);
            e.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            final boolean bookingID = bookingBO.deleteBooking(Integer.valueOf(req.getParameter("bookingID")));
            if (bookingID){
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
