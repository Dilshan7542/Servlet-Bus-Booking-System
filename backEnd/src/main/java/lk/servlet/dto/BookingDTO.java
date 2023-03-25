package lk.servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BookingDTO implements Serializable {
    int bookingID;
    Date bookingDate;
    Time bookingTime;
    int seat;
    CustomerDTO customerDTO;
    RouteDTO routeDTO;
}
