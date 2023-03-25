package lk.servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity(name ="bus")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bus implements SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int busID;
    String busNumber;
    int seat;
    @OneToMany(mappedBy = "bus")
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<Route> routeList;

    public Bus(int busID, String busNumber, int seat) {
        this.busID = busID;
        this.busNumber = busNumber;
        this.seat = seat;
    }
}
