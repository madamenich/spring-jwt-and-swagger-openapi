package com.example.springsecurity;

import javax.swing.Spring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class SpringSecurityApplication {

  public static void main(String[] args) {
    System.out.println(SpringVersion.getVersion());
    SpringApplication.run(SpringSecurityApplication.class, args);
  }

}
