package lk.servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import javax.persistence.Cacheable;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Custom implements SuperEntity {
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
