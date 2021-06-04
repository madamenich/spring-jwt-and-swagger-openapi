package com.example.springsecurity.jwt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
  private AuthenticationManager authenticationManager;
  @Autowired
  public void setAuthenticationManager(
      AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }
private JwtUserDetailsService userDetailsService;
@Autowired
  public void setUserDetailsService(
      JwtUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  private JwtTokenUtil jwtTokenUtil;
@Autowired
  public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
    this.jwtTokenUtil = jwtTokenUtil;
  }


  @Operation(summary = "Login to get token")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "New Employee added", content = {@Content(mediaType = "application/json")}),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = {@Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "401", description = "Un-Authorized user", content = {@Content(schema = @Schema(hidden = true))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content(schema = @Schema(hidden = true))})
  })

  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

    authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

    final UserDetails userDetails = userDetailsService
        .loadUserByUsername(authenticationRequest.getEmail());

    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new JwtResponse(token));
  }

  private void authenticate(String email, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
      System.out.println(new UsernamePasswordAuthenticationToken(email,password).toString());
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }
  }


}
