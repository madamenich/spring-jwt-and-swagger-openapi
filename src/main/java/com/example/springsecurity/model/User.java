package com.example.springsecurity.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private int id;
  private String uid;
  private String username;
  private String email;
  private String password;
  List<Role> roles;
public void addRole(Role role){
  this.roles.add(role);
}
}
