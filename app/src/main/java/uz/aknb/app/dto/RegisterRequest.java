package uz.aknb.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.aknb.app.validation.ValidEmail;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @ValidEmail
    private String email;
    private String username;
    private String password;
}
