package lk.servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "booking")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Booking implements SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int bookingID;
    Date bookingDate;
    Time bookingTime;
    int seat;
    @ManyToOne()
    @JoinColumn(name = "cusID",nullable = false)
    Customer customer;
    @ManyToOne
    @JoinColumn(name = "routeID")
    Route route;
}
