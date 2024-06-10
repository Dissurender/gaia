package dsd.cohort.application.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDTO {

  private String email;
  private String password;
}
