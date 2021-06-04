package com.example.springsecurity.repository;

import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
  @Select("select * from users where users.email=#{email}")
  @Results(value = {
      @Result(property = "uid",column = "user_id"),
      @Result(property = "roles", column = "id", many = @Many(select = "getAllRoleByUserId")
          )}
  )
  public User findUserByEmail(@Param("email") String  email);

  @Select("select r.id,r.name from roles r inner join users_roles ur on r.id = ur.role where ur.users=#{id}")
  public List<Role>  getAllRoleByUserId(@Param("id") int id);

}
