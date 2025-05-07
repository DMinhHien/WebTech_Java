package WebTech.WebTech.domain.DTO;

import jakarta.validation.constraints.NotBlank;

public class ReqLoginDTO {
    @NotBlank(message = "email khong duoc de trong")
    private String email;
    @NotBlank(message = "password khong duoc de trong")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
