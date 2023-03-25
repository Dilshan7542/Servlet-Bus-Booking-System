package lk.servlet.entity;


import lk.servlet.entity.embeded.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.w3c.dom.ls.LSOutput;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "customer")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements SuperEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cusID;
    String nic;
    @Embedded
    Name name;
    String email;
    String pwd;
    @Enumerated(EnumType.STRING)
    Gender gender;
    Date dob;
    String tel;
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<Booking> booking;

    public Customer(int cusID, String nic, Name name, String email, String pwd, Gender gender, Date dob, String tel) {
        this.cusID = cusID;
        this.nic = nic;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.gender = gender;
        this.dob = dob;
        this.tel = tel;
    }

    public enum Gender{
    MALE,FEMALE
        }
}
