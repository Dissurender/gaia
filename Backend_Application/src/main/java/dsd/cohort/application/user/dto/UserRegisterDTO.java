package dsd.cohort.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserRegisterDTO {
  
  private String firstName;
  private String lastName;
  private String email;
  private String password;

}
