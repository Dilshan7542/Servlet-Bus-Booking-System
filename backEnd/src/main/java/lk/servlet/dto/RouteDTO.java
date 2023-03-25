package lk.servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RouteDTO implements Serializable {
    int routeID;
    String via_city;
    double cost;
    Date date;
    Time time;
    BusDTO busDTO;
    List<BookingDTO> bookingDTOList;

    public RouteDTO(int routeID, String via_city, double cost, Date date, Time time, BusDTO busDTO) {
        this.routeID = routeID;
        this.via_city = via_city;
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.busDTO = busDTO;
    }

    public RouteDTO(int routeID) {
        this.routeID = routeID;
    }
}
