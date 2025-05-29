package com.sistema.aeropuerto.entidades;

public class JwtRequest {


  private String username;

  private String password;

  public JwtRequest() {
      // Default constructor
  }

  public JwtRequest(String username, String password) {
      this.username = username;
      this.password = password;
  }

  public String getUsername() {
      return username;
  }







}
