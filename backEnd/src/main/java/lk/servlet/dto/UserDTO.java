package lk.servlet.dto;

import lk.servlet.entity.embeded.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserDTO implements Serializable {
    int userID;
    String name;
    String surname;
    String email;
    String pwd;

}
