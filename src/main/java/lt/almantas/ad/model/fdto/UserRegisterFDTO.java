package lt.almantas.ad.model.fdto;

import lombok.Data;

@Data
public class UserRegisterFDTO {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}