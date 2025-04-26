package WebTech.WebTech.domain.DTO;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
     private long id;
    private String name;
    private String email;
    private String password;
    private Instant birthDay;
    private String gender;
    private String address;
    private String phoneNumber;
    private String userImage;
    private String role;
}
