package com.example.springsecurity.service;

import com.example.springsecurity.dto.UserDto;
import com.example.springsecurity.model.Role;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserDetailsService,UserService {
  ModelMapper mapper = new ModelMapper();
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;
@Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findUserByEmail(email);
    if(user==null)throw new UsernameNotFoundException("User not found");
    return new CustomUserDetails(user);
  }

  @Override
  public void registerDefaultUser(UserDto userDto) {
  Role role = roleRepository.findRoleByName("ROLE_USER");
  User user = mapper.map(userDto,User.class);
  user.addRole(role);


  }
}
