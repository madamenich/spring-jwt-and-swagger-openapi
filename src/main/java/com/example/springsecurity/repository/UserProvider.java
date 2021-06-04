package com.example.springsecurity.repository;

import com.example.springsecurity.model.User;
import org.apache.ibatis.jdbc.SQL;

public class UserProvider {

  public String insertUser(User user){
    return new SQL(){{

      INSERT_INTO("users");
      VALUES("email,username,password","#{users.email},#{user.username},#{user.password}");
      
    }}.toString();
  }

}
