package com.sistema.aeropuerto.entidades;

public class JwtResponse {

  private String token;

  public JwtResponse (String token) {
    this.token = token;
  }

  public JwtResponse () {
    // Constructor por defecto
  }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
