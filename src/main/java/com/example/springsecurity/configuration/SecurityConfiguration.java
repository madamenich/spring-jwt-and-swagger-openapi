package com.example.springsecurity.configuration;

import com.example.springsecurity.jwt.JwtAuthenticationEntryPoint;
import com.example.springsecurity.jwt.JwtRequestFilter;
import com.example.springsecurity.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Autowired
  private UserDetailsService jwtUserDetailsService;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Bean
public UserDetailsService userDetailsService(){
  return new UserServiceImp();
}

@Bean
public  BCryptPasswordEncoder passwordEncoder(){
  return new BCryptPasswordEncoder();
}

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.inMemoryAuthentication()
//        .withUser("admin").password("{noop}1234").roles("ADMIN")
//        .and()
//        .withUser("author").roles("author").password("{noop}12345");

    auth.userDetailsService(jwtUserDetailsService)
        .passwordEncoder(passwordEncoder());

  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
   http.csrf().disable()
       .authorizeRequests()
       .antMatchers("/authenticate","/swagger-ui**/**").permitAll()
//       .antMatchers("/secure/**").hasRole("USER")
       .anyRequest().authenticated()
       .and()
       .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
       .and().sessionManagement()
       .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//   .httpBasic();
       http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(HttpMethod.OPTIONS,"/**").antMatchers("/resources/**",
        "/v3/api-docs/**",
        "/swagger-ui/**");
  }
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
