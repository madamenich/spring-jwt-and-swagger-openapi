package com.example.springsecurity.rest;

import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleRestController {
  UserRepository repository ;
  UserServiceImp userServiceImp;

  public ArticleRestController(UserRepository repository,
      UserServiceImp userServiceImp) {
    this.repository = repository;
    this.userServiceImp = userServiceImp;
  }

  @Autowired
  private UserRepository userRepository;
  @GetMapping("/secure")
  public String secure(){
    System.out.println(userRepository.findUserByEmail("nich@yahoo.com"));
    return "This is secure";
  }
  @GetMapping("/unsecure")
  public String unsecure(){
    return "This is unsecure";
  }
@Secured("ROLE_VIEWER")
  @GetMapping
  public String forvieweronly(){
    return "this message is for view only";
  }

  @PreAuthorize("hasRole('VIEWR')")
  @GetMapping("/pre-authorize")
  public String testPreAuthorize(String username){
    return "Test with @PreAuthorize";
  }

  @PreAuthorize("#username==authentication.name")
  @GetMapping("/method-arg")
  public String preAuthorizeMethodArgument(String username){
    return "Hello "+username;
  }

  @PostAuthorize("returnObject.username==authentication.name")
  @GetMapping("/post-auth")
  public UserDetails postAuthorize(String email){
    return userServiceImp.loadUserByUsername(email);

  }



}
