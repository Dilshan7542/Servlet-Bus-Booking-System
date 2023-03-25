package lk.servlet.entity;

import com.sun.xml.bind.v2.model.core.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity(name = "route")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Route implements SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int routeID;
    String via_city;
    double cost;
    Date date;
    Time time;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "busID")
    Bus bus;
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "route")
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<Booking> bookingList;

    public Route(int routeID, String via_city, double cost, Date date, Time time, Bus bus) {
        this.routeID = routeID;
        this.via_city = via_city;
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.bus = bus;
    }
}
