package com.example.springsecurity.repository;

import com.example.springsecurity.model.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository {
  @Select("select * form roles where roles.name=#{name}")
  public Role findRoleByName(@Param("name") String name);

}
