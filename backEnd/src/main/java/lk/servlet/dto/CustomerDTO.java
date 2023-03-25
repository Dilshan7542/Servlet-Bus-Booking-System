package lk.servlet.dto;

import lk.servlet.entity.embeded.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CustomerDTO implements Serializable {
    int cusID;
    String nic;
    String name;
    String surname;
    String email;
    String pwd;
    Gender gender;
    Date dob;
    String tel;
    BookingDTO bookingDTO;

    public CustomerDTO(int cusID) {
        this.cusID = cusID;
    }

    public CustomerDTO(int cusID, String nic, String name, String surname, String email, String pwd, Gender gender, Date dob, String tel) {
        this.cusID = cusID;
        this.nic = nic;
        this.name = name;
        this.surname = surname;
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
