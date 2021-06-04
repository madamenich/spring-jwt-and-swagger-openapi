package com.example.springsecurity.dto;

import com.example.springsecurity.model.Role;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private String uid;
  private String username;
  private String email;
  private String password;
  List<Role> roles;

}
