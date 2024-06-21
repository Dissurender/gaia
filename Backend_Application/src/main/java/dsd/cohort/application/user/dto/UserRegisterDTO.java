package dsd.cohort.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
