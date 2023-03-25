package lk.servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CustomDTO {
    int bookingID;
    int cusID;
    String name;
    String tel;
    int busID;
    String city;
    int busSeat;
    double cost;
    Date departureDate;
    Time departureTime;
    Date bookingDate;
    Time bookingTime;
}
