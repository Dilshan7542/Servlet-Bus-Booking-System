package lk.servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BusDTO implements Serializable {
    int busID;
    String busNumber;
    int seat;
    List<RouteDTO> routeDTOList;

    public BusDTO(int busID) {
        this.busID = busID;
    }

    public BusDTO(int busID, String busNumber, int seat) {
        this.busID = busID;
        this.busNumber = busNumber;
        this.seat = seat;
    }
}
